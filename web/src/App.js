import React, { useState, useEffect } from 'react';
import './App.css';
import Register from './components/Register';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import ProtectedRoute from './components/ProtectedRoute';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState('login');

  useEffect(() => {
    // Check if user is logged in
    const token = localStorage.getItem('authToken');
    if (token) {
      setIsAuthenticated(true);
      const userData = localStorage.getItem('user');
      if (userData) {
        setUser(JSON.parse(userData));
      }
      setCurrentPage('dashboard');
    }
    setLoading(false);
  }, []);

  const handleLoginSuccess = (userData) => {
    setIsAuthenticated(true);
    setUser(userData);
    setCurrentPage('dashboard');
  };

  const handleLogout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
    setIsAuthenticated(false);
    setUser(null);
    setCurrentPage('login');
  };

  if (loading) {
    return <div className='loading-container'><div className='loading-spinner'></div></div>;
  }

  return (
    <div className='App'>
      <div className='background-decoration'>
        <div className='candy-bg'></div>
      </div>
      
      {isAuthenticated ? (
        <Dashboard user={user} onLogout={handleLogout} />
      ) : (
        <div className='auth-container'>
          {currentPage === 'register' ? (
            <Register onSwitchToLogin={() => setCurrentPage('login')} />
          ) : (
            <Login onLoginSuccess={handleLoginSuccess} onSwitchToRegister={() => setCurrentPage('register')} />
          )}
        </div>
      )}
    </div>
  );
}

export default App;
