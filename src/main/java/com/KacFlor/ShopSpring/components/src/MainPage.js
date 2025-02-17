import React, { useState, useEffect } from 'react';
import './MainPage.css';
import axios from './AxiosConfig';
import { useNavigate } from 'react-router-dom';
import { FaCartPlus, FaHeart, FaClipboardList, FaCog, FaUserPlus, FaPlus, FaEdit, FaTrashAlt } from "react-icons/fa";

const MainPage = () => {
    const navigate = useNavigate();
    const [products, setProducts] = useState([]);
    const [isAdmin, setIsAdmin] = useState(false)
    const [blockedProducts, setBlockedProducts] = useState(new Set());
    const [showAdminOptions, setShowAdminOptions] = useState(false);
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [showProductModal, setShowProductModal] = useState(false);
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [newProduct, setNewProduct] = useState({ name: "", price: "", description: "", stock: "" });
    const [activeProductId, setActiveProductId] = useState(null);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axios.get('/products');

                const parsedData = typeof response.data === 'string' ? JSON.parse(response.data) : response.data;

                setProducts(parsedData);
            } catch (error) {
                console.error('Error fetching products:', error);
            }
        };

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

        fetchProducts();
        fetchCurrentUser();

        const handleBeforeUnload = () => {
            clearCartData();
        };

        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, []);

    const handleAdminOptionsClick = () => {
        setShowAdminOptions(!showAdminOptions);
    };

    const handleCreateAdminClick = () => {
        setShowModal(true);
    };

    const handleAddProductClick = () => {
        setShowProductModal(true);
    };

    const handleModalClose = () => {
        setShowModal(false);
        setLogin("");
        setPassword("");
    };

    const handleProductModalClose = () => {
        setShowProductModal(false);
        setNewProduct({ name: "", price: "", description: "", stock: "" });
    };

    const handleRegisterAdmin = async () => {
        try {
            await axios.post('/authentication/register-admin', { login, password });
            alert('Admin registered successfully');
            handleModalClose();
        } catch (error) {
            console.error('Error registering admin:', error);
            alert('Error registering admin');
        }
    };

    const handleAddProduct = async () => {
        try {
            await axios.post('/products/new', newProduct);
            alert('Product added successfully');
            setProducts([...products, newProduct]);
            handleProductModalClose();
            window.location.reload();
        } catch (error) {
            console.error('Error adding product:', error);
            alert('Error adding product');
        }
    };

    const handleAddToCart = async (product) => {
        try {
            let shipmentId = localStorage.getItem('shipmentId');
            let orderId = localStorage.getItem('orderId');

            if (!shipmentId || !orderId) {
                const newShipment = {
                    shipmentDate: null,
                    address: "",
                    city: "",
                    state: "",
                    country: "",
                    zipcode: "",
                };

                const shipmentResponse = await axios.post('/customers/me/shipment', newShipment);
                shipmentId = shipmentResponse.data;

                const newOrder = {
                    orderDate: new Date(),
                    totalPrice: 0,
                };

                const orderResponse = await axios.post(`/shipments/${shipmentId}/order`, newOrder);
                orderId = orderResponse.data;
                console.log(orderId);

                localStorage.setItem('shipmentId', shipmentId);
                localStorage.setItem('orderId', orderId);
            } else {
                const { data: existingOrder } = await axios.get(`/orders/${orderId}`);

                if (existingOrder.inProgress) {
                    const newOrder = {
                        orderDate: new Date(),
                        totalPrice: 0,
                    };

                    const { data: newOrderId } = await axios.post(`/shipments/${shipmentId}/order`, newOrder);
                    orderId = newOrderId;

                    localStorage.setItem('orderId', orderId);
                }
            }

            const newItem = {
                quantity: 1,
            };

            await axios.post(`/products/${product.id}/order-item?Oid=${orderId}`, newItem);

            alert(`Product with ID: ${product.id} added to cart and item added to the order.`);

            const customerResponse = await axios.get('/customers/me');
            const customer = customerResponse.data;

            if (!customer || !customer.id) {
                alert('Customer not found');
                return;
            }

            const customerId = customer.id;

            const cartResponse = await axios.get(`/carts/customer?Cid=${customerId}`);
            const cart = cartResponse.data;

            if (!cart || !cart.id) {
                alert('Cart not found for the customer');
                return;
            }

            const cartId = cart.id;

            const cartItem = {
                quantity: 1,
            };

            await axios.post(`/products/${product.id}/cart?Cid=${cartId}`, cartItem);

            alert(`Product with ID: ${product.id} added to the cart.`);
        } catch (error) {
            console.error('Error adding product to cart or adding item to order:', error);
            alert('Error adding product to cart or adding item to order.');
        }
    };

    const clearCartData = () => {
        localStorage.removeItem('shipmentId');
        localStorage.removeItem('orderId');
    };

    const handleBlockProduct = async (productId) => {
        try {
            const response = await axios.get(`/products/${productId}/block`);

            setProducts((prevProducts) =>
                prevProducts.map((product) =>
                    product.id === productId
                        ? { ...product, isBlocked: !product.isBlocked }
                        : product
                )
            );
        } catch (error) {
            console.error("There was an error blocking or unblocking the product:", error);
        }
    };

    const handleAddToWishlist = async (productId) => {
        try {

            const customerResponse = await axios.get('/customers/me');
            const customerId = customerResponse.data?.id;

            if (!customerId) {
                alert('Unable to fetch customer data');
                return;
            }

            const wishlistResponse = await axios.get('/wishlists/customer', {
                params: { id: customerId },
            });

            const wishlistId = wishlistResponse.data?.id;

            if (!wishlistId) {
                alert('Wishlist not found');
                return;
            }

            await axios.post(`/products/${productId}/wishlist`, null, {
                params: { Wid: wishlistId },
            });

            alert(`Product with ID: ${productId} added to wishlist successfully!`);
        } catch (error) {
            console.error('Error adding product to wishlist:', error);
            alert('Failed to add product to wishlist');
        }
    };

    const handleFavouritesClick = () => {
        navigate("/wishlist");
    };

    const handleCartClick = () => {
        navigate("/cart");
    };

    const handleEditProduct = (product) => {
        navigate(`/product/${product.id}`);
    };

    const handleOrdersClick = () => {
        navigate(`/orders`);
    };

    return (
        <div className="main-page-container">
            <div className="sidebar-container">
                <div className={`sidebar ${isSidebarOpen ? 'open' : ''}`}>
                    <button className="main-button" onClick={handleCartClick} data-text="Cart">
                        <FaCartPlus/>
                    </button>
                    <div>
                        <button className="main-button" onClick={handleFavouritesClick} data-text="Favourites">
                            <FaHeart/>
                        </button>
                    </div>
                    <button className="main-button" onClick={handleOrdersClick} data-text="Orders">
                        <FaClipboardList/>
                    </button>
                    {isAdmin && (
                        <div className="admin-options">
                            <button className="admin-button" onClick={handleAdminOptionsClick}
                                    data-text="Admin Options">
                                <FaCog/>
                            </button>
                            {showAdminOptions && (
                                <div className="admin-suboptions">
                                    <button className="admin-button" onClick={handleCreateAdminClick}
                                            data-text="Create Admin">
                                        <FaUserPlus/>
                                    </button>
                                    <button className="admin-button" onClick={handleAddProductClick}
                                            data-text="Add Product">
                                        <FaPlus/>
                                    </button>
                                </div>
                            )}
                        </div>
                    )}
                </div>
            </div>

            <div className="main-content">
                <div className="product-table-container">
                    <table className="product-table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Description</th>
                            <th></th>
                            {isAdmin && <th>Admin</th>}
                            <th>Wishlist</th>
                        </tr>
                        </thead>
                        <tbody>
                        {products.map((product) => (
                            <tr
                                key={product.id}
                                className={`product-row ${product.isBlocked ? 'blocked-row' : ''}`}
                            >
                                <td>
                                    <a href={`/product-buy/${product.id}`} className="product-link">
                                        {product.name}
                                    </a>
                                </td>
                                <td>{product.price} zł</td>
                                <td>{product.description}</td>
                                <td>
                                    <button
                                        onClick={() => handleAddToCart(product)}
                                        disabled={product.stock === 0 || product.isBlocked}
                                        className={product.isBlocked ? 'out-of-stock' : (product.stock === 0 ? 'out-of-stock' : '')}
                                    >
                                        {product.isBlocked ? 'Blocked' : (product.stock === 0 ? 'Out of Stock' : 'Add to Cart')}
                                    </button>
                                </td>

                                {isAdmin && (
                                    <td>
                                        <button
                                            onClick={() => handleEditProduct(product)}
                                            disabled={product.isBlocked}
                                            style={{ marginRight: '8px' }}
                                        >
                                            <FaEdit />
                                        </button>
                                        <button
                                            onClick={() => handleBlockProduct(product.id)}
                                            className={`delete-button ${product.isBlocked ? 'blocked' : ''}`}
                                        >
                                            <FaTrashAlt />
                                        </button>
                                    </td>
                                )}

                                <td>
                                    <button
                                        onClick={() => handleAddToWishlist(product.id)}
                                        disabled={product.isBlocked}
                                    >
                                        <FaHeart/>
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>


                    </table>
                </div>
            </div>


            {showModal && (
                <div className="modal">
                    <div className="modal-content">
                        <h3>Create New Admin</h3>
                        <label htmlFor="login">Login:</label>
                        <input
                            type="text"
                            id="login"
                            value={login}
                            onChange={(e) => setLogin(e.target.value)}
                            placeholder="Enter admin login"
                        />
                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter admin password"
                        />
                        <div className="modal-actions">
                            <button onClick={handleRegisterAdmin}>Register Admin</button>
                        </div>
                    </div>
                </div>
            )}

            {showProductModal && (
                <div className="modal">
                    <div className="modal-content">
                        <h3>Add New Product</h3>
                        <label htmlFor="name">Name:</label>
                        <input
                            type="text"
                            id="name"
                            value={newProduct.name}
                            onChange={(e) => setNewProduct({...newProduct, name: e.target.value})}
                            placeholder="Enter product name"
                        />
                        <label htmlFor="price">Price:</label>
                        <input
                            type="number"
                            id="price"
                            value={newProduct.price}
                            onChange={(e) => setNewProduct({...newProduct, price: e.target.value})}
                            placeholder="Enter product price"
                        />
                        <label htmlFor="description">Description:</label>
                        <input
                            type="text"
                            id="description"
                            value={newProduct.description}
                            onChange={(e) => setNewProduct({...newProduct, description: e.target.value})}
                            placeholder="Enter product description"
                        />
                        <label htmlFor="stock">Stock:</label>
                        <input
                            type="number"
                            id="stock"
                            value={newProduct.stock}
                            onChange={(e) => setNewProduct({...newProduct, stock: e.target.value})}
                            placeholder="Enter product stock"
                        />
                        <div className="modal-actions">
                            <button onClick={handleAddProduct}>Add Product</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );


};

export default MainPage;
