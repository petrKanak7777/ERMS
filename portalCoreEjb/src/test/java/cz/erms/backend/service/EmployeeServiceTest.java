package cz.erms.backend.service;

import cz.erms.backend.converter.EmployeeResponseConverter;
import cz.erms.backend.ejb.entity.Employee;
import cz.erms.backend.ejb.repository.EmployeeRepository;
import cz.erms.backend.error.exception.ResourceNotFoundException;
import cz.erms.backend.model.request.EmployeeRequest;
import cz.erms.backend.model.response.EmployeeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private static final String EMPLOYEE_UUID_0 = UUID.randomUUID().toString();
    private static final String EMPLOYEE_FIRST_NAME_0 = "employeeFirstName0";
    private static final String EMPLOYEE_LAST_NAME_0 = "employeeLastName0";
    private static final String EMPLOYEE_EMAIL_0 = "employeeEmail0";

    private static final String EMPLOYEE_UUID_1 = UUID.randomUUID().toString();
    private static final String EMPLOYEE_FIRST_NAME_1 = "employeeFirstName1";
    private static final String EMPLOYEE_LAST_NAME_1 = "employeeLastName1";
    private static final String EMPLOYEE_EMAIL_1 = "employeeEmail1";

    private static final String EMPLOYEE_UUID_2 = UUID.randomUUID().toString();

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private EmployeeResponseConverter employeeResponseConverter;

    @Test
    void getAllEmployees_whenMethodIsCalled_thenCorrectResultIsReturned() {
        when(employeeRepository.findAll()).thenReturn(mockEmployees());

        List<EmployeeResponse> employees = employeeService.getAllEmployees();

        assertEquals(2, employees.size());
        verify(employeeRepository, times(1)).findAll();

        assertEquals(EMPLOYEE_UUID_0, employees.getFirst().getId().toString());
        assertEquals(EMPLOYEE_FIRST_NAME_0, employees.getFirst().getFirstName());
        assertEquals(EMPLOYEE_LAST_NAME_0, employees.getFirst().getLastName());
        assertEquals(EMPLOYEE_EMAIL_0, employees.getFirst().getEmail());

        assertEquals(EMPLOYEE_UUID_1, employees.get(1).getId().toString());
        assertEquals(EMPLOYEE_FIRST_NAME_1, employees.get(1).getFirstName());
        assertEquals(EMPLOYEE_LAST_NAME_1, employees.get(1).getLastName());
        assertEquals(EMPLOYEE_EMAIL_1, employees.get(1).getEmail());
    }

    @Test
    void getEmployeeById_whenMethodIsCalled_thenCorrectResultIsReturned() {
        when(employeeRepository.findById(UUID.fromString(EMPLOYEE_UUID_0))).thenReturn(Optional.of(mockEmployee()));

        EmployeeResponse employee = employeeService.getEmployeeById(UUID.fromString(EMPLOYEE_UUID_0));

        assertNotNull(employee);
        verify(employeeRepository, times(1)).findById(UUID.fromString(EMPLOYEE_UUID_0));
        assertEquals(EMPLOYEE_UUID_0, employee.getId().toString());
        assertEquals(EMPLOYEE_FIRST_NAME_0, employee.getFirstName());
        assertEquals(EMPLOYEE_LAST_NAME_0, employee.getLastName());
        assertEquals(EMPLOYEE_EMAIL_0, employee.getEmail());
    }

    @Test
    void getEmployeeById_whenUnknownUUIDUsed_thenThrowException() {
        when(employeeRepository.findById(UUID.fromString(EMPLOYEE_UUID_0))).thenReturn(Optional.empty());

        UUID uuid = UUID.fromString(EMPLOYEE_UUID_0);

        assertThatThrownBy(() -> employeeService.getEmployeeById(uuid))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Related Employee not found");
        verify(employeeRepository, times(1)).findById(UUID.fromString(EMPLOYEE_UUID_0));
    }

    @Test
    void createEmployee_whenCorrectRequestUsed_thenSaveFile() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee());

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName(EMPLOYEE_FIRST_NAME_0);
        employeeRequest.setLastName(EMPLOYEE_LAST_NAME_0);
        employeeRequest.setEmail(EMPLOYEE_EMAIL_0);

        EmployeeResponse employee = employeeService.createEmployee(employeeRequest);

        assertNotNull(employee);
        verify(employeeRepository, times(1)).save(any(Employee.class));
        assertEquals(EMPLOYEE_UUID_0, employee.getId().toString());
        assertEquals(EMPLOYEE_FIRST_NAME_0, employee.getFirstName());
        assertEquals(EMPLOYEE_LAST_NAME_0, employee.getLastName());
        assertEquals(EMPLOYEE_EMAIL_0, employee.getEmail());
    }

    @Test
    void updateEmployee_whenCorrectRequestUsed_thenSaveEmployee() {
        UUID employeeUUID0 = UUID.fromString(EMPLOYEE_UUID_0);

        when(employeeRepository.findById(employeeUUID0)).thenReturn(Optional.of(mockEmployee()));
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee());

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName(EMPLOYEE_FIRST_NAME_0);
        employeeRequest.setLastName(EMPLOYEE_LAST_NAME_0);
        employeeRequest.setEmail(EMPLOYEE_EMAIL_0);

        EmployeeResponse employee = employeeService.updateEmployee(employeeUUID0, employeeRequest);

        assertNotNull(employee);
        verify(employeeRepository, times(1)).findById(employeeUUID0);
        verify(employeeRepository, times(1)).save(any(Employee.class));
        assertEquals(EMPLOYEE_UUID_0, employee.getId().toString());
        assertEquals(EMPLOYEE_FIRST_NAME_0, employee.getFirstName());
        assertEquals(EMPLOYEE_LAST_NAME_0, employee.getLastName());
        assertEquals(EMPLOYEE_EMAIL_0, employee.getEmail());
    }

    @Test
    void updateEmployee_whenEmployeeNotExists_thenThrowException() {
        UUID employeeUUID2 = UUID.fromString(EMPLOYEE_UUID_2);

        when(employeeRepository.findById(employeeUUID2)).thenReturn(Optional.empty());

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName(EMPLOYEE_FIRST_NAME_0);
        employeeRequest.setLastName(EMPLOYEE_LAST_NAME_0);
        employeeRequest.setEmail(EMPLOYEE_EMAIL_0);

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(employeeUUID2, employeeRequest);
        });

        verify(employeeRepository, times(1)).findById(employeeUUID2);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_whenEmployeeExists_thenDeleteEmployee() {
        UUID employeeUUID0 = UUID.fromString(EMPLOYEE_UUID_0);

        when(employeeRepository.findById(employeeUUID0)).thenReturn(Optional.of(mockEmployee()));

        employeeService.deleteEmployee(employeeUUID0);

        verify(employeeRepository, times(1)).findById(employeeUUID0);
        verify(employeeRepository, times(1)).deleteById(employeeUUID0);
    }

    @Test
    void deleteEmployee_whenEmployeeNotExists_thenThrowException() {
        UUID employeeUUID2 = UUID.fromString(EMPLOYEE_UUID_2);

        when(employeeRepository.findById(employeeUUID2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(employeeUUID2);
        });

        verify(employeeRepository, times(1)).findById(employeeUUID2);
        verify(employeeRepository, times(0)).deleteById(employeeUUID2);
    }

    private List<Employee> mockEmployees() {
        return Arrays.asList(
                mockEmployee(EMPLOYEE_EMAIL_0),
                mockEmployee(EMPLOYEE_EMAIL_1)
        );
    }

    private Employee mockEmployee(String employeeEmail) {
        return (EMPLOYEE_EMAIL_1.equals(employeeEmail)) ? new Employee(
                UUID.fromString(EMPLOYEE_UUID_1),
                EMPLOYEE_FIRST_NAME_1,
                EMPLOYEE_LAST_NAME_1,
                EMPLOYEE_EMAIL_1
        ) : new Employee(
                UUID.fromString(EMPLOYEE_UUID_0),
                EMPLOYEE_FIRST_NAME_0,
                EMPLOYEE_LAST_NAME_0,
                EMPLOYEE_EMAIL_0
        );
    }

    private Employee mockEmployee() {
        return mockEmployee(EMPLOYEE_EMAIL_0);
    }
}
