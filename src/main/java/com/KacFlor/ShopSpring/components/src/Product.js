import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from './AxiosConfig';
import './Product.css';

const Product = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [product, setProduct] = useState({
        name: "",
        price: 0,
        description: "",
        stock: 0
    });

    const [selectedPromotions, setSelectedPromotions] = useState([]);
    const [selectedSuppliers, setSelectedSuppliers] = useState([]);
    const [selectedCategories, setSelectedCategories] = useState([]);

    const [promotions, setPromotions] = useState([]);
    const [suppliers, setSuppliers] = useState([]);
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isSupplierModalOpen, setIsSupplierModalOpen] = useState(false);
    const [isCategoryModalOpen, setIsCategoryModalOpen] = useState(false);
    const openModal = () => setIsModalOpen(true);
    const closeModal = () => setIsModalOpen(false);
    const openSupplierModal = () => setIsSupplierModalOpen(true);
    const closeSupplierModal = () => setIsSupplierModalOpen(false);
    const openCategoryModal = () => setIsCategoryModalOpen(true);
    const closeCategoryModal = () => setIsCategoryModalOpen(false);

    const [newPromotion, setNewPromotion] = useState({
        name: '',
        description: '',
        startDate: '',
        endDate: '',
        discount: 0
    });
    const [newSupplier, setNewSupplier] = useState({
        name: '',
        active: true
    });
    const [newCategory, setNewCategory] = useState({
        name: '',
        active: true
    });

    const fetchPromotions = async () => {
        try {
            const response = await axios.get('/promotions');
            setPromotions(response.data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchSuppliers = async () => {
        try {
            const response = await axios.get('/suppliers');
            setSuppliers(response.data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchCategories = async () => {
        try {
            const response = await axios.get('/categories');
            setCategories(response.data);
        } catch (err) {
            console.error(err);
        }
    };

    const handleCheckboxChange = (id, selectedList, setSelectedList, isChecked) => {
        if (isChecked) {
            setSelectedList(prevList => {
                if (!prevList.includes(id)) {
                    return [...prevList, id];
                }
                return prevList;
            });
        } else {
            setSelectedList(prevList => {
                return prevList.filter(item => item !== id);
            });
        }
    };

    const handleAddPromotion = async () => {
        try {
            await axios.post('/promotions/new', newPromotion);
            alert('Promotion added successfully!');
            closeModal();
            fetchPromotions();
        } catch (err) {
            alert('Error adding promotion.');
            console.error(err);
        }
    };

    const handleAddSupplier = async () => {
        try {
            await axios.post('/suppliers/new', newSupplier);
            alert('Supplier added successfully!');
            closeSupplierModal();
            fetchSuppliers();
        } catch (err) {
            alert('Error adding supplier.');
            console.error(err);
        }
    };

    const handleAddCategory = async () => {
        try {
            await axios.post('/categories/new', newCategory);
            alert('Category added successfully!');
            closeCategoryModal();
            fetchCategories();
        } catch (err) {
            alert('Error adding category.');
            console.error(err);
        }
    };

    const handleIncrement = (field) => {
        setProduct({ ...product, [field]: product[field] + 1 });
    };

    const handleDecrement = (field) => {
        setProduct({ ...product, [field]: Math.max(0, product[field] - 1) });
    };

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const response = await axios.get(`/products/${id}`);
                setProduct(response.data);
                if (response.data) {
                    if (response.data.category) {
                        setSelectedCategories(response.data.category.map(cat => cat.id));
                    } else {
                        setSelectedCategories([]);
                    }

                    if (response.data.promotions) {
                        setSelectedPromotions(response.data.promotions.map(promo => promo.id));
                    } else {
                        setSelectedPromotions([]);
                    }

                    if (response.data.supplier) {
                        setSelectedSuppliers([response.data.supplier.id]);
                    } else {
                        setSelectedSuppliers([]);
                    }
                }
            } catch (err) {
                setError('Error fetching product details.');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchProduct();
        fetchPromotions();
        fetchSuppliers();
        fetchCategories();
    }, [id]);


    const handleInputChange = (e) => {
        const { name, value } = e.target;

        if (name === 'description' && value.length > 2200) return;

        setProduct({ ...product, [name]: value });
    };

    const handleSaveChanges = async () => {
        try {
            const response = await axios.get(`/products/${id}`);
            const currentProduct = response.data;

            let totalDiscountPercentage = 0;
            let currentPromotions = currentProduct.promotions || [];
            let promotionsToRemove = [];
            let promotionsToAdd = [];

            if (selectedPromotions.length > 0) {
                for (const PNid of selectedPromotions) {
                    const promotionResponse = await axios.get(`/promotions/${PNid}`);
                    const promotion = promotionResponse.data;

                    if (promotion.discount) {
                        totalDiscountPercentage += promotion.discount;
                        if (!currentPromotions.some((p) => p.id === promotion.id)) {
                            promotionsToAdd.push(promotion);
                        }
                    }
                }
            }

            for (const promotion of currentPromotions) {
                if (!selectedPromotions.includes(promotion.id)) {
                    promotionsToRemove.push(promotion);
                }
            }

            let basePrice = parseFloat(product.price);
            let finalPrice = basePrice;

            for (const promotion of promotionsToRemove) {
                finalPrice /= (1 - promotion.discount / 100);
            }

            for (const promotion of promotionsToAdd) {
                finalPrice *= (1 - promotion.discount / 100);
            }

            const updatedProduct = {
                ...currentProduct,
                name: product.name,
                price: parseFloat(finalPrice.toFixed(2)),
                stock: parseInt(product.stock, 10),
                description: product.description,
            };

            await axios.patch(`/products/${id}`, updatedProduct);
            alert(`Product updated.`);

            if (currentProduct.promotions && currentProduct.promotions.length > 0) {
                for (const promotion of currentProduct.promotions) {
                    await axios.delete(`/products/${id}/promotion`, {
                        params: { PNid: promotion.id },
                    });
                }
            }

            if (currentProduct.category && currentProduct.category.length > 0) {
                for (const category of currentProduct.category) {
                    await axios.delete(`/products/${id}/category`, {
                        params: { CYid: category.id },
                    });
                }
            }

            if (currentProduct.supplier) {
                const supplier = currentProduct.supplier;
                await axios.delete(`/products/${id}/supplier`, {
                    params: { Sid: supplier.id },
                });
            }

            for (const PNid of selectedPromotions) {
                await axios.post(`/products/${id}/promotion`, null, {
                    params: { PNid },
                });
            }

            for (const CYid of selectedCategories) {
                await axios.post(`/products/${id}/category`, null, {
                    params: { CYid },
                });
            }

            if (selectedSuppliers.length > 0) {
                const Sid = selectedSuppliers[0];
                await axios.post(`/products/${id}/supplier`, null, {
                    params: { Sid },
                });
            }

            navigate('/main');
        } catch (err) {
            alert('Błąd podczas zapisywania zmian.');
            console.error(err);
        }
    };


    const handleSupplierCheckboxChange = (id, isChecked) => {
        if (isChecked) {
            setSelectedSuppliers(prevList => {
                if (!prevList.includes(id)) {
                    return [id];
                }
                return prevList;
            });
        } else {
            setSelectedSuppliers([]);
        }
    };

    if (loading) return <div>Loading product details...</div>;
    if (error) return <div>{error}</div>;

    const handleCancel = () => {
        navigate('/main');
    };

    return (
        <div className="edit-product-page">
            <h1>Edit Product</h1>
            <div className="edit-product-main-info">
                <form className="edit-product-form">
                    <label>
                        Name:
                        <input
                            type="text"
                            name="name"
                            value={product.name}
                            onChange={handleInputChange}
                        />
                    </label>
                    <label>
                        Price:
                        <div className="edit-quantity-control">
                            <input
                                type="number"
                                name="price"
                                value={product.price}
                                onChange={handleInputChange}
                                step="0.01"
                                min="0"
                            />
                            <button type="button" onClick={() => handleIncrement('price')}>+</button>
                            <button type="button" onClick={() => handleDecrement('price')}>-</button>
                        </div>
                    </label>

                    <label>
                        Stock:
                        <div className="edit-quantity-control">
                            <input
                                type="number"
                                name="stock"
                                value={product.stock}
                                onChange={handleInputChange}
                            />
                            <button type="button" onClick={() => handleIncrement('stock')}>+</button>
                            <button type="button" onClick={() => handleDecrement('stock')}>-</button>
                        </div>
                    </label>
                </form>
                <div className="edit-product-description">
                    <label>
                        Description:
                        <textarea
                            name="description"
                            value={product.description}
                            onChange={handleInputChange}
                            maxLength={255}
                        />
                    </label>
                </div>
            </div>

            <div className="edit-tables-section">
                <div className="edit-promotions-section">
                    <h2>Promotions</h2>
                    <button className="edit-add-button" onClick={openModal}>Add New Promotion</button>
                    <div className="edit-table-scroll">
                        <table className="edit-table-layout">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Discount</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Active</th>
                            </tr>
                            </thead>
                            <tbody>
                            {promotions.map((promo) => (
                                <tr key={promo.id}>
                                    <td>{promo.name}</td>
                                    <td>{promo.discount}%</td>
                                    <td>{promo.startDate}</td>
                                    <td>{promo.endDate}</td>
                                    <td>
                                        <input
                                            type="checkbox"
                                            checked={selectedPromotions.includes(promo.id)}
                                            onChange={(e) =>
                                                handleCheckboxChange(promo.id, selectedPromotions, setSelectedPromotions, e.target.checked)
                                            }
                                        />
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                <div className="edit-suppliers-section">
                    <h2>Suppliers</h2>
                    <button className="edit-add-button" onClick={openSupplierModal}>Add New Supplier</button>
                    <div className="edit-table-scroll">
                        <table className="edit-table-layout">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Active</th>
                            </tr>
                            </thead>
                            <tbody>
                            {suppliers.map((supplier) => (
                                <tr key={supplier.id}>
                                    <td>{supplier.name}</td>
                                    <td>
                                        <input
                                            type="checkbox"
                                            checked={selectedSuppliers.includes(supplier.id)}
                                            disabled={selectedSuppliers.length > 0 && !selectedSuppliers.includes(supplier.id)}
                                            onChange={(e) =>
                                                handleSupplierCheckboxChange(supplier.id, e.target.checked)
                                            }
                                        />
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                <div className="edit-categories-section">
                    <h2>Categories</h2>
                    <button className="edit-add-button" onClick={openCategoryModal}>Add New Category</button>
                    <div className="edit-table-scroll">
                        <table className="edit-table-layout">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Active</th>
                            </tr>
                            </thead>
                            <tbody>
                            {categories.map((category) => (
                                <tr key={category.id}>
                                    <td>{category.name}</td>
                                    <td>
                                        <input
                                            type="checkbox"
                                            checked={selectedCategories.includes(category.id)}
                                            onChange={(e) =>
                                                handleCheckboxChange(category.id, selectedCategories, setSelectedCategories, e.target.checked)
                                            }
                                        />
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>

            {isModalOpen && (
                <div className="edit-modal">
                    <div className="edit-modal-content">
                        <h3>Add New Promotion</h3>
                        <label>
                            Name:
                            <input
                                type="text"
                                value={newPromotion.name}
                                onChange={(e) => setNewPromotion({...newPromotion, name: e.target.value})}
                                placeholder="Enter promotion name"
                            />
                        </label>
                        <label>
                            Start Date:
                            <input
                                type="date"
                                value={newPromotion.startDate}
                                onChange={(e) => setNewPromotion({...newPromotion, startDate: e.target.value})}
                            />
                        </label>
                        <label>
                            End Date:
                            <input
                                type="date"
                                value={newPromotion.endDate}
                                onChange={(e) => setNewPromotion({...newPromotion, endDate: e.target.value})}
                            />
                        </label>
                        <label>
                            Discount:
                            <input
                                type="number"
                                value={newPromotion.discount}
                                onChange={(e) => setNewPromotion({...newPromotion, discount: e.target.value})}
                                placeholder="Enter discount percentage"
                            />
                        </label>
                        <div className="edit-modal-actions">
                            <button onClick={handleAddPromotion}>Save</button>
                            <button onClick={closeModal}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}

            {isSupplierModalOpen && (
                <div className="edit-modal">
                    <div className="edit-modal-content">
                        <h3>Add New Supplier</h3>
                        <label>
                            Name:
                            <input
                                type="text"
                                value={newSupplier.name}
                                onChange={(e) => setNewSupplier({...newSupplier, name: e.target.value})}
                                placeholder="Enter supplier name"
                            />
                        </label>
                        <div className="edit-modal-actions">
                            <button onClick={handleAddSupplier}>Save</button>
                            <button onClick={closeSupplierModal}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}

            {isCategoryModalOpen && (
                <div className="edit-modal">
                    <div className="edit-modal-content">
                        <h3>Add New Category</h3>
                        <label>
                            Name:
                            <input
                                type="text"
                                value={newCategory.name}
                                onChange={(e) => setNewCategory({...newCategory, name: e.target.value})}
                                placeholder="Enter category name"
                            />
                        </label>
                        <div className="edit-modal-actions">
                            <button onClick={handleAddCategory}>Save</button>
                            <button onClick={closeCategoryModal}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}

            <div className="edit-action-buttons">
                <button className="edit-submit-button" onClick={handleSaveChanges}>Submit</button>
                <button className="edit-cancel-button" onClick={handleCancel}>Cancel</button>
            </div>
        </div>
    );
};

export default Product;
