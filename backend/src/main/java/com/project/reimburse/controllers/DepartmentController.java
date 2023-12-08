package com.project.reimburse.controllers;

import com.project.reimburse.dtos.in.DepartmentInDtoClass;
import com.project.reimburse.dtos.out.DepartmentOutDtoClass;
import com.project.reimburse.services.DepartmentService;
import com.project.reimburse.utils.ApiHomePath;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a Controller for Department.
 */
@RestController
@CrossOrigin
@RequestMapping(ApiHomePath.DEPARTMENT_HOME_PATH)
public class DepartmentController {

  /**
  * DepartmentService Instance.
  */
  @Autowired
  private DepartmentService departmentService;

  /**
   * This is a Logger Object.
   */
  private final Logger log = LoggerFactory
         .getLogger(DepartmentController.class);

  /**
  * This is a Controller used to create a new Department.
  *
  * @param departmentDto This Object contains information of Department.
  * @return Response Object of type DepartmentOutDto with Http Status Created.
  */
  @PostMapping("/department")
    public ResponseEntity<DepartmentOutDtoClass> createDepartment(
      final @Valid  @RequestBody DepartmentInDtoClass departmentDto) {
    log.info("Creating a new Department");
    final DepartmentOutDtoClass resp = this.departmentService.createDepartment(departmentDto);
    log.info("Department created successfully");
    return new ResponseEntity<DepartmentOutDtoClass>(resp, HttpStatus.CREATED);
  }

  /**
  * This controller is used to get department by its ID.
  *
  * @param departmentId This is used to search for department by its ID.
  * @return This method returns DepartmentOutDto Object containing the Department.
  */
  @GetMapping("/department/{departmentId}")
   public ResponseEntity<DepartmentOutDtoClass> getDepartmentById(
      final @PathVariable int departmentId) {
    log.info("Fetching Department by ID: {}", departmentId);
    final DepartmentOutDtoClass resp = this.departmentService.getDepartmentById(departmentId);
    log.info("Fetched Department by ID: {}", departmentId);
    return new ResponseEntity<DepartmentOutDtoClass>(resp, HttpStatus.OK);
  }

  /**
   * This method is used to get all departments.
   *
   * @return This returns all available Departments.
   */
  @GetMapping("/departments")
    public ResponseEntity<List<DepartmentOutDtoClass>> getAllDepartments() {
    log.info("Fetching all Departments");
    final List<DepartmentOutDtoClass> resp = this.departmentService.getAllDepartments();
    log.info("Fetched all Departments");
    return new ResponseEntity<List<DepartmentOutDtoClass>>(resp, HttpStatus.OK);
  }
}
