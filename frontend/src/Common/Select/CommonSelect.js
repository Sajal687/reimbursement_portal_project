import React from 'react';
import styles from './commonSelect.module.css';

const CommonSelect = ({ name, value, options, onChange , className , disableOption}) => {
  return (
    <select name={name} value={value} onChange={onChange} className={className === '' ? styles.selectTag : className}>
      {options.map((option) => (
        <option key={option.value} value={option.value} className={styles.option} disabled={option.value === disableOption}>
          {option.label}
        </option>
      ))}
    </select>
  );
};

export default CommonSelect;