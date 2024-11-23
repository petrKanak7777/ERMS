package cz.erms.backend.service;

import cz.erms.backend.converter.EmployeeResponseConverter;
import cz.erms.backend.ejb.entity.Employee;
import cz.erms.backend.ejb.repository.EmployeeRepository;
import cz.erms.backend.error.exception.ResourceNotFoundException;
import cz.erms.backend.model.request.EmployeeRequest;
import cz.erms.backend.model.response.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EmployeeService {

    private static final String EMPLOYEE_NOT_FOUND = "Related Employee not found";

    private final EmployeeRepository employeeRepository;
    private final EmployeeResponseConverter employeeResponseConverter;

    @Autowired
    public EmployeeService(
            EmployeeRepository employeeRepository,
            EmployeeResponseConverter employeeResponseConverter) {
        this.employeeRepository = employeeRepository;
        this.employeeResponseConverter = employeeResponseConverter;
    }

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees
                .stream()
                .map(employeeResponseConverter)
                .toList();
    }

    public EmployeeResponse getEmployeeById(UUID uuid) {
        Employee existingEmployee = employeeRepository.findById(uuid).orElse(null);
        if (Objects.isNull(existingEmployee)) {
            throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND);
        }

        return employeeResponseConverter.apply(existingEmployee);
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {

        Employee saveEmployee = new Employee();
        saveEmployee.setFirstName(employeeRequest.getFirstName());
        saveEmployee.setLastName(employeeRequest.getLastName());
        saveEmployee.setEmail(employeeRequest.getEmail());

        Employee employee = employeeRepository.save(saveEmployee);

        return employeeResponseConverter.apply(employee);
    }

    public EmployeeResponse updateEmployee(UUID uuid, EmployeeRequest employeeRequest) {
        Employee existingEmployee = employeeRepository.findById(uuid).orElse(null);
        if (Objects.isNull(existingEmployee)) {
            throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND);
        }

        existingEmployee.setFirstName(employeeRequest.getFirstName());
        existingEmployee.setLastName(employeeRequest.getLastName());
        existingEmployee.setEmail(employeeRequest.getEmail());
        Employee employee = employeeRepository.save(existingEmployee);

        return employeeResponseConverter.apply(employee);
    }

    public void deleteEmployee(UUID uuid) {
        Employee existingEmployee = employeeRepository.findById(uuid).orElse(null);
        if (Objects.isNull(existingEmployee)) {
            throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND);
        }

        employeeRepository.deleteById(uuid);
    }
}
