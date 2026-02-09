import React from 'react';

function Dashboard({ user, onLogout }) {
  return (
    <div className='dashboard-container'>
      <div className='dashboard-card'>
        <div className='dashboard-header'>
          <h1 className='dashboard-title'> Welcome! </h1>
          <p className='dashboard-subtitle'>Sweet as Candyland</p>
        </div>

        <div className='user-info'>
          <div className='user-info-item'>
            <div className='user-info-label'>Name</div>
            <div className='user-info-value'>
              {user?.firstName} {user?.lastName}
            </div>
          </div>

          <div className='user-info-item'>
            <div className='user-info-label'>Email</div>
            <div className='user-info-value'>{user?.email}</div>
          </div>

          <div className='user-info-item'>
            <div className='user-info-label'>User ID</div>
            <div className='user-info-value'>{user?.id}</div>
          </div>
        </div>

        <button onClick={onLogout} className='logout-btn'>
           Logout 
        </button>
      </div>
    </div>
  );
}

export default Dashboard;
