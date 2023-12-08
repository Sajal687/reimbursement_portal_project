package com.project.reimburse.repositories;

import com.project.reimburse.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This is a DepartmentRepo.
 */
public interface DepartmentRepo extends JpaRepository<Department, Integer> {
  /**
   * This method is used to check whether the department exists or not.
   *
   * @param departmentName Name of the department.
   * @return Boolean value , which specify whether name is present or not.
  */
  Boolean existsByDepartmentName(String departmentName);
}
