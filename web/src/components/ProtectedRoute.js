import React from 'react';

function ProtectedRoute({ isAuthenticated, children }) {
  if (!isAuthenticated) {
    return <div>Please login to access this page</div>;
  }

  return children;
}

export default ProtectedRoute;
