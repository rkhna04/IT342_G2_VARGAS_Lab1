import React, { useState } from 'react';
import authService from '../services/authService';

function Profile({ user, onUpdateUser, onBack }) {
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    firstName: user?.firstName || '',
    lastName: user?.lastName || '',
    email: user?.email || '',
    phone: user?.phone || '',
    gender: user?.gender || '',
    age: user?.age ?? '',
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
      const updatedUser = await authService.updateProfile({
        firstName: formData.firstName,
        lastName: formData.lastName,
        email: formData.email,
        phone: formData.phone || null,
        gender: formData.gender || null,
        age: formData.age !== '' ? Number(formData.age) : null,
      });
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
        <div className='profile-nav-title'>My Profile</div>
        <button onClick={onBack} className='back-btn'>
          ← Back to Dashboard
        </button>
      </div>

      <div className='profile-page-content'>
        
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

          <form className='profile-form' onSubmit={handleSubmit}>
            <div className='profile-sections'>
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
              <h3 className='profile-section-title'>Additional Information</h3>
            
              <div className='profile-form-row'>
                <div className='profile-form-group'>
                  <label className='profile-label'>Gender</label>
                  {isEditing ? (
                    <input
                      type='text'
                      name='gender'
                      className='profile-input'
                      value={formData.gender}
                      onChange={handleChange}
                      placeholder='Enter gender'
                    />
                  ) : (
                    <div className='profile-display-value'>{user?.gender || 'Not set'}</div>
                  )}
                </div>

                <div className='profile-form-group'>
                  <label className='profile-label'>Age</label>
                  {isEditing ? (
                    <input
                      type='number'
                      name='age'
                      className='profile-input'
                      value={formData.age}
                      onChange={handleChange}
                      placeholder='Enter age'
                      min='0'
                      max='150'
                    />
                  ) : (
                    <div className='profile-display-value'>{user?.age ?? 'Not set'}</div>
                  )}
                </div>
              </div>
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
          </form>
        </div>
      </div>
    </div>
  );
}

export default Profile;
