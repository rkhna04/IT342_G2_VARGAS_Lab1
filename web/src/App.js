import React, { useState, useEffect } from 'react';
import './App.css';
import Register from './components/Register';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import Profile from './components/Profile';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState('login');
  const [showProfile, setShowProfile] = useState(false);
  const [showLogoutModal, setShowLogoutModal] = useState(false);

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
    console.log('Logout button clicked');
    setShowLogoutModal(true);
  };

  const confirmLogout = () => {
    console.log('Logout confirmed: Yes');
    try {
      localStorage.removeItem('authToken');
      localStorage.removeItem('user');
      setIsAuthenticated(false);
      setUser(null);
      setCurrentPage('login');
      setShowProfile(false);
      setShowLogoutModal(false);
      console.log('Logout successful');
    } catch (error) {
      console.error('Logout error:', error);
    }
  };

  const cancelLogout = () => {
    console.log('Logout cancelled: No');
    setShowLogoutModal(false);
  };

  const handleShowProfile = () => {
    setShowProfile(true);
  };

  const handleBackToDashboard = () => {
    setShowProfile(false);
  };

  const handleProfileUpdate = (updatedUser) => {
    setUser(updatedUser);
  };

  if (loading) {
    return <div className='loading-container'><div className='loading-spinner'></div></div>;
  }

  return (
    <div className='App'>
      {showLogoutModal && (
        <div className='modal-overlay'>
          <div className='modal-content'>
            <h3>Confirm Logout</h3>
            <p>Are you sure you want to logout?</p>
            <div className='modal-buttons'>
              <button className='btn-yes' onClick={confirmLogout}>Yes</button>
              <button className='btn-no' onClick={cancelLogout}>No</button>
            </div>
          </div>
        </div>
      )}
      
      <div className='background-decoration'>
        <div className='candy-bg'></div>
      </div>
      
      {isAuthenticated ? (
        showProfile ? (
          <Profile user={user} onBack={handleBackToDashboard} onUpdateUser={handleProfileUpdate} />
        ) : (
          <Dashboard user={user} onLogout={handleLogout} onShowProfile={handleShowProfile} />
        )
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
