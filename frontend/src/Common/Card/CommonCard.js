import React, { useState } from 'react';
import styles from './commonCard.module.css';
import CommonButton from '../CommonButton';
import CommonModal from '../Modal/CommonModal';
import { toast } from 'react-toastify';
import { getRequest, putRequest } from '../../Apis/Services/ApiMethods';
import { SHOW_IMAGE, UPDATE_CLAIM_STATUS } from '../../Apis/ApiEndpoints';
import { FaRupeeSign } from "react-icons/fa";
import { BiCategory } from "react-icons/bi";

const CommonCard = ({ userName, expenseType, id, amount, dateOfExpense, comment, documentFilePath, status, setClaimData, claimData, setTriggered, cardFor }) => {
    const [commentPopUp, setCommentPopUp] = useState(false);
    const [rejectComment, setRejectComment] = useState('');
    const [validationError, setValidationError] = useState("");
    const [commentValue, setCommentValue] = useState('');
    const [commentPopup, setCommentPopup] = useState(false);
    const [showApproved, setShowApproved] = useState(false);
    const [commentLen, setCommentLen] = useState(0);


    const viewMore = (comment) => {
        setCommentValue(comment)
        setCommentPopup(true)
    }

    const closeModal = () => {
        setCommentPopup(false)
    }


    const InStatus = ["all", "Pending"];
    const InUser = ["Admin", "manager"];

    const handleShowImage = () => {
        getRequest(`${SHOW_IMAGE}/${documentFilePath}`, null, (response) => {
            if (response.status === 200) {
                let image = new Image();
                image.src = 'data:image/jpg;base64,' + response.data.image;
                let win = window.open(image);
                win.document.write(image.outerHTML);

                // if (win && !win.closed) {
                //     win.close();
                // }
            } else {
                console.error("Error occured while opening an Image.");
            }
        })
    }

    const handleAcceptClaim = () => {
        const payload = {
            status: "Approved",
            comment: ""
        }

        putRequest(`${UPDATE_CLAIM_STATUS}/${id}`, payload, null, (response) => {
            if (response?.status === 200) {
                toast.success("Claim request is accepted.", {
                    position: toast.POSITION.TOP_RIGHT
                });
            } else {
                toast.error("Some error occured!!", {
                    position: toast.POSITION.TOP_RIGHT
                });
            }
        })

        setClaimData(claimData.filter((data) => data?.id !== id))
        setTriggered(true)
        closeApprovedPopUp();
    }

    let extraDots = comment?.length > 6 ? "..." : ''

    const handleRejectClaim = () => {
        const payload = {
            status: "Rejected",
            comment: rejectComment
        }

        if (rejectComment !== "") {
            putRequest(`${UPDATE_CLAIM_STATUS}/${id}`, payload, null, (response) => {
                if (response?.status === 200) {
                    toast.success("Claim rejected successfully.", {
                        position: toast.POSITION.TOP_RIGHT
                    });
                } else {
                    toast.error("Some error occured!!", {
                        position: toast.POSITION.TOP_RIGHT
                    });
                }
            })
            setClaimData(claimData.filter((data) => data?.id !== id))
            setTriggered(true)
            closePopUp();
        }
    }

    const handleShowPopUp = () => {
        setCommentPopUp(true);
    }

    const closePopUp = () => {
        setValidationError("");
        setRejectComment("");
        setCommentPopUp(false);
    }

    const closeApprovedPopUp = () => {
        setShowApproved(false);
    }

    const handleShowApproved = () => {
        setShowApproved(true);
    }

    const handleCommentChange = (e) => {
        setRejectComment(e.target.value)
        setCommentLen(e.target.value.length);
        if (e.target.value.length < 1) {
            setValidationError("Comment is required");
        } else {
            setValidationError("");
        }

    }

    return (
        <div className={styles.card}>
            {
                commentPopup && <CommonModal isOpen={true} onClose={closeModal}><div className={styles.commentValue}>{commentValue}</div></CommonModal>
            }
            <div className={styles.cardHeader}>
                <h3 className={styles.cardTitle}><BiCategory size={20}/>{expenseType}:</h3>
            </div>
            <div className={styles.cardBody}>
                <div className={styles.row}>
                    <div className={styles.halfCol}>
                        <p className={styles.cardText}>
                            RequestId:
                            <span className={styles.cardValue}>{id}</span>
                        </p>
                    </div>
                    <div className={styles.halfCol}>
                        <p className={styles.cardText}>
                            Name:
                            <span className={styles.cardValue}>{userName}</span>
                        </p>
                    </div>
                </div>
                <div className={styles.row}>
                    <div className={styles.halfCol}>
                        <p className={styles.cardText}>
                            Bill Amount:
                            <span className={styles.cardValue}><FaRupeeSign size={15}/>{amount}</span>
                        </p>
                    </div>
                    <div className={styles.halfCol}>
                        <p className={styles.cardText}>
                            Invoice Date:
                            <span className={styles.cardValue}>{dateOfExpense?.slice(0, 10)}</span>
                        </p>
                    </div>
                </div>
                <div className={styles.row}>
                    <div className={styles.halfCol}>
                        <p className={styles.cardText} style={{ textAlign: 'left' }}>
                            Comment:
                            <span className={styles.cardValue}>{comment?.slice(0, 6) + extraDots}{comment?.length > 6 ? <span className={styles.viewMoreButton} onClick={() => viewMore(comment)}> View more</span> : ''}
                            </span>
                        </p>
                    </div>
                    <div className={styles.halfCol}>
                        <p className={styles.cardText}>
                            Status:
                            <span className={styles.cardValue}>{status}</span>
                        </p>
                    </div>
                </div>

                <div className={styles.btngrp}>
                    <div className={styles.btnRow}>
                        <div className={styles.btnCol}>
                            <CommonButton type="button" onClickBtn={() => handleShowImage()} className={styles.invoiceBtn}>View Invoice</CommonButton>
                        </div>
                    </div>
                    {
                        JSON.parse(localStorage.getItem("userDetails"))?.designation !== "Employee"
                        &&
                        InStatus.includes(status)
                        &&
                        InUser.includes(cardFor)
                        &&
                        <div className={styles.btnRow}>
                            <div className={styles.btnCol}>
                                <CommonButton type="button" onClickBtn={() => handleShowApproved()} className={styles.acceptBtn}>Accept</CommonButton>
                                <CommonButton type="button" onClickBtn={() => handleShowPopUp()} className={styles.rejectBtn}>Reject</CommonButton>
                            </div>
                        </div>
                    }
                </div>
            </div>

            <CommonModal isOpen={commentPopUp} onClose={closePopUp}>
                <div className={styles.popUpBody}>
                    <div className={styles.commentLen}> 
                        <label htmlFor='rejectcomment' className={styles.rejectCommentLabel}>Comment<span className={styles.mandatoryField}>*</span></label>
                        <p>{commentLen}/{250 - commentLen}</p>
                    </div>
                    <textarea name="rejectcomment" maxLength={250} rows={5} cols={30} value={rejectComment} onChange={(e) => handleCommentChange(e)} placeholder='Enter your comment.' className={styles.commentTextarea} />
                    {
                        validationError && <span className={styles.errorField}>{validationError}</span>
                    }
                    <div className={styles.btns}>
                        <CommonButton type="button" className={styles.modalRejectBtn} onClickBtn={handleRejectClaim}>Save</CommonButton>
                    </div>
                </div>
            </CommonModal>

            <CommonModal isOpen={showApproved} onClose={closeApprovedPopUp}>
                <div className={styles.popUpBody}>
                    <p className={styles.modelText}>Are you sure, you want to approved this claim?</p>
                    <div className={styles.actionBtns}>
                        <CommonButton type="button" onClickBtn={handleAcceptClaim} className={styles.approvedBtn}>Yes</CommonButton>
                        <CommonButton type="button" onClickBtn={() => setShowApproved(false)} className={styles.approvedCancleBtn}>No</CommonButton>
                    </div>
                </div>
            </CommonModal>
        </div>
    )
}

export default CommonCard