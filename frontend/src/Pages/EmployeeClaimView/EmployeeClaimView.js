import React, { useState, useEffect } from 'react';
import styles from './employeeClaimView.module.css';
import ClaimRequestForm from '../ClaimRequest/ClaimRequestForm';
import CommonButton from '../../Common/CommonButton';
import CommonCard from '../../Common/Card/CommonCard';
import { getRequest } from '../../Apis/Services/ApiMethods';
import { EMPLOYEE_CLAIMS } from '../../Apis/ApiEndpoints';

function ViewClaim() {
  const [claims, setClaims] = useState();
  const [selectedClaim, setSelectedClaim] = useState(null);
  const [isActive, setIsActive] = useState('');
  const [statusData, setStatusData] = useState([]);


  const handleStatusBtnClick = (status) => {
    setIsActive(status)
    setStatusData([]);
    switch (status) {
      case "pending":
        setStatusData(claims?.filter((claim) => claim.claimStatus === "Pending"))
        break;
      case "approved":
        setStatusData(claims?.filter((claim) => claim.claimStatus === "Approved"))
        break;
      case "rejected":
        setStatusData(claims?.filter((claim) => claim.claimStatus === "Rejected"))
        break;
      default:
        break;
    }
  }

  const handleAllBtnClick = () => {
    setIsActive("all")
    setStatusData(claims)
  }


  useEffect(() => {
    const userDetails = JSON.parse(localStorage.getItem('userDetails'));
    const userEmail = userDetails?.email;

    getRequest(`${EMPLOYEE_CLAIMS}/${userEmail}` , null , (response)=>{
      if(response.status === 200){
        setClaims(response.data);
      }else{
        console.error(response);
      }
    })
  }, []);

  useEffect(() => {
    handleAllBtnClick("all");
  }, [claims]);

  const closeEditModal = () => {
    setSelectedClaim(null);
  };

  return (
    <div className={styles.viewContainer}>
      <div className={styles.slider}>
      <CommonButton name="all" type="button" active={isActive} className={isActive === "all" ? styles.activeAllBtn : styles.allBtn}  onClickBtn={() => handleAllBtnClick("all")}>All</CommonButton>
        <CommonButton name="pending" type="button" active={isActive} className={isActive === "pending" ? styles.activePendingBtn : styles.pendingBtn} onClickBtn={() => handleStatusBtnClick("pending")}>Pending</CommonButton>
        <CommonButton name="approved" type="button" active={isActive} className={isActive === "approved" ? styles.activeApprovedBtn : styles.approvedBtn} onClickBtn={() => handleStatusBtnClick("approved")}>Approved</CommonButton>
        <CommonButton name="rejected" type="button" active={isActive} className={isActive === "rejected" ? styles.activeRejectedBtn : styles.rejectedBtn} onClickBtn={() => handleStatusBtnClick("rejected")}>Rejected</CommonButton>
    </div>

      <div className={styles.cardContainer}>
        {
          statusData?.length !== 0 ?
            statusData?.map((claim, index) => {
              return (
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
              )
            }) : <p className={styles.emptyMsg}>No claims found!!</p>
        }
      </div>

      {selectedClaim && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <h2>Edit Claim</h2>
            <ClaimRequestForm />
            <CommonButton type="button" className={styles.closeButton} onClickBtn={closeEditModal}>
              Close
            </CommonButton>
          </div>
        </div>
      )}
    </div>
  );
}

export default ViewClaim;
