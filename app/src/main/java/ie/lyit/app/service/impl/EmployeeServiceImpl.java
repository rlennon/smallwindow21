package ie.lyit.app.service.impl;

import ie.lyit.app.domain.Employee;
import ie.lyit.app.repository.EmployeeRepository;
import ie.lyit.app.service.EmployeeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> partialUpdate(Employee employee) {
        log.debug("Request to partially update Employee : {}", employee);

        return employeeRepository
            .findById(employee.getId())
            .map(
                existingEmployee -> {
                    if (employee.getFirstName() != null) {
                        existingEmployee.setFirstName(employee.getFirstName());
                    }
                    if (employee.getLastName() != null) {
                        existingEmployee.setLastName(employee.getLastName());
                    }
                    if (employee.getEmail() != null) {
                        existingEmployee.setEmail(employee.getEmail());
                    }
                    if (employee.gets3ImageKey() != null) {
                        existingEmployee.sets3ImageKey(employee.gets3ImageKey());
                    }

                    return existingEmployee;
                }
            )
            .map(employeeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
