import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';
import axios from './AxiosConfig';

const Register = () => {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('/authentication/register-user', {
                login,
                password
            });

            if (response.status === 200) {
                alert('Registration successful!');
                setErrorMessage('');
                navigate('/login');
            } else {
                setErrorMessage(response.data.message || 'Registration failed.');
            }
        } catch (error) {
            if (error.response && error.response.data && error.response.data.message) {
                setErrorMessage(error.response.data.message);
            } else {
                setErrorMessage('An error occurred. Please try again later.');
            }
        }
    };

    const handleGoHome = () => {
        navigate('/');
    };

    return (
        <div className="container">
            <form onSubmit={handleSubmit}>
                <h2>Register</h2>
                <input
                    type="text"
                    placeholder="Email"
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit" className="submit-button">Register</button>

                <button type="button" onClick={handleGoHome} className="cancel-button">
                    Cancel
                </button>
            </form>

            {errorMessage && <div className="error-message">{errorMessage}</div>}
        </div>
    );
};

export default Register;
