import React, { useState, useEffect } from "react";
import axios from './AxiosConfig';
import { useParams, useNavigate } from 'react-router-dom';
import "./cart.css";

const Cart = () => {
    const [cart, setCart] = useState(null);
    const navigate = useNavigate();
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const [modalOpen, setModalOpen] = useState(false);

    useEffect(() => {
        const fetchCartData = async () => {
            try {
                const customerResponse = await axios.get('/customers/me');
                const customer = customerResponse.data;

                if (!customer || !customer.id) {
                    throw new Error("Customer not found.");
                }

                let orderId = localStorage.getItem('orderId');
                console.log(orderId);

                const cartResponse = await axios.get(`/carts/customer?Cid=${customer.id}`);
                setCart(cartResponse.data);
            } catch (err) {
                setError("Error fetching cart data.");
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchCartData();
    }, []);

    const handlePayment = async () => {
        if (!cart) return;

        try {
            const customerResponse = await axios.get("/customers/me");
            const customer = customerResponse.data;

            if (!customer) {
                alert("Cart Empty");
                return;
            }

            const shipmentsResponse = await axios.get(`/shipments/customer?id=${customer.id}`);
            const shipments = shipmentsResponse.data;

            if (!shipments || shipments.length === 0) {
                alert("No shipments");
                return;
            }

            const totalAmount = cart.products.reduce((sum, product, index) => {
                return sum + product.price * cart.quantities[index];
            }, 0);

            const matchingShipment = shipments.find(shipment => {
                return shipment.orders.some(order => {
                    const orderProductIds = order.orderItems.map(item => item.product.id).sort();
                    const cartProductIds = cart.products.map(product => product.id).sort();
                    return JSON.stringify(orderProductIds) === JSON.stringify(cartProductIds);
                });
            });

            if (!matchingShipment) {
                return;
            }

            await Promise.all(matchingShipment.orders.map(async (order) => {
                order.inProgress = true;
                try {
                    await axios.patch(`/orders/${order.id}/inProgress`);
                } catch (error) {
                    console.error(`Error with order Id: ${order.id}:`, error);
                }
            }));

            const paymentData = {
                paymentDate: new Date().toISOString().split('T')[0],
                paymentMet: "CREDIT_CARD",
                amount: totalAmount.toFixed(2)
            };

            await axios.post(`/shipments/${matchingShipment.id}/payment`, paymentData);

            try {
                await axios.delete("/products/allCart", { params: { Cid: customer.id } });
            } catch (error) {
                console.error("ErRoR:", error);
            }

            setModalOpen(false);
        } catch (err) {
            alert("Error with payment.");
            console.error(err);
        }
        navigate('/main');
    };


    if (loading) {
        return <div className="cart-loading">Loading cart...</div>;
    }

    if (error) {
        return <div className="cart-error">{error}</div>;
    }

    if (!cart || !cart.products || cart.products.length === 0) {
        return <div className="cart-empty">Your cart is empty.</div>;
    }

    return (
        <div className="cart-container">
            <h1>Your Cart</h1>
            <table className="cart-table">
                <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody>
                {cart.products.map((product, index) => (
                    <tr key={product.id}>
                        <td>{product.name}</td>
                        <td>${product.price.toFixed(2)}</td>
                        <td>{cart.quantities[index]}</td>
                        <td>${(product.price * cart.quantities[index]).toFixed(2)}</td>
                    </tr>
                ))}
                </tbody>
                <tfoot>
                <tr>
                    <td colSpan="3">Total Price</td>
                    <td>${cart.price.toFixed(2)}</td>
                </tr>
                </tfoot>
            </table>

            <div className="cart-buttons">
                <button className="add-to-cart-button" onClick={() => setModalOpen(true)}>Finalize Order</button>
                <button className="back-to-shop-button" onClick={() => window.location.href = "/main"}>Back to Shop</button>
            </div>

            {modalOpen && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>Finalize Your Order</h2>
                        <p>Proceed with payment?</p>
                        <button className="add-to-cart-button" onClick={handlePayment}>Confirm Payment</button>
                        <button className="back-to-shop-button" onClick={() => setModalOpen(false)}>Cancel</button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Cart;
