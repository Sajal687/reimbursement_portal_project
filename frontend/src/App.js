import { Routes, Route } from 'react-router-dom';
import { useEffect, useState } from 'react';
import Login from "./Components/Login/Login";
import RegistrationPage from "./Components/Registration/RegistrationPage";
import Admin from './Components/Dashboards/Admin';
import DepartmentPage from './Pages/Department/DepartmentPage';
import CategoryPage from './Pages/Category/CategoryPage';
import EmployeePage from './Pages/Employee/EmployeePage';
import ClaimRequestForm from './Pages/ClaimRequest/ClaimRequestForm';
import ViewClaim from './Pages/ViewClaim/ViewClaim';
import AdminClaimView from './Pages/AdminClaimViewPage/AdminClaimView';
import AssignEmployeeClaims from './Pages/AssignEmployeeClaims/AssignEmployeeClaims';
import EmployeeClaimView from './Pages/EmployeeClaimView/EmployeeClaimView';
import styles from './App.module.css';
import { ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


function App() {
  const [checkLogIn, setCheckLogin] = useState(false);

  useEffect(() => {
    setCheckLogin(JSON.parse(localStorage.getItem('userDetails')) !== null);
  })

  const userDetails = JSON.parse(localStorage.getItem("userDetails"));


  return (
    <div className={styles.app}>
      <ToastContainer
        position='top-right'
        autoClose={2000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme='dark'
      />
      <Routes>
        {
          JSON.parse(localStorage.getItem('userDetails')) !== null ?
            userDetails.designation === "Admin" ?
              <Route path='/admin/*' element={<Admin setCheckLogin={setCheckLogin} />}>
                <Route path='department' element={<DepartmentPage />} />
                <Route path='category' element={<CategoryPage />} />
                <Route path='employee' element={<EmployeePage />} />
                <Route path='claimview' element={<AdminClaimView />} />
              </Route> :
              userDetails.designation === "Employee" ?
                <Route path='/employee/*' element={<Admin setCheckLogin={setCheckLogin} />}>
                  <Route path='claim-request' element={<ClaimRequestForm />} />
                  <Route path='claim' element={<EmployeeClaimView />} />
                </Route> :
                <Route path='/manager/*' element={<Admin setCheckLogin={setCheckLogin} />}>
                  <Route path='claim-request' element={<ClaimRequestForm />} />
                  <Route path='myclaim' element={<ViewClaim />} />
                  <Route path='sde-claim' element={<AssignEmployeeClaims />} />
                </Route> 
            :
            <Route>

              <Route path='/login' element={<Login setCheckLogin={setCheckLogin} />} />
              <Route path='/' element={<Login setCheckLogin={setCheckLogin} />} />
              <Route path='/register' element={<RegistrationPage />} />
            </Route>
        }
        <Route path='*' element={<Login/>}/>
      </Routes>
    </div>
  );
}

export default App;
