import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from 'react-router-dom';
import "./Wishlist.css";
import axios from "./AxiosConfig";

function FavouritesPage() {
    const [wishlistProducts, setWishlistProducts] = useState([]);
    const navigate = useNavigate();
    const [wishlistId, setWishlistId] = useState(null);

    useEffect(() => {
        const fetchWishlist = async () => {
            try {
                const customerResponse = await axios.get("/customers/me");
                const customerId = customerResponse.data?.id;

                const response = await axios.get(`/wishlists/customer`, { params: { id: customerId } });

                if (response.status !== 200) {
                    throw new Error("Error, wishlist not found.");
                }

                const data = response.data;
                setWishlistId(data.id);
                setWishlistProducts(data.products || []);
            } catch (error) {
                console.error("Error fetching wishlist:", error);
            }
        };

        fetchWishlist();
    }, []);

    const handleRemove = async (productId) => {
        try {

            await axios.delete(`/products/${productId}/wishlist`, { params: { Wid: wishlistId } });

            setWishlistProducts((prevProducts) =>
                prevProducts.filter((product) => product.id !== productId)
            );
        } catch (error) {
            console.error("Error removing product from wishlist:", error);
        }
    };

    const handleCancel = () => {
        navigate('/main');
    };

    return (
        <div className="wishlist-container">
            <h2>Wishlist</h2>
            <div className="wishlist-table-container">
                <div className="wishlist-row">
                    {wishlistProducts.length > 0 ? (
                        wishlistProducts.map((product) => (
                            <div key={product.id} className="wishlist-item">
                                <div
                                    className={`availability-status ${
                                        product.stock > 0 ? "available" : "unavailable"
                                    }`}
                                >
                                    {product.stock > 0 ? "Available" : "Unavailable"}
                                </div>
                                <div className="product-details">
                                    <a
                                        href={`/product-buy/${product.id}`}
                                        className="product-name-link"
                                    >
                                        {product.name}
                                    </a>
                                    <span className="product-price" style={{ marginRight: '8px' }}>{product.price.toFixed(2)} zł</span>
                                </div>

                                <button
                                    className="remove-button"
                                    onClick={() => handleRemove(product.id)}
                                >
                                    Remove from Wishlist
                                </button>
                            </div>
                        ))
                    ) : (
                        <div>Wishlist empty</div>
                    )}
                </div>
            </div>
            <div className="action">
                <button className="cancel-button" onClick={handleCancel}>Cancel</button>
            </div>
        </div>
    );
}

export default FavouritesPage;
