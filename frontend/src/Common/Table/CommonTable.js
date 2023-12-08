import React from 'react';
import styles from './commonTable.module.css';

function CommonTable({ columns, data }) {
  return (

    <table className={styles.commonTable}>
      <thead>
        <tr>
          {columns?.map((column, index) => (
            <th key={index}>{column}</th>
          ))} 
        </tr>
      </thead>
      <tbody>
        {
          data?.length === 0 && <p className={styles.dataNotFound}>No records found!!</p>
        }
        {data?.map((row, rowIndex) => (
          <tr key={rowIndex}>
            {columns?.map((column, colIndex) => (
                <td key={colIndex}>{row[column]}</td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default CommonTable;
