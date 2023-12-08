import React from 'react';

const CommonInput = ({ type, name, isRequired, value, handleChangeMethod, placeholderText , accept , customStyle , max , min , className}) => {
    return (
        <input type={type} name={name} required={isRequired} accept={accept} value={value} onChange={handleChangeMethod} max={max} min={min} placeholder={placeholderText} className={className}/>
    )
}

export default CommonInput; 