package cz.erms.backend.converter;

import cz.erms.backend.ejb.entity.Employee;
import cz.erms.backend.model.response.EmployeeResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EmployeeResponseConverter implements Function<Employee, EmployeeResponse> {

    @Override
    public EmployeeResponse apply(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setId(employee.getId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setEmail(employee.getEmail());

        return employeeResponse;
    }
}
