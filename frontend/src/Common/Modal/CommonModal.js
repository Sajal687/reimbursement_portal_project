import React from 'react';
import styles from './commonModal.module.css';
import {AiFillCloseSquare} from 'react-icons/ai';

const CommonModal = ({ isOpen, onClose, title, children }) => {
  return (
    isOpen && (
      <div className={styles.modalOverlay}>
        <div className={styles.modal}>
          <div className={styles.modalHeader}>
            <h4>{title}</h4>      
            <span
              className={styles.closeIcon}
              onClick={onClose}
            >
              <AiFillCloseSquare fill={"black"} className={styles.crossIconStyle}/>
            </span>
          </div>
          <div className={styles.modalContent}>
            {children}
          </div>
          <div className={styles.modalHeader}>
           
          </div>
        </div>
      </div>
    )
  );
};

export default CommonModal;
