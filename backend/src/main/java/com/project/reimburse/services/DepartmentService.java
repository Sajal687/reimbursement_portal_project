package com.project.reimburse.services;

import com.project.reimburse.dtos.in.DepartmentInDtoClass;
import com.project.reimburse.dtos.out.DepartmentOutDtoClass;
import java.util.List;


/**
 * This is a DepartmentService Interface.
 */
public interface DepartmentService {
  /**
  * This is a Service used to create a new Department.
  *
  * @param departmentDto This Object contains information of Department.
  * @return Response Object of type DepartmentOutDtoClass.
  */
  DepartmentOutDtoClass createDepartment(DepartmentInDtoClass departmentDto);

  /**
   * This Service is used to get department by its ID.
   *
   * @param departmentId This is used to search for department by its ID.
   * @return This method returns DepartmentOutDtoClass Object containing the Department.
   */
  DepartmentOutDtoClass getDepartmentById(int departmentId);

  /**
   * This method is used to get all departments.
   *
   * @return This returns all available Departments.
   */
  List<DepartmentOutDtoClass> getAllDepartments();
}
