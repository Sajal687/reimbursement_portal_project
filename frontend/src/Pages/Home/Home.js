import React from 'react';
import { FaSignInAlt, FaUserPlus } from 'react-icons/fa';
import styles from './home.module.css';
import {Link} from 'react-router-dom';

const HomePage = ()  => {
  return (
    <div className={styles.homePage}>
      <div className={styles.container}>
        <h1 style={{color: "black"}}>Welcome to the Reimbursement Portal</h1>
        <div className={styles.buttonContainer}>
          <Link className={styles.loginButton} to={'/login'}>
             Login <FaSignInAlt />
          </Link>
          <Link className={styles.registerButton} to={'/register'}>
            Register <FaUserPlus />
          </Link>
        </div>
      </div>
    </div>
  );
}

export default HomePage;

        