import React, { useEffect, useState } from 'react'
import styles from './departmentPage.module.css';
import CommonButton from '../../Common/CommonButton';
import CommonInput from '../../Common/CommonInput';
import CommonModal from '../../Common/Modal/CommonModal';
import CommonTable from '../../Common/Table/CommonTable';
import { BsPlusCircleFill } from 'react-icons/bs';
import { toast } from 'react-toastify';
import { getRequest, postRequest } from '../../Apis/Services/ApiMethods';
import { ADD_DEPARTMENT, ALL_DEPARTMENTS } from '../../Apis/ApiEndpoints';

const DepartmentPage = () => {
    const [showPopup, setShowPopUp] = useState(false);
    const [departments, setDepartments] = useState([]);
    const [newDepartment, setNewDepartment] = useState("");
    const [afterAddedDepartment, setAfterAddedDepartment] = useState(true);
    const [validationError, setValidationError] = useState('');


    useEffect(() => {
        getRequest(ALL_DEPARTMENTS, null, (response) => {
            if (response.status === 200) {
                setDepartments(response.data)
            } else {
                toast.error("Some error occured , while fetching all department list!!", {
                    position: toast.POSITION.TOP_RIGHT
                });
            }
        })
    }, [afterAddedDepartment]);


    const handleChange = (e) => {
        setValidationError("");
        setNewDepartment(e.target.value);

        let departmentRegex = /^[A-Za-z]+[A-Za-z ]*$/;
        if (e.target.value.length === 0) {
            setValidationError("Department name is required!")
        } else if (!departmentRegex.test(e.target.value)) {
            setValidationError("Department name only contains alphabets.");
        } else {
            setValidationError('');
        }

    }

    const handleClosePopUp = () => {
        if (newDepartment !== "" && validationError === '') {
            const upperDeptName = newDepartment.toUpperCase();
            const payload = {
                departmentName: upperDeptName
            }

            postRequest(ADD_DEPARTMENT , payload , null , (response)=>{
                if(response.status === 201){
                    toast.success("Department added successfully.", {
                        position: toast.POSITION.TOP_RIGHT
                    });
                    setAfterAddedDepartment(!afterAddedDepartment);
                    setDepartments([...departments, newDepartment]);
                }else{
                    toast.error(`${response?.response?.data?.messages}`, {
                        position: toast.POSITION.TOP_RIGHT
                    });
                }
            })
            setNewDepartment("");
            closePopUp();
        } else {
            setValidationError("Department name must contains atleast one character and having only alphabets.")
        }
    }

    const closePopUp = () => {
        setShowPopUp(!showPopup);
        setValidationError("")
    }

    const addDepartmentBtn = () => {
        setShowPopUp(!showPopup)
    }

    return (
        <>
            <CommonModal isOpen={showPopup} onClose={closePopUp}>
                <div className={styles.popup}>
                    <label htmlFor='newDepartment' className={styles.lableName}>Department<span className={styles.mandatoryField}>*</span> :</label>
                    <CommonInput type='text' name='newDepartment' value={newDepartment} className={styles.deptInput} handleChangeMethod={handleChange} placeholderText='Enter department name' />
                    {validationError && <span className={styles.errorField}>{validationError}</span>}
                </div>

                <div>
                    <CommonButton type="button" onClickBtn={handleClosePopUp} className={styles.addDeptBtnModal}>Add Department</CommonButton>
                </div>
            </CommonModal>
            <div>
                <div className={styles.headerContainer}>
                    <h1 className={styles.departmentTitle}>Departments:</h1>
                    <CommonButton type="button" onClickBtn={addDepartmentBtn} className={styles.addDeptBtn}><span className={styles.iconStyle}><BsPlusCircleFill /></span><span className={styles.btnContent}>Add Department</span></CommonButton>
                </div>
                <div className={styles.deptTable}>
                    <CommonTable columns={["Department Name"]} data={departments.map((department) => ({
                        "Department Name": department?.departmentName
                    }))} />
                </div>
            </div>
        </>
    )
}

export default DepartmentPage