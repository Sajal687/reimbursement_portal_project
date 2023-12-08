import React, { useState, useEffect } from 'react';
import styles from './adminClaimView.module.css';
import CommonButton from '../../Common/CommonButton';
import CommonCard from '../../Common/Card/CommonCard';
import CommonSelect from '../../Common/Select/CommonSelect';
import { getRequest } from '../../Apis/Services/ApiMethods';
import { toast } from 'react-toastify';
import { ALL_ADMIN_CLAIMS, ALL_ADMIN_STATUS_CLAIMS, GET_CATEGORIES } from '../../Apis/ApiEndpoints';
import { FaRegArrowAltCircleLeft, FaRegArrowAltCircleRight } from 'react-icons/fa';


const AdminClaimView = () => {
  const [isActive, setIsActive] = useState('');
  const [claimData, setClaimData] = useState([]);
  const [triggerd, setTriggered] = useState(false);
  const [filterClaimData, setFilterClaimData] = useState([]);
  const [currentFilter, setCurrentFilter] = useState('currentDesignation');
  const [currentDesignation, setCurrentDesignation] = useState("All");
  const [categories, setCategories] = useState([]);
  const [currentCategory, setCurrentCategory] = useState("Select Category");
  const [showData , setShowData] = useState([]);
  const [currentIdx , setCurrentIdx] = useState(0);
  const showCardPerPage = 3;
  const [disableRightClick , setDisableRight] = useState();
  const [disableLeftClick , setDisableLeft] = useState(false);


  useEffect(() => {
    getRequest(GET_CATEGORIES, null, function (response) {
      if (response?.status === 200) {
        setCategories(response.data);
      } else {
        toast.error("Some error occured!!", {
          position: toast.POSITION.TOP_RIGHT
        });
      }
    })
    handleAllBtnClick();
  }, []);

  useEffect(() => {
    handleAllBtnClick();
  }, [triggerd]);

  useEffect(() => {
    setDisableLeft(true);
    handleNextBtnClick();
  }, [currentFilter , currentCategory , currentDesignation , claimData , isActive])

  const designationOptions = [
    { label: 'All', value: 'All' },
    { label: 'Employees', value: 'Employee' },
    { label: 'Managers', value: 'Manager' },
  ];

  const filterOptions = [
    { label: 'Designation', value: 'currentDesignation' },
    { label: 'Category', value: 'currentCategory' }
  ];

  function toPascalCase(str) {
    return str.replace(/(\w)(\w*)/g, function (_, firstChar, restOfWord) {
      return firstChar.toUpperCase() + restOfWord.toLowerCase();
    });
  }

  const categoryOptions = [
    { label: "All", value: "All" },
    ...(categories?.map((category) => ({
      label: toPascalCase(category.categoryName),
      value: category.categoryName,
    })) || []),
  ];

  const handleAllBtnClick = () => {
    setCurrentFilter('currentDesignation');
    setCurrentDesignation('All');
    setIsActive("all");
    getRequest(`${ALL_ADMIN_CLAIMS}`, null, (response) => {
      if (response.status === 200) {
        setClaimData(response.data)
        setFilterClaimData(response.data);
        setShowData(response.data);
      } else {
        console.error(response)
      }
    })
  }

  const handlePendingBtnClick = () => {
    setIsActive("pending")
    setCurrentFilter('currentDesignation');
    setCurrentDesignation('All');
    getRequest(`${ALL_ADMIN_STATUS_CLAIMS}/Pending`, null, (response) => {
      if (response.status === 200) {
        setClaimData(response.data);
        setFilterClaimData(response.data);
        setShowData(response.data);
      } else {
        console.error(response)
      }
    })
  }

  const handleApprovedBtnClick = () => {
    setIsActive("approved")
    setCurrentFilter('currentDesignation');
    setCurrentDesignation('All');
    getRequest(`${ALL_ADMIN_STATUS_CLAIMS}/Approved`, null, (response) => {
      if (response.status === 200) {
        setClaimData(response.data);
        setFilterClaimData(response.data);
        setShowData(response.data);
      } else {
        console.error(response);
      }
    })
  }

  const handleRejectBtnClick = () => {
    setIsActive("rejected")
    setCurrentFilter('currentDesignation');
    setCurrentDesignation('All');
    getRequest(`${ALL_ADMIN_STATUS_CLAIMS}/Rejected`, null, (response) => {
      if (response.status === 200) {
        setClaimData(response.data);
        setFilterClaimData(response.data);
        setShowData(response.data);
      } else {
        console.error(response)
      }
    })
  }

  const handleFilterChange = (e) => {
    setCurrentFilter(e.target.value);
    setCurrentIdx(0)
    if (e.target.value === 'currentDesignation') {
      setCurrentDesignation('All')
      setFilterClaimData(claimData.filter((claim) => claim));
      setShowData(claimData.filter((claim) => claim));
    } else {
      setCurrentCategory('All')
      setFilterClaimData(claimData.filter((claim) => claim));
      setShowData(claimData.filter((claim) => claim));
    }
  }


  const handleDesignationChange = (e) => {
    setCurrentDesignation(e.target.value);
    setCurrentIdx(0)
    if (e.target.value === "All") {
      setFilterClaimData(claimData.filter((claim) => claim));
      setShowData(claimData.filter((claim)=> claim));
    } else {
      setFilterClaimData(claimData.filter((claim) => claim.user.designation === e.target.value));
      setShowData(claimData.filter((claim)=> claim.user.designation === e.target.value));
    }
  }

  const handleCategoryChange = (e) => {
    setCurrentCategory(e.target.value);
    setCurrentIdx(0)
    if (e.target.value === "All") {
      setFilterClaimData(claimData.filter((claim) => claim));
      setShowData(claimData.filter((claim) => claim));
    } else {
      setFilterClaimData(claimData.filter((claim) => claim.expenseType === e.target.value));
      setShowData(claimData.filter((claim) => claim.expenseType === e.target.value));
    }
  }

  const handlePrevBtnClick = () => {
    if(currentIdx > showCardPerPage){ 
      setDisableRight(false);
      setShowData(filterClaimData.slice(currentIdx - 2*showCardPerPage , currentIdx - showCardPerPage))
      setCurrentIdx(currentIdx > showCardPerPage ? (currentIdx - showCardPerPage) : showCardPerPage);
      setDisableLeft(currentIdx-showCardPerPage <= showCardPerPage ? true : false); 
    }
  }

  const handleNextBtnClick = () => {
    //Check wheather filterClaimData contains any Card or not?
    if(filterClaimData.length - currentIdx > 0){  //3-2 = 1 > 0 ====> 
      let cardPerPage = showCardPerPage;
      setDisableLeft(currentIdx >= cardPerPage ? false : true);   //4 >= 4
      
      //Check if Cards are less than showCardPerPage count.
      if(filterClaimData.length - currentIdx < showCardPerPage){
        cardPerPage = filterClaimData.length - currentIdx;
      }

      setShowData(filterClaimData.slice(currentIdx, currentIdx+cardPerPage));  //(0,0+2)  .  (2 , 3)
      setCurrentIdx(currentIdx+showCardPerPage)
      setDisableRight(currentIdx+showCardPerPage >= filterClaimData.length ? true : false);
    }
  }

  return (
    <div className={styles.view_container}>
      <div className={styles.slider}>
        <CommonButton name="all" type="button" active={isActive} className={isActive === "all" ? styles.activeAllBtn : styles.allBtn} onClickBtn={() => handleAllBtnClick()}>All</CommonButton>
        <CommonButton name="pending" type="button" active={isActive} className={isActive === "pending" ? styles.activePendingBtn : styles.pendingBtn} onClickBtn={() => handlePendingBtnClick()}>Pending</CommonButton>
        <CommonButton name="approved" type="button" active={isActive} className={isActive === "approved" ? styles.activeAcceptBtn : styles.acceptBtn} onClickBtn={() => handleApprovedBtnClick()}>Accepted</CommonButton>
        <CommonButton name="rejected" type="button" active={isActive} className={isActive === "rejected" ? styles.activeRejectBtn : styles.rejectBtn} onClickBtn={() => handleRejectBtnClick()}>Rejected</CommonButton>
      </div>


      <div className={styles.filter}>
        <label className={styles.filterLabel}>Filter by:</label>
        <CommonSelect name='chooseFilter' value={currentFilter} onChange={(e) => handleFilterChange(e)} options={filterOptions} className={styles.filterSelect} />


        {
          currentFilter === 'currentDesignation' ?
            <CommonSelect name='designationFilter' value={currentDesignation} onChange={(e) => handleDesignationChange(e)} options={designationOptions} className={styles.filterSelect} />
            :
            <CommonSelect name='categoryFilter' value={currentCategory} onChange={(e) => handleCategoryChange(e)} options={categoryOptions} className={styles.filterSelect} />
        }
      </div>

      <div className={styles.cardContainer}>
        {
          showData?.length !== 0 ?
            showData?.map((claim, index) => {
              let appendDesignation = currentDesignation === 'All' && (claim?.user?.designation?.substring(0, 3) === 'Man' ? 'Mng' : 'Emp');
              appendDesignation = appendDesignation ? `(${appendDesignation})` : '';
              return (
                <CommonCard
                  key={index}
                  userName={`${claim?.user?.name} ${appendDesignation}`}
                  expenseType={claim.expenseType}
                  id={claim.id}
                  amount={claim.amount}
                  dateOfExpense={claim.dateOfExpense}
                  comment={claim.claimDesc != null ? claim.claimDesc : claim.comment}
                  documentFilePath={claim.documentFilePath}
                  status={claim.claimStatus}
                  setClaimData={setClaimData}
                  claimData={claimData}
                  setTriggered={setTriggered}
                  cardFor='Admin'
                />
              )
            }) : <p className={styles.emptyMsg}>No claims found!!</p>
        }
      </div>
      <div className={styles.directionBtns}>
        <FaRegArrowAltCircleLeft size={30} onClick={handlePrevBtnClick} className={disableLeftClick ? styles.arrowDisable : styles.arrow}/>
        <FaRegArrowAltCircleRight size={30} onClick={handleNextBtnClick} className={disableRightClick ? styles.arrowDisable : styles.arrow}/>
      </div>
    </div>
  );
}

export default AdminClaimView;
