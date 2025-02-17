import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Home.css';

const Home = () => {
    const navigate = useNavigate();

    return (
        <div className="home-container">
            <div className="options-container">
                <h1>Welcome to the OurStore.pl</h1>
                <p>Please choose an option:</p>
                <button className={'buttons'} onClick={() => navigate('/login')}>Login</button>
                <button className={'buttons'} onClick={() => navigate('/register')}>Register</button>
            </div>
        </div>
    );
};

export default Home;
