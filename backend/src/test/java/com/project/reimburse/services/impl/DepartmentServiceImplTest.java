package com.project.reimburse.services.impl;

import com.project.reimburse.dtos.in.DepartmentInDtoClass;
import com.project.reimburse.dtos.out.DepartmentOutDtoClass;
import com.project.reimburse.entities.Department;
import com.project.reimburse.exceptions.ResourceAlreadyExist;
import com.project.reimburse.repositories.DepartmentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDepartment() {
        DepartmentInDtoClass departmentInDto = new DepartmentInDtoClass(0 , "IT");
        Department department = new Department(1, "IT", null);
        
        when(departmentRepo.save(any(Department.class))).thenReturn(department);

        DepartmentOutDtoClass result = departmentService.createDepartment(departmentInDto);

        assertThat(result).isNotNull();
        assertThat(result.getDepartmentName()).isEqualTo("IT");
    }
    
    @Test
    public void testCreateDepartment_DepartmentAlreadyExists() {
        DepartmentInDtoClass departmentInDto = new DepartmentInDtoClass();
        departmentInDto.setDepartmentName("IT");
        
        when(departmentRepo.existsByDepartmentName("IT")).thenReturn(true);

        assertThrows(ResourceAlreadyExist.class,() -> departmentService.createDepartment(departmentInDto));
    }

    @Test
    public void testGetDepartmentById() {
        int departmentId = 1;
        Department department = new Department(departmentId, "IT", null);
        when(departmentRepo.findById(departmentId)).thenReturn(Optional.of(department));

        DepartmentOutDtoClass result = departmentService.getDepartmentById(departmentId);

        assertThat(result).isNotNull();
        assertThat(result.getDepartmentName()).isEqualTo("IT");
    }

    @Test
    public void testGetAllDepartments() {
        Department department1 = new Department(1, "IT", null);
        Department department2 = new Department(2, "HR", null);
        List<Department> departments = Arrays.asList(department1, department2);
        when(departmentRepo.findAll()).thenReturn(departments);

        List<DepartmentOutDtoClass> result = departmentService.getAllDepartments();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getDepartmentName()).isEqualTo("IT");
        assertThat(result.get(1).getDepartmentName()).isEqualTo("HR");
    }
    
    @Test
    public void testInDtoToDepartment() {
    	DepartmentInDtoClass departmentInDto = new DepartmentInDtoClass();
    	departmentInDto.setDepartmentName("department");
    	departmentInDto.setId(1);
    	
    	Department department = departmentService.inDtoToDepartment(departmentInDto);
    	
    	assertNotNull(department);
    	assertThat(departmentInDto.getDepartmentName()).isEqualTo(department.getDepartmentName());
        assertThat(departmentInDto.getId()).isEqualTo(department.getId());
    	
    }
    
    @Test
    public void testDepartmentOutDtoClass() {
    	Department department = new Department();
    	department.setDepartmentName("department");
    	department.setId(1);
    	
    	DepartmentOutDtoClass departmentOutDtoClass = departmentService.departmentToOutDto(department);
    	assertThat(departmentOutDtoClass.getDepartmentName()).isEqualTo(department.getDepartmentName());
        assertThat(departmentOutDtoClass.getId()).isEqualTo(department.getId());
    	
    }
}
