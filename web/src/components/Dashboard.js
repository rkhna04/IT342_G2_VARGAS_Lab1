import React from 'react';

function Dashboard({ user, onLogout, onShowProfile }) {
  return (
    <div className='dashboard-main-container'>
      {/* Header with Profile and Logout buttons in upper-right */}
      <header className='dashboard-header-nav'>
        <div className='dashboard-header-left'>
          <h1 className='dashboard-brand'>User Management System</h1>
        </div>
        <div className='dashboard-header-right'>
          <button onClick={onShowProfile} className='profile-btn' type='button'>
            Profile
          </button>
          <button onClick={onLogout} className='logout-btn-nav' type='button'>
            Logout
          </button>
        </div>
      </header>

      <div className='dashboard-content-wrapper'>
        {/* Dashboard Section */}
        <div className='dashboard-section'>
          <div className='dashboard-card-new'>
            <h2 className='dashboard-section-title'>Dashboard</h2>
            <div className='dashboard-blank-content'>
              <p className='dashboard-welcome-text'>Welcome to your dashboard, {user?.firstName}!</p>
              <div className='dashboard-placeholder'>
                {/* Blank dashboard content area */}
                <div className='placeholder-box'>Dashboard Content Area</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
