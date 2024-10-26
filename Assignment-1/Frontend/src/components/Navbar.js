import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar">
      <ul>
        <li><Link to="/">Create Rule</Link></li>
        <li><Link to="/combine">Combine Rules</Link></li>
        <li><Link to="/evaluate">Evaluate Rule</Link></li>
      </ul>
    </nav>
  );
};

export default Navbar;
