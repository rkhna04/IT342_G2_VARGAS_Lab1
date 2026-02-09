import React, { useState } from 'react';
import authService from '../services/authService';

function Profile({ user, onUpdateUser, onBack }) {
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    firstName: user?.firstName || '',
    lastName: user?.lastName || '',
    email: user?.email || '',
    phone: user?.phone || '',
    address: user?.address || '',
    city: user?.city || '',
    country: user?.country || '',
  });
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setMessage('');
    setLoading(true);

    try {
      const updatedUser = await authService.updateProfile(formData);
      onUpdateUser(updatedUser);
      setMessage('Profile updated successfully!');
      setIsEditing(false);
    } catch (err) {
      setError(err.message || 'Failed to update profile');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    setFormData({
      firstName: user?.firstName || '',
      lastName: user?.lastName || '',
      email: user?.email || '',
      phone: user?.phone || '',
      address: user?.address || '',
      city: user?.city || '',
      country: user?.country || '',
    });
    setIsEditing(false);
    setError('');
    setMessage('');
  };

  return (
    <div className='profile-page'>
      <div className='profile-page-nav'>
        <button onClick={onBack} className='back-btn'>
          ← Back to Dashboard
        </button>
      </div>

      <div className='profile-page-content'>
        <h1 className='profile-page-title'>My Profile</h1>
        
        {message && <div className='message success-message'>{message}</div>}
        {error && <div className='message error-message'>{error}</div>}

        <div className='profile-page-card'>
          <div className='profile-page-header-section'>
            <div className='profile-avatar'>
              <div className='profile-avatar-circle'>
                {user?.firstName?.charAt(0)}{user?.lastName?.charAt(0)}
              </div>
            </div>
            <div className='profile-basic-info'>
              <h2 className='profile-name'>{user?.firstName} {user?.lastName}</h2>
              <p className='profile-email'>{user?.email}</p>
              <p className='profile-id'>ID: {user?.id}</p>
            </div>
          </div>

          <div className='profile-actions'>
            {!isEditing ? (
              <button onClick={() => setIsEditing(true)} className='edit-profile-btn'>
                Edit Profile
              </button>
            ) : (
              <div className='profile-action-buttons'>
                <button onClick={handleCancel} className='cancel-btn' disabled={loading}>
                  Cancel
                </button>
                <button onClick={handleSubmit} className='save-btn' disabled={loading}>
                  {loading ? 'Saving...' : 'Save Changes'}
                </button>
              </div>
            )}
          </div>

          <form className='profile-form' onSubmit={handleSubmit}>
            <div className='profile-form-section'>
              <h3 className='profile-section-title'>Personal Information</h3>
              
              <div className='profile-form-row'>
                <div className='profile-form-group'>
                  <label className='profile-label'>First Name</label>
                  {isEditing ? (
                    <input
                      type='text'
                      name='firstName'
                      className='profile-input'
                      value={formData.firstName}
                      onChange={handleChange}
                      required
                    />
                  ) : (
                    <div className='profile-display-value'>{user?.firstName || 'Not set'}</div>
                  )}
                </div>

                <div className='profile-form-group'>
                  <label className='profile-label'>Last Name</label>
                  {isEditing ? (
                    <input
                      type='text'
                      name='lastName'
                      className='profile-input'
                      value={formData.lastName}
                      onChange={handleChange}
                      required
                    />
                  ) : (
                    <div className='profile-display-value'>{user?.lastName || 'Not set'}</div>
                  )}
                </div>
              </div>

              <div className='profile-form-group'>
                <label className='profile-label'>Email Address</label>
                {isEditing ? (
                  <input
                    type='email'
                    name='email'
                    className='profile-input'
                    value={formData.email}
                    onChange={handleChange}
                    required
                  />
                ) : (
                  <div className='profile-display-value'>{user?.email || 'Not set'}</div>
                )}
              </div>

              <div className='profile-form-group'>
                <label className='profile-label'>Phone Number</label>
                {isEditing ? (
                  <input
                    type='tel'
                    name='phone'
                    className='profile-input'
                    value={formData.phone}
                    onChange={handleChange}
                    placeholder='Enter phone number'
                  />
                ) : (
                  <div className='profile-display-value'>{user?.phone || 'Not set'}</div>
                )}
              </div>
            </div>

            <div className='profile-form-section'>
              <h3 className='profile-section-title'>Address Information</h3>
              
              <div className='profile-form-group'>
                <label className='profile-label'>Street Address</label>
                {isEditing ? (
                  <input
                    type='text'
                    name='address'
                    className='profile-input'
                    value={formData.address}
                    onChange={handleChange}
                    placeholder='Enter street address'
                  />
                ) : (
                  <div className='profile-display-value'>{user?.address || 'Not set'}</div>
                )}
              </div>

              <div className='profile-form-row'>
                <div className='profile-form-group'>
                  <label className='profile-label'>City</label>
                  {isEditing ? (
                    <input
                      type='text'
                      name='city'
                      className='profile-input'
                      value={formData.city}
                      onChange={handleChange}
                      placeholder='Enter city'
                    />
                  ) : (
                    <div className='profile-display-value'>{user?.city || 'Not set'}</div>
                  )}
                </div>

                <div className='profile-form-group'>
                  <label className='profile-label'>Country</label>
                  {isEditing ? (
                    <input
                      type='text'
                      name='country'
                      className='profile-input'
                      value={formData.country}
                      onChange={handleChange}
                      placeholder='Enter country'
                    />
                  ) : (
                    <div className='profile-display-value'>{user?.country || 'Not set'}</div>
                  )}
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Profile;
