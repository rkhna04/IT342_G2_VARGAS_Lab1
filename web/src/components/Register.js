import React, { useState } from 'react';
import authService from '../services/authService';

function Register({ onSwitchToLogin }) {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

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
    setSuccess('');

    // Validation
    if (!formData.firstName || !formData.lastName || !formData.email || !formData.password) {
      setError('All fields are required');
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    if (formData.password.length < 6) {
      setError('Password must be at least 6 characters long');
      return;
    }

    setLoading(true);

    try {
      await authService.register(
        formData.firstName,
        formData.lastName,
        formData.email,
        formData.password
      );
      setSuccess('Registration successful! Please login.');
      setTimeout(() => onSwitchToLogin(), 2000);
    } catch (err) {
      setError(err.message || 'Registration failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className='auth-form' onSubmit={handleSubmit}>
      <h1 className='form-title'> Register </h1>
      
      {error && <div className='message error-message'>{error}</div>}
      {success && <div className='message success-message'>{success}</div>}

      <div className='name-row'>
        <div className='form-group'>
          <label className='form-label'>First Name</label>
          <input
            type='text'
            name='firstName'
            className='form-input'
            placeholder='Enter your first name'
            value={formData.firstName}
            onChange={handleChange}
            disabled={loading}
          />
        </div>

        <div className='form-group'>
          <label className='form-label'>Last Name</label>
          <input
            type='text'
            name='lastName'
            className='form-input'
            placeholder='Enter your last name'
            value={formData.lastName}
            onChange={handleChange}
            disabled={loading}
          />
        </div>
      </div>

      <div className='form-group'>
        <label className='form-label'>Email</label>
        <input
          type='email'
          name='email'
          className='form-input'
          placeholder='Enter your email'
          value={formData.email}
          onChange={handleChange}
          disabled={loading}
        />
      </div>

      <div className='form-group'>
        <label className='form-label'>Password</label>
        <input
          type='password'
          name='password'
          className='form-input'
          placeholder='Enter your password'
          value={formData.password}
          onChange={handleChange}
          disabled={loading}
        />
      </div>

      <div className='form-group'>
        <label className='form-label'>Confirm Password</label>
        <input
          type='password'
          name='confirmPassword'
          className='form-input'
          placeholder='Confirm your password'
          value={formData.confirmPassword}
          onChange={handleChange}
          disabled={loading}
        />
      </div>

      <button type='submit' className='btn btn-primary' disabled={loading}>
        {loading ? 'Creating Account...' : 'Register'}
      </button>

      <div className='switch-auth'>
        Already have an account?{' '}
        <button type='button' onClick={onSwitchToLogin}>
          Login here
        </button>
      </div>
    </form>
  );
}

export default Register;
