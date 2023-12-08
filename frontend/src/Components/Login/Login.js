import React, { useState, useEffect } from 'react';
import styles from './login.module.css';
import CommonInput from '../../Common/CommonInput';
import CommonButton from '../../Common/CommonButton';
import { useNavigate } from 'react-router-dom';
import { IoPersonCircleOutline } from 'react-icons/io5';
import { toast } from 'react-toastify';
import { postRequest } from '../../Apis/Services/ApiMethods';
import { LOGIN_PATH } from '../../Apis/ApiEndpoints';

const Login = ({ setCheckLogin }) => {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState({});
    const [showPassword , setShowPassword] = useState(false);
    const fieldErrors = {};

    useEffect(() => {
        if (localStorage.getItem('userDetails')) {
            let user = JSON.parse(localStorage.getItem('userDetails'))
            if (user !== null && user.designation === 'Admin') {
              navigate('/admin/employee')
            } else if (user !== null && user.designation === 'Employee') {
              navigate('/employee/claim')
            } else if (user !== null && user.designation === 'Manager') {
              navigate('/manager/myclaim')
            }
          }
    }, [])

    const handleShowPassword = () => {
        setShowPassword(!showPassword);
    }

    const handlePasswordChange = (e) => {
        setPassword(e.target.value)

        if (e.target.value.length < 8) {
            fieldErrors.password = "Password must be length of 8";
        }
        setErrors(fieldErrors)
    }

    const handleEmailChange = (e) => {
        setEmail(e.target.value)
        const emailReg = /^[A-Za-z0-9._%+-]+@nucleusteq.com$/;
        
        if (!emailReg.test(e.target.value))
            fieldErrors.email = "Email must includes Domain Name";
        setErrors(fieldErrors)
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        const encryptedPassword = btoa(password);

        const payload = {
             email: email,
             password: encryptedPassword
        } 

        if (email !== '' && password !== '') {
            postRequest(LOGIN_PATH , payload , null , (response)=>{
                if(response.status === 200){
                    let firstName = response?.data?.name?.split(' ')[0];
                    toast.success(`Welcome ${firstName}`, {
                        position: toast.POSITION.TOP_RIGHT
                    });
                    localStorage.setItem('userDetails', JSON.stringify(response.data));
                    setCheckLogin(true);
                    if (response.data.designation === "Admin")
                        navigate('/admin/employee')
                    else if (response.data.designation === "Employee")
                        navigate('/employee/claim')
                    else if (response.data.designation === "Manager")
                        navigate('/manager/myclaim')
                }else{
                    toast.error(`Invalid Credentials: ${response?.response?.data?.messages}`)
                }
            })
            setErrors({})
            setEmail("");
            setPassword("");
        }
        else {
            toast.info("Please fill in both the fields")
        }
    }

    return (
        <div className={styles.container}>
            <div className={styles.loginContainer}>
                <h1 className={styles.heading}>Reimbursement Portal</h1>
                <IoPersonCircleOutline size={140} style={{ fill: 'white' }} />
                <form onSubmit={handleSubmit} className={styles.loginForm}>
                    <div className={styles.field}>
                        <label>Email <span className={styles.astick}>*</span></label>
                        <CommonInput type='email' className={styles.inputValue} name="username" placeholderText='example@nucleusteq.com' value={email} handleChangeMethod={handleEmailChange} />
                    </div>
                    {errors.email && <span className={styles.loginErrorField}>{errors.email}</span>}

                    <div className={styles.field}>
                        <label>Password <span className={styles.astick}>*</span></label>
                        <CommonInput type={showPassword ? 'text' : 'password'} className={styles.inputValue} placeholderText='Enter your password' value={password} handleChangeMethod={(e) => handlePasswordChange(e)} />
                    </div>
                    {errors.password && <span className={styles.loginErrorField}>{errors.password}</span>}
                    <div className={styles.showPasswordContainer}>
                        <CommonInput type="checkbox" handleChangeMethod={handleShowPassword} className={styles.checkboxBtn}/><span>Show Password</span>
                    </div>

                    <CommonButton type='submit' className={styles.loginBtn}>LogIn</CommonButton>
                </form>
                <div className={styles.signUp}>
                    <p>Don't have an account? <a href='/register'>Sign-Up</a></p>
                </div>
            </div>
        </div>
    )

}

export default Login;