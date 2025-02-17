import React, { useState } from 'react';
import './Login.css';
import axios from './AxiosConfig';

const Login = () => {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage('');

        if (!login.trim() || !password.trim()) {
            setErrorMessage('Email and password cannot be empty.');
            return;
        }

        try {
            const response = await axios.post('/authentication/login', {
                login,
                password,
            });

            if (response.status === 200 && response.data.token) {
                localStorage.setItem('token', response.data.token);
                window.location.href = '/main';
            } else {
                setErrorMessage(response.data.message || 'Invalid login credentials.');
            }
        } catch (error) {
            console.error(error);
            if (error.response && error.response.status === 401) {
                setErrorMessage('Incorrect email or password.');
            } else {
                setErrorMessage('An error occurred while processing your request.');
            }
        }
    };

    const handleGoHome = () => {
        window.location.href = '/';
    };

    return (
        <div className="login-container">
            <form onSubmit={handleSubmit}>
                <h2>Login</h2>
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
                    onCopy={(e) => e.preventDefault()}
                    onPaste={(e) => e.preventDefault()}
                    onSelectStart={(e) => e.preventDefault()}
                />
                {errorMessage && <p className="error-message">{errorMessage}</p>}
                <button className="login_button">Login</button>
                <button type="button" onClick={handleGoHome} className="cancel_button">
                    Cancel
                </button>
            </form>
        </div>
    );
};

export default Login;
