import React, { useEffect, useState } from 'react'
import styles from './admin.module.css';
import { FaUser, FaBuilding, FaTag, FaUsers, FaSignOutAlt } from 'react-icons/fa';
import { Link, useNavigate, Outlet } from 'react-router-dom';


const Admin = ({ setCheckLogin }) => {
  const navigate = useNavigate();
  const [userRole, setUserRole] = useState('');
  const [userName, setUserName] = useState('');

  let activePath = "";
  let path = window.location.pathname
  if (JSON.parse(localStorage.getItem("userDetails")).designation === "Admin")
    activePath = path.slice(7, path.length);


  if (JSON.parse(localStorage.getItem("userDetails")).designation === "Employee")
    activePath = path.slice(10, path.length);

  if (JSON.parse(localStorage.getItem("userDetails")).designation === "Manager")
    activePath = path.slice(9, path.length);

  useEffect(() => {
    const userDetails = JSON.parse(localStorage.getItem("userDetails"));
    setUserName(userDetails?.name);
    if (userDetails?.designation === 'Admin') {
      setUserRole('Admin');
    }
    else if (userDetails?.designation === 'Employee') {
      setUserRole('Employee');
    }
    else {
      setUserRole('Manager');
    }
  }, [])

  const handleLogoutBtnClick = () => {
    localStorage.clear();
    setCheckLogin(false);
    navigate('/login')
  }

  return (
    <>
      <div className={styles.container}>
        <aside className={styles.sidebar}>
          <div className={styles.portalName}>
            <span>Reimbursement Portal</span>
          </div>
          <div className={styles.avatar}>
            <FaUser className={styles.avatarIcon} />
            {userName && (<h1 className={styles.heading}>{userName}</h1>)}
          </div>

          {userRole === 'Admin' ?
            (
              <nav className={styles.navCont}>
                <ul className={styles.nav}>
                  <li className={activePath === "employee" ? styles.activeLink : styles.navItems}>
                    <Link to={'employee'}>
                      <FaUsers /> Employee
                    </Link>
                  </li>
                  <li className={activePath === "claimview" ? styles.activeLink : styles.navItems}>
                    <Link to={'claimview'}>
                      <FaUsers /> Claim Requests
                    </Link>
                  </li>
                  <li className={activePath === "department" ? styles.activeLink : styles.navItems}>
                    <Link to={'department'}>
                      <FaBuilding /> Department
                    </Link>
                  </li>
                  <li className={activePath === "category" ? styles.activeLink : styles.navItems}>
                    <Link to={'category'}>
                      <FaTag /> Category
                    </Link>
                  </li>
                </ul>
              </nav>
            ) :
            userRole === 'Employee' ?
              (
                <nav className={styles.navCont}>
                  <ul className={styles.nav}>
                    <li className={activePath === "claim" ? styles.activeLink : styles.navItems}>
                      <Link to={'claim'}>
                        <FaTag /> My Claims
                      </Link>
                    </li>
                    <li className={activePath === "claim-request" ? styles.activeLink : styles.navItems}>
                      <Link to={'claim-request'}>
                        <FaBuilding /> Add Claim
                      </Link>
                    </li>
                  </ul>
                </nav>
              )
              :
              (
                <nav className={styles.navCont}>
                  <ul className={styles.nav}>
                    <li className={activePath === "myclaim" ? styles.activeLink : styles.navItems}>
                      <Link to={'myclaim'}>
                        <FaTag /> My Claims
                      </Link>
                    </li>
                    <li className={activePath === "sde-claim" ? styles.activeLink : styles.navItems}>
                      <Link to={'sde-claim'}>
                        <FaTag /> SDE's Claims
                      </Link>
                    </li>
                    <li className={activePath === "claim-request" ? styles.activeLink : styles.navItems}>
                      <Link to={'claim-request'}>
                        <FaBuilding /> Add Claim
                      </Link>
                    </li>
                  </ul>
                </nav>
              )
          }

          <div className={styles.bottom}>
            <span className={styles.userRole}>[{userRole}]</span>
            <div className={styles.logoutBtn} onClick={handleLogoutBtnClick}>
              Logout <span><FaSignOutAlt /></span>
            </div>
          </div>
        </aside>
        <div className={styles.wrapper}>
          <div className={styles.main}>
            <Outlet />
          </div>
        </div>
      </div>
    </>
  )
}

export default Admin;