import React , {useState , useEffect} from 'react'
import CommonButton from '../../Common/CommonButton';
import CommonCard from '../../Common/Card/CommonCard';
import styles from './assignEmployeeClaim.module.css';
import { getRequest } from '../../Apis/Services/ApiMethods';
import { UNDER_MANAGER_EMPLOYEE, UNDER_MANAGER_EMPLOYEE_BY_STATUS } from '../../Apis/ApiEndpoints';

const AssignEmployeeClaims = () => {
  const [claimData , setClaimData] = useState([]);
  const [isActive, setIsActive] = useState('');
  const [triggerd , setTriggered] = useState(false);

  const userEmail = JSON.parse(localStorage.getItem("userDetails"))?.email;
 
  useEffect(() => {
    handleAllBtnClick();
  },[]);

  useEffect(() => {
    
  },[triggerd]);
  
  const handleAllBtnClick = () => {
    setIsActive("all")
    getRequest(`${UNDER_MANAGER_EMPLOYEE}/${userEmail}`, null , (response)=>{
      if(response?.status === 200){
        setClaimData(response.data)
      }else{
        console.error(response)
      }
    })
  }


  const handlePendingBtnClick = () => {
    setIsActive("pending")

    getRequest(`${UNDER_MANAGER_EMPLOYEE_BY_STATUS}/${userEmail}/status/Pending` , null , (response)=>{
      if(response.status === 200){
        setClaimData(response.data);
      }else{
        console.error(response)
      }
    })
  }
  

  
  const handleApprovedBtnClick = () => {
    setIsActive("approved")

    getRequest(`${UNDER_MANAGER_EMPLOYEE_BY_STATUS}/${userEmail}/status/Approved` , null , (response)=>{
      if(response.status === 200){
        setClaimData(response.data);
      }else{
        console.error(response)
      }
    })
  }



  const handleRejectBtnClick = () => {
    setIsActive("rejected")

    getRequest(`${UNDER_MANAGER_EMPLOYEE_BY_STATUS}/${userEmail}/status/Rejected` , null , (response)=>{
      if(response.status === 200){
        setClaimData(response.data);
      }else{
        console.error(response)
      }
    })
  }

  return (
    <div className={styles.view_container}> 
    <div className={styles.slider}>
        <CommonButton name="all" type="button" active={isActive} className={isActive === "all" ? styles.activeAllBtn : styles.allBtn} onClickBtn={() => handleAllBtnClick()}>All</CommonButton>
        <CommonButton name="pending" type="button" active={isActive} className={isActive === "pending" ? styles.activePendingBtn : styles.pendingBtn} onClickBtn={() => handlePendingBtnClick()}>Pending</CommonButton>
        <CommonButton name="approved" type="button" active={isActive} className={isActive === "approved" ? styles.activeAcceptBtn : styles.acceptBtn} onClickBtn={() => handleApprovedBtnClick()}>Accepted</CommonButton>
        <CommonButton name="rejected" type="button" active={isActive} className={isActive === "rejected" ? styles.activeRejectBtn : styles.rejectBtn} onClickBtn={() => handleRejectBtnClick()}>Rejected</CommonButton>
    </div>

    <div className={styles.cardContainer}>
    {
      claimData?.length !== 0 ? 
      claimData.map((claim, index) => {
        return (
          <CommonCard
            userName={claim?.user?.name}
            expenseType={claim.expenseType}
            id={claim.id}
            amount={claim.amount}
            dateOfExpense={claim.dateOfExpense}
            comment={claim.claimDesc != null ? claim.claimDesc : claim.comment}
            documentFilePath={claim.documentFilePath} 
            status={claim.claimStatus}
            setClaimData={setClaimData}
            claimData = {claimData}
            setTriggered = {setTriggered}
            cardFor = "manager"
            />
        )
      }) : <p className={styles.emptyMsg}>No claim found!!</p>
    }
    </div>
  </div>
  ) 
}

export default AssignEmployeeClaims