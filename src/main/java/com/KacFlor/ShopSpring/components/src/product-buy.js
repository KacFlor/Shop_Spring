import React, { useState, useEffect } from "react";
import axios from './AxiosConfig';
import { useParams, useNavigate } from 'react-router-dom';
import "./product-buy.css";

const ProductBuy = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [product, setProduct] = useState(null);
    const [error, setError] = useState(null);
    const [inputError, setInputError] = useState(false);
    const [reviews, setReviews] = useState([]);
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const [review, setReview] = useState({ rating: "", comment: "" });
    const [hasReviewed, setHasReviewed] = useState(false);
    const [quantity, setQuantity] = useState(1);
    const [isReviewModalOpen, setIsReviewModalOpen] = useState(false);
    const [loading, setLoading] = useState(false);

    const fetchProduct = async () => {
        try {
            const response = await axios.get(`/products/${id}`);
            setProduct(response.data);
            checkIfReviewed();
        } catch (err) {
            setError("Error!!");
        }
    };

    const fetchReviews = async () => {
        setLoading(true);
        try {
            const response = await axios.post('/reviews/product', null, {
                params: {
                    Pid: id,
                },
            });
            if (response.data) {
                setReviews(response.data);
            }
        } catch (error) {
            console.error('Error fetching reviews:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleQuantityChange = (e) => {
        const value = e.target.value;

        if (/^\d+$/.test(value)) {
            const numericValue = parseInt(value, 10);

            if (numericValue > 0 && numericValue <= product.stock) {
                setQuantity(numericValue);
                setInputError(false);
            } else {
                setInputError(true);
            }
        } else if (value === "") {
            setQuantity("");
            setInputError(false);
        } else {
            setInputError(true);
        }
    };

    const clearCartData = () => {
        localStorage.removeItem('shipmentId');
        localStorage.removeItem('orderId');
    };

    const handleAddToCart = async (product, quantity) => {
        try {
            if (!quantity || isNaN(quantity) || quantity <= 0 || quantity > product.stock) {
                alert('Please enter a valid quantity.');
                return;
            }

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

                const { data: newShipmentId } = await axios.post('/customers/me/shipment', newShipment);
                shipmentId = newShipmentId;

                const newOrder = {
                    orderDate: new Date(),
                    totalPrice: 0,
                };

                const { data: newOrderId } = await axios.post(`/shipments/${shipmentId}/order`, newOrder);
                orderId = newOrderId;

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
                quantity: parseInt(quantity, 10),
            };

            await axios.post(`/products/${product.id}/order-item?Oid=${orderId}`, newItem);
            alert(`Product with ID: ${product.id} added to the order.`);

            const { data: customer } = await axios.get('/customers/me');

            if (!customer || !customer.id) {
                alert('Customer not found.');
                return;
            }

            const customerId = customer.id;

            const { data: cart } = await axios.get(`/carts/customer?Cid=${customerId}`);

            if (!cart || !cart.id) {
                alert('Cart not found for the customer.');
                return;
            }

            const cartId = cart.id;

            const cartItem = {
                quantity: parseInt(quantity, 10),
            };

            await axios.post(`/products/${product.id}/cart?Cid=${cartId}`, cartItem);
            alert(`Product with ID: ${product.id} added to the cart.`);
        } catch (error) {
            console.error('Error adding product to cart or adding item to order:', error);

            if (error.response) {
                alert(`Error: ${error.response.data.message || 'Something went wrong.'}`);
            } else {
                alert('Error adding product to cart or order. Please try again.');
            }
        }
    };

    const checkIfReviewed = async () => {
        try {
            const customerResponse = await axios.get('/customers/me');
            const customerId = customerResponse.data.id;

            const response = await axios.post('/reviews/check', null, {
                params: {
                    Cid: customerId,
                    Pid: id,
                },
            });

            if (response.status === 202) {
                setHasReviewed(true);
            } else {
                setHasReviewed(false);
            }

        } catch (error) {
            console.error("Error checking if user has reviewed:", error);
            setHasReviewed(false);
        }
    };

    const handleAddReview = async () => {
        try {
            const customerResponse = await axios.get('/customers/me');
            const customerId = customerResponse.data.id;

            const newReview = {
                rating: review.rating,
                comment: review.comment,
            };

            const response = await axios.post(`/reviews/new?Cid=${customerId}&Pid=${id}`, newReview);

            if (response.status === 202) {
                closeReviewModal();
                setHasReviewed(true);
            }
            closeSidebar()
            openSidebar()

        } catch (error) {
            console.error('Error adding review:', error);
            alert('Error adding review.');
        }
    };


    useEffect(() => {
        fetchProduct();

        const handleBeforeUnload = () => {
            clearCartData();
        };

        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [id]);

    if (error) {
        return <p className="error-message">{error}</p>;
    }

    if (!product) {
        return <p className="loading-message">Loading...</p>;
    }

    const handleCancel = () => {
        navigate('/main');
    };

    const handleDeleteReview = async (reviewId) => {
        try {
            await axios.delete(`/reviews/${reviewId}`);
            alert('Review deleted successfully!');
        } catch (error) {
            console.error('Error deleting review:', error);
        }
    };

    const openSidebar = () => {
        setIsSidebarOpen(true);
        fetchReviews();
    };

    const closeSidebar = () => {
        setIsSidebarOpen(false);
    };

    const handleReviewChange = (e) => {
        const { name, value } = e.target;
        setReview((prevReview) => ({
            ...prevReview,
            [name]: value,
        }));
    };


    const openReviewModal = () => {
        setIsReviewModalOpen(true);
    };

    const closeReviewModal = () => {
        setIsReviewModalOpen(false);
    };

    return (
        <div className="product-page">
            <h1>Product Details</h1>

            <div className="product-main-info">
                <div className="product-form">
                    <label>
                        Name:
                        <p>{product.name}</p>
                    </label>
                    <label>
                        Price:
                        <p>{product.price}</p>
                    </label>
                </div>

                <div className="product-description">
                    <label>
                        Description:
                        <p className="lol">{product.description}</p>
                    </label>
                </div>
            </div>

            <div className="promotions-section">
                <h2>Promotions</h2>
                <div className="table-scroll">
                    <table className="table-layout">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Discount</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                        </tr>
                        </thead>
                        <tbody>
                        {product.promotions?.map((promo) => (
                            <tr key={promo.id}>
                                <td>{promo.name}</td>
                                <td>{promo.discount}%</td>
                                <td>{promo.startDate}</td>
                                <td>{promo.endDate}</td>
                            </tr>
                        )) || (
                            <tr>
                                <td colSpan="4">No promotions available</td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>

            </div>

            <div className="categories-section">
                <h2>Categories</h2>
                <div className="categories-container">
                    {product.category?.map((cat) => (
                        <div key={cat.id} className="category-box">
                            #{cat.name}
                        </div>
                    )) || "None"}
                </div>
            </div>

            <div className="sidebar-button-container">
                <button onClick={openSidebar} className="open-sidebar-btn">
                    Reviews
                </button>
            </div>

            {isSidebarOpen && (
                <div className="sidebar-window">
                    <div className="sidebar-content-wrapper">
                        <div className="review-buttons">
                            <button
                                className={`review-button ${hasReviewed ? "reviewed" : ""}`}
                                onClick={openReviewModal}
                                disabled={hasReviewed}
                            >
                                {hasReviewed ? "You've already reviewed this product" : "Give a review"}
                            </button>
                            <button
                                className="delete-review-button"
                                onClick={handleDeleteReview}
                            >
                                Delete a review
                            </button>
                        </div>

                        <div className="reviews-container">
                            {loading ? (
                                <p>Loading reviews...</p>
                            ) : reviews.length > 0 ? (
                                reviews.map((review) => (
                                    <div key={review.id} className="review-bubble">
                                        <strong>{review.user}</strong>

                                        <div className="star-rating">
                                            {[1, 2, 3, 4, 5].map((star) => (
                                                <span
                                                    key={star}
                                                    className={`star ${star <= review.rating ? "filled" : ""}`}
                                                >
                                        &#9733;
                                    </span>
                                            ))}
                                        </div>

                                        <p>{review.comment}</p>
                                    </div>
                                ))
                            ) : (
                                <p className="no-reviews">No reviews yet.</p>
                            )}
                        </div>

                        <button className="close-sidebar-btn" onClick={closeSidebar}>
                            Cancel
                        </button>
                    </div>
                </div>
            )}

            {isReviewModalOpen && (
                <div className="edit-modal-overlay">
                    <div className="edit-modal">
                        <div className="edit-modal-content">
                            <h3>Write a Review</h3>
                            <label>
                                Rating:
                                <input
                                    type="number"
                                    name="rating"
                                    value={review.rating}
                                    onChange={handleReviewChange}
                                    min="1"
                                    max="5"
                                    step="1"
                                    placeholder="Rate (1-5)"
                                    required
                                />
                            </label>
                            <label>
                                Comment:
                                <textarea
                                    name="comment"
                                    value={review.comment}
                                    onChange={handleReviewChange}
                                    placeholder="Write your review here"
                                />
                            </label>
                            <div className="edit-modal-actions">
                                <button onClick={handleAddReview}>Submit</button>
                                <button onClick={closeReviewModal}>Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
            )}


            <div className="action-buttons">
                <div className="test">
                    <input
                        type="text"
                        className="quantity-input"
                        value={quantity}
                        placeholder="1"
                        onChange={handleQuantityChange}
                    />
                    <span className="max-text">Max:</span>
                    <span className="stock-text">{product.stock}</span>
                </div>

                <div className="buttons-container">
                    {product.stock === 0 ? (
                        <button className="out-of-stock-button" disabled>
                            Out of Stock
                        </button>
                    ) : (
                        <button
                            className="add-to-cart-button"
                            onClick={() => handleAddToCart(product, quantity)}
                        >
                            Add to Cart
                        </button>
                    )}
                    <button className="back-to-shop-button" onClick={handleCancel}>
                        Back to Shop
                    </button>
                </div>
            </div>
        </div>
    );



};

export default ProductBuy;
