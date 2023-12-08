package com.project.reimburse.services.impl;

import com.project.reimburse.dtos.in.DepartmentInDtoClass;
import com.project.reimburse.dtos.out.DepartmentOutDtoClass;
import com.project.reimburse.entities.Department;
import com.project.reimburse.exceptions.BadRequestException;
import com.project.reimburse.exceptions.ResourceAlreadyExist;
import com.project.reimburse.repositories.DepartmentRepo;
import com.project.reimburse.services.DepartmentService;
import com.project.reimburse.utils.MessageConstants;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is a Department Service Implementation class.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
  /**
   * DepartmentRepo Instance.
   */
  @Autowired
  private DepartmentRepo departmentRepo;

  /**
   * This is a Logger Object.
   */
  private final Logger log = LoggerFactory
         .getLogger(DepartmentServiceImpl.class);

  /**
   * This is a Service Implementation used to create a new Department.
   *
   * @param departmentInDto This Object contains information of Department.
   * @return Response Object of type DepartmentOutDtoClass.
   */
  @Override
  public DepartmentOutDtoClass createDepartment(final DepartmentInDtoClass departmentInDto) {
    departmentInDto.setDepartmentName(
        departmentInDto.getDepartmentName().toUpperCase(Locale.ENGLISH));
    Boolean ifDepartmentExists = this.departmentRepo.existsByDepartmentName(
        departmentInDto.getDepartmentName());
    if (ifDepartmentExists) {
      throw new ResourceAlreadyExist(MessageConstants.DEPARTMENT_ALREADY_EXISTS);
    }
    log.info("Creating Department with department name {}", departmentInDto);
    Department department = inDtoToDepartment(departmentInDto);
    Department savedDepartment = this.departmentRepo.save(department);
    DepartmentOutDtoClass resp = departmentToOutDto(savedDepartment);
    return resp;
  }

  /**
   * This Service Implementation is used to get department by its ID.
   *
   * @param departmentId This is used to search for department by its ID.
   * @return This method returns DepartmentOutDtoClass Object containing the Department.
   */
  @Override
  public DepartmentOutDtoClass getDepartmentById(final int departmentId) {
    // TODO Auto-generated method stub
    log.info("Finding Department by ID.");
    Department department = this.departmentRepo.findById(
        departmentId).orElseThrow(() -> new BadRequestException(
        MessageConstants.DEPARTMENT_DOESNT_EXIST));
    log.info("Department find Successfully");
    DepartmentOutDtoClass resp = departmentToOutDto(department);
    return resp;
  }

  /**
   * This method is used to get all departments.
   *
   * @return This returns all available Departments.
   */
  @Override
    public List<DepartmentOutDtoClass> getAllDepartments() {
    List<Department> allDepartments = this.departmentRepo.findAll();
    List<DepartmentOutDtoClass> resp = allDepartments.stream().map(
         (department) -> departmentToOutDto(department)).collect(Collectors.toList());
    log.info("Getting all Deparmtment");
    return resp;
  }

  /**
   * This is a Custom Mapper for InDto to Deparmtent.
   *
 * @param inDto Takes InDto Object.
 * @return Department object.
 */
  public Department inDtoToDepartment(final DepartmentInDtoClass inDto) {
    Department department = new Department();
    department.setId(inDto.getId());
    department.setDepartmentName(inDto.getDepartmentName());
    return department;
  }

  /**
   * This is a Custom mapper for DepartmentS to DepartmentOutDtoClass.
   *
 * @param department Takes Department Object.
 * @return DepartmentOutDtoClass object.
 */
  public DepartmentOutDtoClass departmentToOutDto(final Department department) {
    DepartmentOutDtoClass outDtoClass = new DepartmentOutDtoClass();
    outDtoClass.setId(department.getId());
    outDtoClass.setDepartmentName(department.getDepartmentName());
    return outDtoClass;
  }
}
