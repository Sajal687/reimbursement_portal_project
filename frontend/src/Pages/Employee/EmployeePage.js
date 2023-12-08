import React, { useState, useEffect } from 'react'
import CommonButton from '../../Common/CommonButton';
import styles from './employeePage.module.css';
import CommonTable from '../../Common/Table/CommonTable';
import CommonSelect from '../../Common/Select/CommonSelect';
import CommonModal from '../../Common/Modal/CommonModal';
import { toast } from 'react-toastify';
import { getRequest, putRequest } from '../../Apis/Services/ApiMethods';
import { ALL_EMPLOYEE_BY_TYPE, ALL_MANAGER_IN_DEPARTMENT, UPDATE_MANAGER_FOR_EMPLOYEE } from '../../Apis/ApiEndpoints';

const EmployeePage = () => {
    const [employees, setEmployees] = useState([]);
    const [allManagerOfDept, setAllManagerOfDept] = useState([]);
    const [openCard, setOpenCard] = useState(false);
    const [assignManager, setAssignManager] = useState("");
    const [employeeEmail, setEmployeeEmail] = useState("");
    const [active, setActive] = useState(true);
    const [employeeType, setEmployeeType] = useState("Employee");
    const [validationError, setValidationError] = useState("");
    const [updateTrigger, setUpdateTrigger] = useState(true);
    const [disabledCurrManagerName, setDisabledCurrManagerName] = useState();

    useEffect(() => {
        handleEmployeeClick("Employee")
    }, [updateTrigger])

    const sdeColumns = ['Name', 'Email', 'Department', 'Date of Joining', 'Manager Name', 'Action'];
    const managerColumns = ['Name', 'Email', 'Department', 'Date of Joining'];

    const handleGetEmployees = (employeeType) => {

        getRequest(`${ALL_EMPLOYEE_BY_TYPE}/${employeeType}`, null, (response) => {
            if (response?.status === 200) {
                setEmployees(response.data)
            } else {

            }
        })
    }

    const handleAssignBtnClick = (employee) => {
        setEmployeeEmail(employee.email);
        let departmentName = employee?.departmentOutDto?.departmentName;

        getRequest(`${ALL_MANAGER_IN_DEPARTMENT}/${departmentName}`, null, (response) => {
            if (response?.status === 200) {
                setAllManagerOfDept(response.data);
            } else {

            }
        })
        setDisabledCurrManagerName(employee.managerName);
        setOpenCard(true);
    }


    const handleDropdownClick = (e) => {
        setAssignManager(e.target.value)
        if (e.target.value === "") {
            setValidationError("Please select manager.")
        } else {
            setValidationError("");
        }
    }

    const handleAssignMangerBtnClick = () => {
        if (assignManager === "") {
            setValidationError("Please Select Manager");
            return;
        }

        const payload = {
            managerName: assignManager,
            employeeEmail: employeeEmail
        };

        putRequest(UPDATE_MANAGER_FOR_EMPLOYEE, payload, null, (response) => {
            if (response.status === 200) {
                toast.success("Manager assigned successfully.", {
                    position: toast.POSITION.TOP_RIGHT
                });
                setUpdateTrigger(!updateTrigger);
            } else {
                toast.error(`Some error occured!!`, {
                    position: toast.POSITION.TOP_RIGHT
                });
            }
        })
        closePopUp();
    }

    const closePopUp = () => {
        setValidationError("");
        setAssignManager("");
        setOpenCard(false);
    }

    const handleEmployeeClick = (emp) => {
        if (emp === "Employee")
            setEmployeeType("Employee");
        else
            setEmployeeType("Manager")
        handleGetEmployees(emp);
        setActive(emp);
    }

    const managerOptions = [
        { label: 'Select Managers', value: '' },
        ...allManagerOfDept.map((manager) => ({
            label: manager.name,
            value: manager.name,
        })),
    ];

    return (
        <div className={styles.employeeContainer}>
            <div className={styles.employeeBtn}>
                <CommonButton name="Employee" type="button" active={active} className={active === "Employee" ? styles.activeEmpBtn : styles.empBtn} onClickBtn={() => handleEmployeeClick("Employee")} >SDE</CommonButton>
                <CommonButton name="Manager" type="button" active={active} className={active === "Manager" ? styles.activeMngBtn : styles.mngBtn} onClickBtn={() => handleEmployeeClick("Manager")}>Manager</CommonButton>
            </div>

            <div className={styles.empTable}>
                {employeeType === "Employee" &&
                    <CommonTable columns={sdeColumns} data={employees.map((employee) => ({
                        "Name": employee.name,
                        "Email": employee.email,
                        "Department": employee.departmentOutDto.departmentName,
                        'Date of Joining': employee.dateOfJoining?.slice(0, 10),
                        "Manager Name": employee.managerName,
                        "Action": (
                            <CommonButton type='submit' onClickBtn={() => handleAssignBtnClick(employee)} className={styles.assignActionBtn}>{employee.managerId === 1 ? "Assign" : "Re-Assign"}</CommonButton>
                        ),
                    }))} />
                }
            </div>

            <div className={styles.empTable}>
                {
                    employeeType === "Manager" &&
                    <CommonTable columns={managerColumns} data={employees.map((employee) => ({
                        "Name": employee.name,
                        "Email": employee.email,
                        "Department": employee.departmentOutDto.departmentName,
                        'Date of Joining': employee.dateOfJoining?.slice(0, 10),
                    }))} />
                }
            </div>

            <CommonModal isOpen={openCard} onClose={closePopUp}>
                <div className={styles.mainContent}>
                    <label>Select from available managers:</label>
                    <CommonSelect
                        name="dropdown"
                        value={assignManager}
                        options={managerOptions}
                        onChange={handleDropdownClick}
                        disableOption={disabledCurrManagerName}
                        className={styles.assignManagerSelect}
                    />
                    {
                        validationError && <span className={styles.errorField}>{validationError}</span>
                    }
                    <div className={styles.actionBtns}>
                        <CommonButton type="button" className={styles.assignMngBtn} onClickBtn={handleAssignMangerBtnClick}>Assign</CommonButton>
                        <CommonButton type="button" className={styles.cancleAssignManager} onClickBtn={()=>setOpenCard(false)}>Cancel</CommonButton>
                    </div>
                </div>
            </CommonModal>
        </div>
    )
}

export default EmployeePage;