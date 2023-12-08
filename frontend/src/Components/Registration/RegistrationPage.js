import React, { useState, useEffect } from 'react';
import styles from './registrationStyle.module.css';
import CommonInput from '../../Common/CommonInput';
import CommonButton from '../../Common/CommonButton';
import { useNavigate } from 'react-router-dom';
import CommonSelect from '../../Common/Select/CommonSelect';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { getRequest, postRequest } from '../../Apis/Services/ApiMethods';
import { ALL_DEPARTMENTS, REGISTER_PATH } from '../../Apis/ApiEndpoints';

const RegistrationPage = () => {
    const navigate = useNavigate();
    const [userPassword, setUserPassword] = useState("");
    const [departments, setDepartments] = useState([]);
    const [currentDate, setCDate] = useState('');
    const [validationMessages, setValidationMessages] = useState({});

    useEffect(() => {
        let tdate = new Date();
        let tda = String(tdate.getDate()).padStart(2, '0');
        let tmo = String(tdate.getMonth() + 1).padStart(2, '0');
        let tye = tdate.getFullYear();
        let currentDate = `${tye}-${tmo}-${tda}`;
        setCDate(currentDate);

        getRequest(ALL_DEPARTMENTS, null, (response) => {
            if (response.status === 200) {
                setDepartments(response.data)
            } else {
                console.error(response);
            }
        })
    }, [])

    const [formData, setFormData] = useState({
        name: '',
        email: '',
        dateOfJoining: '',
        department: {},
        designation: '',
        secretQuestion: '',
        secretAnswer: '',
        password: '',
        confirmPassword: '',
    });

    const handleDepartmentChange = (e) => {
        let { name, value } = e.target;
        let obj = {
            id: e.target.value
        }
        setFormData({ ...formData, department: obj })
        validateFields(name, value);
    }



    const validateFields = (forName, forValue) => {
        const nameRegex = /^[A-Za-z]+[A-Za-z ]*$/;
        const nameRegexOnlyForAlphabets = /^[a-zA-Z ]*$/;
        const emailPattern = /^[A-Za-z0-9._%+-]+@nucleusteq.com$/;

        const newValidationMessages = { ...validationMessages };
        Object.keys(newValidationMessages).forEach(key => {
            if (newValidationMessages[key] === null) {
                delete newValidationMessages[key];
            }
        });

        switch (forName) {
            case "name": {
                if (forValue.length < 4) {
                    newValidationMessages.name = 'Name must be at least 4 characters.';
                } else if (!nameRegexOnlyForAlphabets.test(formData.name)) {
                    newValidationMessages.name = 'Name only contains alphabets.'
                }
                else if (!nameRegex.test(formData.name)) {
                    newValidationMessages.name = "Name doesn't contains trailing space."
                }
                else newValidationMessages.name = '';
                break;
            }

            case "email": {
                if (!emailPattern.test(forValue)) {
                    newValidationMessages.email = 'Email must contains @nucleusteq domain name.';
                }else newValidationMessages.email = '';
                break;
            }

            case "dateOfJoining": {
                if (!forValue)
                    newValidationMessages.dateOfJoining = 'Date of joining is required';
                else newValidationMessages.dateOfJoining = '';
                break;
            }

            case "department": {
                if (forValue === 0)
                    newValidationMessages.department = 'Please select department';
                else newValidationMessages.department = '';
                break;
            }

            case "designation": {
                if (!forValue)
                    newValidationMessages.designation = 'Please select designation';
                else newValidationMessages.designation = '';
                break;
            }

            case "secretQuestion": {
                if (!forValue)
                    newValidationMessages.secretQuestion = 'Secret question is required';
                else newValidationMessages.secretQuestion = '';
                break;
            }

            case "secretAnswer": {
                if (!forValue)
                    newValidationMessages.secretAnswer = 'Secret answer is required';
                else newValidationMessages.secretAnswer = '';
                break;
            }

            case "password": {
                const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/i;
                if (!passwordPattern.test(userPassword)) {
                    newValidationMessages.password =
                        'Password must be minimum of eight characters and at least one uppercase letter, one lowercase letter, one number, and one special character';
                } else newValidationMessages.password = '';
                break;
            }

            case "confirmPassword": {
                if (!forValue)
                    newValidationMessages.confirmPassword = "Enter confirm password."
                else if (userPassword !== forValue)
                    newValidationMessages.confirmPassword = 'Password does not match';
                else newValidationMessages.confirmPassword = '';
                break;
            }

            default: {
                break;
            }
        }
        setValidationMessages(newValidationMessages);

        var isValid = true;
        var errorKeys = Object.keys(newValidationMessages)
        errorKeys.map((e) => {
            if (newValidationMessages[e] !== '') {
                isValid = false;
            }
            return null;
        })
        if (errorKeys.length < 9) {
            isValid = false;
        }
        return isValid;
    };

    const handleChange = (e) => {
        let { name, value } = e.target;
        setFormData({ ...formData, [name]: value });

        validateFields(name, value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (validateFields()) {
            const encryptedPassword = btoa(userPassword);
            formData.password = encryptedPassword;
            formData.name = formData.name.trimEnd();


            postRequest(REGISTER_PATH, formData, null, (response) => {
                if (response.status === 201) {
                    toast.success("Congratulations!! You're registered successfully. Please login to continue .", {
                        position: toast.POSITION.TOP_CENTER
                    });
                    navigate('/login')
                } else {
                    toast.error(`${response?.response?.data?.messages[0]}`, {
                        position: toast.POSITION.TOP_CENTER
                    });
                }
            })
        } else {
            toast.info("Please fill in all the fields!!", {
                position: toast.POSITION.TOP_CENTER
            });
        }
    };

    const designationOptions = [
        { label: 'Select Designation', value: '' },
        { label: 'Employee', value: 'Employee' },
        { label: 'Manager', value: 'Manager' },
    ];

    const secretQuestionOptions = [
        { label: 'Select Secret Question', value: '' },
        { label: 'Favorite Book', value: 'Favorite Book' },
        { label: 'Favorite Song', value: 'Favorite Song' },
        { label: 'What is the name of the town where you were born?', value: 'What is the name of the town where you were born?' },
        { label: 'Best place to visit', value: 'Best place to visit' },
    ];

    const departmentOptions = [
        { label: 'Select Department', value: '0' },
        ...departments.map((department) => ({
            label: department.departmentName,
            value: department.id,
        })),
    ];

    const handlePasswordChange = (e) => {
        let { name, value } = e.target;
        setUserPassword(e.target.value)

        validateFields(name, value);
    }

    return (
        <div className={styles.container}>
            <div className={styles.registrationContainer}>
                <form onSubmit={handleSubmit} className={styles.registrationForm}>
                    <h4 className={styles.registerationHeader}>Reimbursement Portal</h4>
                    <div className={styles.registrationField}>
                        <label>Name<span className={styles.mandatoryField}>*</span></label>
                        <CommonInput className={styles.inputValue} type='text' name='name' value={formData.name} handleChangeMethod={handleChange} placeholderText='Enter your name' />
                    </div>
                    {validationMessages.name && (
                        <span className={styles.errorField}>{validationMessages.name}</span>
                    )}

                    <div className={styles.registrationField}>
                        <label>Email<span className={styles.mandatoryField}>*</span></label>
                        <CommonInput className={styles.inputValue} type='text' name='email' value={formData.email} handleChangeMethod={handleChange} placeholderText='Enter your email' />
                    </div>
                    {validationMessages.email && (
                        <span className={styles.errorField}>{validationMessages.email}</span>
                    )}

                    <div className={styles.registrationField}>
                        <label>DOJ<span className={styles.mandatoryField}>*</span></label>
                        <CommonInput className={styles.inputValue} type='date' name='dateOfJoining' value={formData.dateOfJoining} handleChangeMethod={handleChange} placeholderText='Enter your date of joining' max={currentDate} />
                    </div>
                    {validationMessages.dateOfJoining && (
                        <span className={styles.errorField}>{validationMessages.dateOfJoining}</span>
                    )}

                    <div className={styles.registrationField}>
                        <label htmlFor='department'>Department<span className={styles.mandatoryField}>*</span></label>
                        <CommonSelect name='department' onChange={(e) => handleDepartmentChange(e)} value={formData.department.id} options={departmentOptions} />

                    </div>
                    {validationMessages.department && (
                        <span className={styles.errorField}>{validationMessages.department}</span>
                    )}

                    <div className={styles.registrationField}>
                        <label>Designation<span className={styles.mandatoryField}>*</span></label>
                        <CommonSelect name='designation' value={formData.designation} onChange={(e) => handleChange(e)} options={designationOptions} />
                    </div>
                    {validationMessages.designation && (
                        <span className={styles.errorField}>{validationMessages.designation}</span>
                    )}

                    <div className={styles.registrationField}>
                        <label>Secret Question<span className={styles.mandatoryField}>*</span></label>
                        <CommonSelect
                            name="secretQuestion"
                            value={formData.secretQuestion}
                            options={secretQuestionOptions}
                            onChange={(e) => handleChange(e)}
                        />
                    </div>
                    {validationMessages.secretQuestion && (
                        <span className={styles.errorField}>{validationMessages.secretQuestion}</span>
                    )}

                    <div className={styles.registrationField}>
                        <label>Secret Answer<span className={styles.mandatoryField}>*</span></label>
                        <CommonInput className={styles.inputValue} type='password' name='secretAnswer' value={formData.secretAnswer} handleChangeMethod={handleChange} placeholderText='Enter your answer' />
                    </div>
                    {validationMessages.secretAnswer && (
                        <span className={styles.errorField}>{validationMessages.secretAnswer}</span>
                    )}

                    <div className={styles.registrationField}>
                        <label>Password<span className={styles.mandatoryField}>*</span></label>
                        <CommonInput className={styles.inputValue} type='password' name='password' required value={userPassword} handleChangeMethod={handlePasswordChange} placeholderText='Enter your password' />
                    </div>
                    {validationMessages.password && (
                        <span className={styles.errorField}>{validationMessages.password}</span>
                    )}

                    <div className={styles.registrationField}>
                        <label>Confirm Password<span className={styles.mandatoryField}>*</span></label>
                        <CommonInput className={styles.inputValue} type='password' name='confirmPassword' value={formData.confirmPassword} handleChangeMethod={handleChange} placeholderText='Confirm your password' />
                    </div>
                    {validationMessages.confirmPassword && (
                        <span className={styles.errorField}>{validationMessages.confirmPassword}</span>
                    )}
                    <CommonButton type='submit' className={styles.registerationBtn}>Submit</CommonButton>
                <div className={styles.signUp}>
                    <p>Already have an account? <a href='/login'>Login Here</a></p>
                </div>
                </form>
            </div>
        </div>
    )
}

export default RegistrationPage