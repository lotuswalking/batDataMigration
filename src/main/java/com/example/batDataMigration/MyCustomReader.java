package com.example.batDataMigration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import com.example.batDataMigration.primary.Employee;
import com.example.batDataMigration.primary.EmployeeRepository;
import com.example.batDataMigration.secondary.Manager;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
@Log
public class MyCustomReader extends JdbcCursorItemReader<Employee> implements ItemReader<Employee> {
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }
    @Autowired
    EmployeeRepository employeeRepository;

    @Value("lastRuntime")
    String lastRuntime;

    public MyCustomReader(@Autowired DataSource primaryDataSource) {
        log.info("Start to read employees"+this.lastRuntime);
        lastRuntime = LocalDate.now().toString();
        setDataSource(primaryDataSource);
        setSql("SELECT * FROM employee");
        setFetchSize(1);
        setRowMapper(new EmployeeRowMapper());
        log.info("End to read employees:"+lastRuntime);
    }

    public class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setName(rs.getString("name"));
            employee.setSalary(rs.getInt("salary"));
            return employee;
        }
    }
}