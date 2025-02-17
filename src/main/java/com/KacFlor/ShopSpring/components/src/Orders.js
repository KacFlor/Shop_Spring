import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from 'react-router-dom';
import axios from './AxiosConfig';
import "./Orders.css";

const Orders = () => {
    const [adminOrders, setAdminOrders] = useState([]);
    const [userOrders, setUserOrders] = useState([]);
    const navigate = useNavigate();
    const [selectedOrder, setSelectedOrder] = useState(null);
    const [modalOpen, setModalOpen] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);

    useEffect(() => {
        const fetchCurrentUser = async () => {
            try {
                const response = await axios.get('/user/me');
                if (response.data.role === 'ADMIN') {
                    setIsAdmin(true);
                }
            } catch (error) {
                console.error('Error fetching current user data:', error);
            }
        };
        fetchCurrentUser();
    }, []);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                if (isAdmin) {
                    const response = await axios.get("/shipments");
                    const allOrders = response.data.flatMap(shipment => shipment.orders);
                    setAdminOrders(allOrders);
                }

                const userResponse = await axios.get("/customers/me");
                const customerId = userResponse.data?.id;
                if (customerId) {
                    const userShipmentsResponse = await axios.get(`/shipments/customer?id=${customerId}`);
                    const userOrders = userShipmentsResponse.data.flatMap(shipment => shipment.orders);
                    setUserOrders(userOrders);
                }
            } catch (error) {
                console.error("Error fetching orders:", error);
            }
        };

        fetchOrders();
    }, [isAdmin]);

    const fetchOrderDetails = async (orderId) => {
        try {
            const response = await axios.get(`/orders/${orderId}`);
            setSelectedOrder(response.data);
            setModalOpen(true);
        } catch (error) {
            console.error("Error fetching order details:", error);
        }
    };

    const handleCancel = () => {
        navigate('/main');
    };

    return (
        <div className="orders-container">
            <h2>Orders</h2>

            {isAdmin ? (
                <table className="orders-table">
                    <thead>
                    <tr>
                        <th>Order Date</th>
                        <th>Total Price</th>
                        <th>In Progress</th>
                    </tr>
                    </thead>
                    <tbody>
                    {adminOrders.map((order) => (
                        <tr key={order.id} onClick={() => fetchOrderDetails(order.id)} className="order-row">
                            <td>{new Date(order.orderDate).toLocaleDateString()}</td>
                            <td>{order.totalPrice != null ? `$${order.totalPrice.toFixed(2)}` : ''}</td>
                            <td>{order.inProgress === false ? "In Progress" : "Completed"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <table className="orders-table">
                    <thead>
                    <tr>
                        <th>Order Date</th>
                        <th>Total Price</th>
                        <th>In Progress</th>
                    </tr>
                    </thead>
                    <tbody>
                    {userOrders.map((order) => (
                        <tr key={order.id} onClick={() => fetchOrderDetails(order.id)} className="order-row">
                            <td>{new Date(order.orderDate).toLocaleDateString()}</td>
                            <td>{order.totalPrice != null ? `$${order.totalPrice.toFixed(2)}` : ''}</td>
                            <td>{order.inProgress === false ? "In Progress" : "Completed"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}

            {modalOpen && selectedOrder && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close" onClick={() => setModalOpen(false)}>&times;</span>
                        <h4>Items:</h4>
                        <ul>
                            {selectedOrder.orderItems.map((item) => (
                                <li key={item.id}>{item.name} - x{item.quantity} </li>
                            ))}
                        </ul>
                    </div>
                </div>
            )}

            <button className="back-b" onClick={handleCancel}> Back to Shop</button>
        </div>
    );
};

export default Orders;
