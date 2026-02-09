import React, { useState } from 'react';
import authService from '../services/authService';

function Login({ onLoginSuccess, onSwitchToRegister }) {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [error, setError] = useState('');
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

    if (!formData.email || !formData.password) {
      setError('Email and password are required');
      return;
    }

    setLoading(true);

    try {
      const data = await authService.login(formData.email, formData.password);
      onLoginSuccess(data.user);
    } catch (err) {
      setError(err.message || 'Login failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className='auth-form' onSubmit={handleSubmit}>
      <h1 className='form-title'> Login </h1>

      {error && <div className='message error-message'>{error}</div>}

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

      <button type='submit' className='btn btn-primary' disabled={loading}>
        {loading ? 'Logging in...' : 'Login'}
      </button>

      <div className='switch-auth'>
        Don't have an account?{' '}
        <button type='button' onClick={onSwitchToRegister}>
          Register here
        </button>
      </div>
    </form>
  );
}

export default Login;
