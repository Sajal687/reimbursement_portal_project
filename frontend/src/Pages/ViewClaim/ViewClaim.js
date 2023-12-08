import React, { useState, useEffect } from 'react';
import styles from './viewClaim.module.css';
import CommonButton from '../../Common/CommonButton';
import CommonCard from '../../Common/Card/CommonCard';
import { getRequest } from '../../Apis/Services/ApiMethods';
import { ALL_MANAGER_CLAIMS, ALL_MANAGER_CLAIMS_BY_STATUS } from '../../Apis/ApiEndpoints';

function ViewClaim() {
  const [isActive, setIsActive] = useState('');
  const [claimData, setClaimData] = useState([]);
  const userDetails = JSON.parse(localStorage.getItem('userDetails'));
  const userEmail = userDetails?.email;

  useEffect(() => {
  }, []);

  useEffect(() => {
    handleAllBtnClick();
  }, []);


  const handleAllBtnClick = () => {
    setIsActive("all")

    getRequest(`${ALL_MANAGER_CLAIMS}/${userEmail}` , null , (response)=>{
      if(response?.status === 200){
        setClaimData(response.data);
      }else{
        console.error(response);
      }
    })
  }


  const handlePendingBtnClick = () => {
    setIsActive("pending")
    getRequest(`${ALL_MANAGER_CLAIMS_BY_STATUS}/${userEmail}/status/Pending` , null , (response)=>{
      if(response.status === 200){
        setClaimData(response.data);
      }else{
        console.error(response);
      }
    })
  }

  const handleApprovedBtnClick = () => {
    setIsActive("approved")

    getRequest(`${ALL_MANAGER_CLAIMS_BY_STATUS}/${userEmail}/status/Approved` , null , (response)=>{
      if(response.status === 200){
        setClaimData(response.data);
      }else{
        console.error(response);
      }
    })
  }

  const handleRejectBtnClick = () => {
    setIsActive("rejected")

    getRequest(`${ALL_MANAGER_CLAIMS_BY_STATUS}/${userEmail}/status/Rejected` , null , (response)=>{
      if(response.status === 200){
        setClaimData(response.data);
      }else{
        console.error(response);
      }
    })
  }

  return (
    <div className={styles.viewContainer}>
      <div className={styles.slider}>
        <CommonButton name="all" type="button" active={isActive} className={isActive === "all" ? styles.activeAllBtn : styles.allBtn} onClickBtn={() => handleAllBtnClick()}>All</CommonButton>
        <CommonButton name="pending" type="button" active={isActive} className={isActive === "pending" ? styles.activePendingBtn : styles.pendingBtn} onClickBtn={() => handlePendingBtnClick()}>Pending</CommonButton>
        <CommonButton name="approved" type="button" active={isActive} className={isActive === "approved" ? styles.activeApprovedBtn : styles.approvedBtn} onClickBtn={() => handleApprovedBtnClick()}>Accepted</CommonButton>
        <CommonButton name="rejected" type="button" active={isActive} className={isActive === "rejected" ? styles.activeRejectedBtn : styles.rejectedBtn} onClickBtn={() => handleRejectBtnClick()}>Rejected</CommonButton>
      </div>

      <div className={styles.cardContainer}>
        {
          claimData.length !== 0 ?
            claimData.map((claim, index) => {
              return (
                <React.Fragment key={index}>
                  <CommonCard
                    userName={claim?.user?.name}
                    expenseType={claim?.expenseType}
                    id={claim?.id}
                    amount={claim?.amount}
                    dateOfExpense={claim?.dateOfExpense}
                    comment={claim?.claimDesc != null ? claim?.claimDesc : claim?.comment}
                    documentFilePath={claim?.documentFilePath}
                    status={claim?.claimStatus}
                  />
                </React.Fragment>
              )
            })
            : <p className={styles.emptyMsg}>No claim found!!</p>}
      </div>
    </div>
  );
}

export default ViewClaim;