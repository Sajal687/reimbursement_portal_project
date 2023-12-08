import React from 'react'


const CommonButton = ({ name, type, active, onClickBtn, children , className , disable}) => {
  active = active === undefined ? "NotSame" : active;
  return (
    <button
      name={name}
      onClick={onClickBtn}
      type={type}
      className={className}
      disabled={disable}
    >
      {children}
    </button>
  )
}

export default CommonButton