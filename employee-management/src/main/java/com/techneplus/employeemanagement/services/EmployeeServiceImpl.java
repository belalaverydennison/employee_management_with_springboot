package com.techneplus.employeemanagement.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techneplus.employeemanagement.dto.EmployeeRequest;
import com.techneplus.employeemanagement.entity.Department;
import com.techneplus.employeemanagement.entity.Employee;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    EntityManager entityManager;

    @Override
    public List getAllEmployees() throws JsonProcessingException {
        String queryString = "SELECT json_build_object(" + "'departmentId', d.id, " + "'Name', d.name, "
                + "'noOfEmployees', count(e.id), " + "'totalSalary', sum(e.salary), "
                + "'employees', json_agg(json_build_object(" + "'employeeId', e.employee_id, "
                + "'dateOfBirth', e.date_of_birth, " +
                "'name', e.name, " +"'salary', e.salary" + "))" + ") AS department " + "FROM Dept d "
                + "JOIN Emp e ON d.id = e.department_id " + "GROUP BY d.id, d.name";

        List resultList = entityManager.createNativeQuery(queryString).getResultList();
        return new ObjectMapper().readValue(String.valueOf(resultList), List.class);
    }

    @Override
    public ResponseEntity addEmployee(EmployeeRequest employeeRequest) {
        Department department = null;

        // Mapping Request With Employee Object Using Constructor
        Employee employee = new Employee(employeeRequest.getName(),employeeRequest.getSalary(),employeeRequest.getDateOfBirth(),
                employeeRequest.getEmployeeId(), department);

        System.out.println(employee);
        // if Department ID is passed then fetch the department details by ID.
        if (employeeRequest.getDepartmentId() != null) {
            department = entityManager.find(Department.class, employeeRequest.getDepartmentId());
        }

        // If Department is not found by Department ID, then check by Department Name.
        if (department == null) {
            String queryString = "SELECT d FROM Department d WHERE d.name = :departmentName";
            TypedQuery<Department> query = entityManager.createQuery(queryString, Department.class);
            query.setParameter("departmentName", employeeRequest.getDepartmentName());
            List<Department> departmentList = query.getResultList();

            // if Department is found then fetch the details else It is a new Department
             if (!departmentList.isEmpty()) {
                 department = departmentList.get(0);
            } else {
                 //creating a new Department
                department = new Department();
                department.setName(employeeRequest.getDepartmentName());
                entityManager.persist(department);
            }

        }
        //Map the Employee with the Department
        employee.setDepartment(department);

        //Save the Employee
        entityManager.persist(employee);
        entityManager.flush();
        //Return Response
        return new ResponseEntity(Map.of("Message","Successfully Added Employee "+employeeRequest.getName()+" to "+employee.getDepartment().getName()), HttpStatus.CREATED) ;
    }

    @Override
    public Department getDepartmentWithMaxSalary(){
        String queryString = "SELECT d, SUM(e.salary) AS totalSalary " +
                "FROM Department d " +
                "JOIN Employee e ON d.id = e.department.id " +
                "GROUP BY d.id, d.name " +
                "ORDER BY totalSalary Desc";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        query.setMaxResults(1);

        List<Object[]> result = query.getResultList();
        if (!result.isEmpty()) {
            Department department = (Department) result.get(0)[0];
            Double maxSalary = (Double) result.get(0)[1];
            department.setMaxSalary(maxSalary);
            return department;
        } else {
            return null;
        }
    }


    public List<Department> getAllDepartments() {
        return entityManager.createQuery("SELECT d FROM Department d", Department.class).getResultList();
    }

    public Department getDepartmentWithMaxEmployeeSalary() throws JsonProcessingException {
        String queryString = "SELECT d, MAX(e.salary) AS maxSalary FROM Department d "
                + "JOIN Employee e ON d.id = e.department.id GROUP BY d.id, d.name " + "ORDER BY maxSalary DESC";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        query.setMaxResults(1);

        List<Object[]> result = query.getResultList();
        if (!result.isEmpty()) {
            Department department = (Department) result.get(0)[0];
            Double maxSalary = (Double) result.get(0)[1];
            department.setMaxSalary(maxSalary);
            return department;
        } else {
            return null;
        }
    }
}
