import React, { useEffect, useState } from 'react';
import styles from './categoryPage.module.css';
import CommonButton from '../../Common/CommonButton';
import CommonTable from '../../Common/Table/CommonTable';
import CommonInput from '../../Common/CommonInput';
import CommonModal from '../../Common/Modal/CommonModal';
import { BsPlusCircleFill } from 'react-icons/bs';
import { MdDelete } from 'react-icons/md';
import { toast } from 'react-toastify';
import { FiAlertTriangle } from 'react-icons/fi'
import { deleteRequest, getRequest, postRequest } from '../../Apis/Services/ApiMethods';
import { ADD_CATEGORY, DELETE_CATEGORY, GET_CATEGORIES } from '../../Apis/ApiEndpoints';

const CategoryPage = () => {
    const [categories, setCategories] = useState([]);
    const [newCategory, setNewCategory] = useState({ categoryName: '', categoryPriceLimit: '' });
    const [errors, setErrors] = useState([]);
    const [openCard, setOpenCard] = useState(false);
    const [openConfirm, setOpenConfirm] = useState(false);
    const [deleteCategoryName, setDeleteCategoryName] = useState();

    const columns = ['Name', 'Price', 'Action'];

    useEffect(() => {
        getRequest(GET_CATEGORIES, null, function (response) {
            if (response?.status === 200) {
                setCategories(response.data)
            } else {
                toast.error("Some error occured!!", {
                    position: toast.POSITION.TOP_RIGHT
                });
            }
        })
    }, [])

    const showPopUp = () => {
        setOpenCard(true);
    }

    const closePopUp = () => {
        setErrors([]);
        setNewCategory({ categoryName: '', categoryPriceLimit: '' });
        setOpenCard(false);
    }

    const validateFields = (e) => {
        let name = e?.target?.name;
        let value = e?.target?.value;

        let newValidationMessages = { ...errors };
        Object.keys(newValidationMessages).forEach(key => {
            if (newValidationMessages[key] === null) {
                delete newValidationMessages[key];
            }
        });

        let nameRegex = /^[a-zA-Z][a-zA-Z ]*$/;
        switch (name) {
            case "categoryName": {
                if (value.length < 4) {
                    newValidationMessages.nameError = "Category Name must be more than 3 words.";
                } else if (!nameRegex.test(value)) {
                    newValidationMessages.nameError = "Category name only contains alphabets.";
                } else {
                    newValidationMessages.nameError = ''
                }
                break;
            }

            case "categoryPriceLimit": {
                if (value.length <= 0 || isNaN(value)) {
                    newValidationMessages.priceError = "Please enter a Valid Number";
                } else if (parseInt(value) < 100) {
                    newValidationMessages.priceError = "Price Limit must be greater than 99.";
                } else {
                    newValidationMessages.priceError = ''
                }
                break;
            }
            default: {
                break;
            }
        }

        setErrors(newValidationMessages);

        var isValid = true;
        var errorKeys = Object.keys(newValidationMessages)
        errorKeys.map((e) => {
            if (newValidationMessages[e] !== '') {
                isValid = false;
            }
            return null;
        })
        if (errorKeys.length < 2) {
            isValid = false;
        }
        return isValid;
    }

    const handleAddCategory = () => {
        const payload = { categoryName: newCategory.categoryName, categoryPriceLimit: newCategory.categoryPriceLimit, categoryDescription: newCategory.categoryDescription };
        if (validateFields()) {
            postRequest(ADD_CATEGORY, payload, null, (response) => {
                if (response.status === 201) {
                    toast.success("Category added successfully.", {
                        position: toast.POSITION.TOP_RIGHT
                    });
                    setCategories([...categories, response.data]);
                } else {
                    toast.error(`${response.response.data.messages}`, {
                        position: toast.POSITION.TOP_RIGHT
                    });
                }
            })
            closePopUp();
        } else {
            toast.info("Please fill in all the fields!!", {
                position: toast.POSITION.TOP_CENTER
            });
        }
    }


    const handleConfirm = (val) => {
        setOpenConfirm(false);
        if (val === "delete") {
            handleDeleteCategory(deleteCategoryName)
        }
    }

    const handleOpenConfirmDelete = (categoryName) => {
        setOpenConfirm(true);
        setDeleteCategoryName(categoryName);
    }



    const handleDeleteCategory = (categoryName) => {
        let allcategories = [...categories];
        allcategories = allcategories.filter((category) => category.categoryName !== categoryName);
        setCategories(allcategories);

        deleteRequest(`${DELETE_CATEGORY}/${categoryName}`, (response) => {
            if (response.status === 200) {
                toast.success(`${response?.data?.message}`, {
                    position: toast.POSITION.TOP_RIGHT
                });
            } else {
                toast.error(`${response.response.data.errorMessage[0]}`, {
                    position: toast.POSITION.TOP_RIGHT
                });
            }
        })
    }



    const handleFieldChange = (e) => {
        setNewCategory({ ...newCategory, [e.target.name]: e.target.value })
        validateFields(e);
    }


    return (
        <>
            <div className={styles.reimbursecategory}>
                <CommonModal isOpen={openCard} onClose={closePopUp}>
                    <div className={styles.cardBody}>
                        <div className={styles.inputField}>
                            <label htmlFor='categoryName' className={styles.labelT}>Category Name<span className={styles.mandatoryField}>*</span></label>
                            <CommonInput className={styles.inputStyle} type='text' name='categoryName' value={newCategory.categoryName} handleChangeMethod={(e) => handleFieldChange(e)} placeholderText='Category name' />
                            {errors.nameError && <span className={styles.errorField}>{errors.nameError}</span>}
                        </div>

                        <div className={styles.inputField}>
                            <label htmlFor='categoryName' className={styles.labelT}>Category Price<span className={styles.mandatoryField}>*</span></label>
                            <CommonInput className={styles.inputStyle} type='text' name='categoryPriceLimit' value={newCategory.categoryPriceLimit} handleChangeMethod={(e) => handleFieldChange(e)} placeholderText='Max claim amount' />
                            {errors.priceError && <span className={styles.errorField}>{errors.priceError}</span>}
                        </div>

                        <CommonButton type="button" onClickBtn={handleAddCategory} className={styles.categoryAddBtn}>Add Category</CommonButton>
                    </div>
                </CommonModal>
                <div className={styles.header}>
                    <h1 className={styles.categoryTitle}>Categories:</h1>
                    <CommonButton type="button" onClickBtn={showPopUp} className={styles.addCategoryBtn}><span className={styles.iconStyle}><BsPlusCircleFill className={styles.plusIconStyle} /></span><span className={styles.btnContent}>Add Categories</span></CommonButton>
                </div>
                <CommonTable columns={columns} data={categories?.map((category) => ({
                    "Name": category.categoryName,
                    "Price": category.categoryPriceLimit,
                    "Action": (
                        <CommonButton type="button" onClickBtn={() => handleOpenConfirmDelete(category.categoryName)} className={styles.deleteIconBtn}> <span className={styles.btnContent}>Delete</span><span className={styles.deleteIconStyle}><MdDelete /></span></CommonButton>
                    ),
                }))} />

                <CommonModal isOpen={openConfirm} onClose={() => setOpenConfirm(false)}>
                    <div className={styles.alertIcon}><FiAlertTriangle fill={"#ed1d26"} className={styles.alertIconStyle} /></div>
                    <p className={styles.modelText}>Are you sure, you want to delete to this category.</p>
                    <div className={styles.actionBtns}>
                        <CommonButton type="button" onClickBtn={() => handleConfirm("delete")} className={styles.addCategoryDeleteBtn}>Delete</CommonButton>
                        <CommonButton type="button" onClickBtn={() => handleConfirm("cancle")} className={styles.addCategoryCancleBtn}>Cancel</CommonButton>
                    </div>
                </CommonModal>
            </div>
        </>
    )
}

export default CategoryPage