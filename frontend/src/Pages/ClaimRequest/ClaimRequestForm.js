import React, { useState, useEffect } from 'react';
import styles from './claimRequestForm.module.css';
import CommonButton from '../../Common/CommonButton'
import CommonInput from '../../Common/CommonInput';
import CommonSelect from '../../Common/Select/CommonSelect';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import { getRequest, postRequestForClaim } from '../../Apis/Services/ApiMethods';
import { ADD_CLAIM_REQUEST, GET_CATEGORIES } from '../../Apis/ApiEndpoints';

function ClaimRequestForm() {
  const [validationError, setValidationError] = useState();
  const [selectedFile, setSelectedFile] = useState(null);
  const [claimType, setClaimType] = useState('');
  const [claimDate, setClaimDate] = useState('');
  const [claimAmount, setClaimAmount] = useState('');
  const [claimComment, setClaimComment] = useState('');
  const [categories, setCategories] = useState([]);
  const [currentDate, setCDate] = useState('');
  const [prevDate, setPrevDate] = useState('');
  const [previewImage, setPreviewImage] = useState(true);
  const [claimLimit, setClaimLimit] = useState("0");
  const [commentLen , setCommentLen] = useState(0);


  const navigate = useNavigate();

  useEffect(() => {
    let date = new Date();
    let day = String(date.getDate()).padStart(2, '0');
    let mon = String(date.getMonth() + 1).padStart(2, '0');
    let year = date.getFullYear();
    let currentDate = `${year}-${mon}-${day}`;
    setCDate(currentDate);

    getRequest(GET_CATEGORIES, null, (response) => {
      if (response?.status === 200) {
        setCategories(response.data);
      } else {
        console.error(response);
      }
    })
  }, [])

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      if (
        file.type === 'image/jpeg' ||
        file.type === 'image/png' ||
        file.type === 'application/pdf'
      ) {
        setSelectedFile(file);
        setValidationError({ ...validationError, emptySelectedFile: "" })
        setPreviewImage(false);
      } else {
        toast.info('Please select a valid image (JPEG, PNG) or PDF file.');
      }
    } else if (file === null) {
      setValidationError({ ...validationError, emptySelectedFile: "Claim document is required!!" })
    }
  };


  const handleSubmit = async (e) => {
    const userData = JSON.parse(localStorage.getItem('userDetails'));
    e.preventDefault();

    const formData = new FormData();
    formData.append('expenseType', claimType);
    formData.append('dateOfExpense', new Date(claimDate));
    formData.append('amount', claimAmount);
    formData.append('comment', claimComment);
    formData.append('documentFile', selectedFile);
    formData.append('userEmail', userData?.email);

    let isValid = true;
    let allKeys = validationError && Object?.keys(validationError);
    allKeys?.map((key) => {
      if (validationError[key] !== '')
        isValid = false;
      return null;
    })

    if (validationError != null && isValid && allKeys.length === 5) {
      const userDetails = JSON.parse(localStorage.getItem("userDetails"));

      postRequestForClaim(ADD_CLAIM_REQUEST, formData, null, (response) => {
        if (response.status === 201) {
          toast.success(response.data.message, {
            position: toast.POSITION.TOP_RIGHT
          });
          userDetails?.designation === "Employee" ? navigate('/employee/claim') : navigate('/manager/myclaim');

          setClaimType('');
          setClaimDate('');
          setClaimAmount('');
          setClaimComment('');
          setSelectedFile(null);
        } else {
          if (response) {
            toast.error(`${response?.response?.data?.messages}`, {
              position: toast.POSITION.TOP_RIGHT
            })
          } else {
            toast.error(`An error occurred while making the request:, ${response.response.data.messages}`, {
              position: toast.POSITION.TOP_RIGHT
            })
          }
        }
      })
    } else {
      toast.error(`Please fill in all the fields.`, {
        position: toast.POSITION.TOP_RIGHT
      });
    }

  };


  const categoryOptions = [
    { label: 'Select Category', value: '' },
    ...categories.map((category) => ({
      label: category.categoryName,
      value: category.categoryName,
    })),
  ];

  const handleClaimTypeChange = (e) => {
    setClaimType(e.target.value);
    categories.map((category, index) => {
      if (e.target.value === category.categoryName) {
        setClaimLimit(category.categoryPriceLimit)
      }
      return null;
    })

    if (e.target.value === "") {
      setValidationError({ ...validationError, emptyClaimType: "Claim Type is required!!" });
      setClaimLimit("0")
    } else {
      setValidationError({ ...validationError, emptyClaimType: "" });
    }
  }

  const handleChangeClaimDate = (e) => {
    setClaimDate(e.target.value);

    if (e.target.value === "") {
      setValidationError({ ...validationError, emptyClaimDate: "Claim Date is required!!" });
    } else {
      setValidationError({ ...validationError, emptyClaimDate: "" });
    }
  }

  const handleClaimAmount = (e) => {
    setClaimAmount(e.target.value)
    const numberRegex = /^(?:[1-9]\d*|\d)$/;

    const categoryLimit = new Map([]);
    categories.map((category) => {
      return (
        categoryLimit.set(category.categoryName, Number(category.categoryPriceLimit))
      )
    })
    if (e.target.value === "") {
      setValidationError({ ...validationError, priceLimit: "Claim amount is Required!!" });
    } else if (Number(e.target.value) > Number(categoryLimit.get(claimType))) {
      setValidationError({ ...validationError, priceLimit: `Claim amount must be less than ${categoryLimit.get(claimType)} for ${claimType}` });
    } else if (!numberRegex.test(e.target.value)) {
      setValidationError({ ...validationError, priceLimit: "Claim amount is in a numeral format." })
    } else if (Number(e.target.value) <= 0) {
      setValidationError({ ...validationError, priceLimit: "Claim amount must be more than 0." })
    }
    else {
      setValidationError({ ...validationError, priceLimit: "" })
    }
  }

  const handleCommentChange = (e) => {
    setClaimComment(e.target.value);

    setCommentLen(e.target.value.length)

    if (e.target.value === "")
      setValidationError({ ...validationError, emptyClaimComment: "Claim comment is required!!" })
    else if (e.target.value.length > 251)
      setValidationError({ ...validationError, emptyClaimComment: "Comment size must be less than 250 characters." });
    else
      setValidationError({ ...validationError, emptyClaimComment: "" })
  }

  return (
    <div className={styles.container}>
      <h2 className={styles.heading}>Raise Claim Request</h2>
      <div className={styles.claimRequestContainer}>
        <div className={styles.claimForm}>
          <form onSubmit={handleSubmit}>
            <div className={styles.formField}>
              <label htmlFor="claimType">Claim Type<span className={styles.mandatoryField}>*</span></label>
              <CommonSelect
                className={styles.claimInput}
                name="claimType"
                value={claimType}
                options={categoryOptions}
                onChange={(e) => handleClaimTypeChange(e)}
              />
            </div>
            {validationError?.emptyClaimType && (
              <span className={styles.errorFieldClaim}>{validationError.emptyClaimType}</span>
            )}

            <div className={styles.formField}>
              <label htmlFor="claimDate">Date<span className={styles.mandatoryField}>*</span></label>
              <CommonInput type='date' name='claimDate' className={styles.claimInput} value={claimDate} max={currentDate} min={JSON.parse(localStorage.userDetails).dateOfJoining.slice(0,10)} customStyle={{ width: "100%" }} handleChangeMethod={(e) => handleChangeClaimDate(e)} placeholderText='Enter the claim date' />

            </div>
            {validationError?.emptyClaimDate && (
              <span className={styles.errorFieldClaim}>{validationError.emptyClaimDate}</span>
            )}

            <div className={styles.formField}>
              <label htmlFor="claimAmount">Amount<span className={styles.mandatoryField}>*</span></label>
              <div className={styles.claimLimitBox}>
                <CommonInput type='text' className={styles.claimAmountBox} customStyle={{ width: "100%" }} name='claimAmount' value={claimAmount} handleChangeMethod={(e) => handleClaimAmount(e)} placeholderText='Enter the claim amount' />
                <div className={styles.limit}>&lt;={claimLimit}</div>
              </div>
            </div>
            {validationError?.priceLimit && (
              <span className={styles.errorFieldClaim}>{validationError.priceLimit}</span>
            )}

            <div className={styles.formField}>
              <div className={styles.commentLen}>
                <label htmlFor="claimComment">Comment<span className={styles.mandatoryField}>*</span></label>
                <p>{commentLen}/{250 - commentLen}</p>
              </div>
              <textarea
                id="claimComment"
                className={styles.claimInput}
                value={claimComment}
                onChange={(e) => handleCommentChange(e)}
                maxLength={250}
              ></textarea>
            </div>
            {validationError?.emptyClaimComment && (
              <span className={styles.errorFieldClaim}>{validationError.emptyClaimComment}</span>
            )}


            <CommonButton type="submit" className={styles.submitButton} >Submit</CommonButton>
          </form>
        </div>
        <div className={styles.fileUpload}>
          <div></div>
          <label className={styles.documentUploadLabel}>Document Upload<span className={styles.mandatoryField}>*</span></label>
          <div className={styles.previewImage}>
            {previewImage ?
              <img src='/assets/background_image/previewImage.png' alt='previewImage' width="500" />
              :
              <img
                src={URL.createObjectURL(selectedFile)}
                alt="Preview"
                width="300"
              />
            }
          </div>
          <CommonInput type='file' name='fileInput' accept=".jpg, .jpeg, .png" className={styles.inputFile} handleChangeMethod={handleFileChange} />

          {validationError?.emptySelectedFile && (
            <span className={styles.errorFieldClaim}>{validationError.emptySelectedFile}</span>
          )}

        </div>
      </div>
    </div>
  );
}

export default ClaimRequestForm;
