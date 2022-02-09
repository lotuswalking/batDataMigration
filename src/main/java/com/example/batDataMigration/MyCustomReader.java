package com.example.batDataMigration;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.example.batDataMigration.primary.Employee;
import lombok.extern.java.Log;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
@Log
public class MyCustomReader extends JdbcCursorItemReader<Employee> implements ItemReader<Employee> {

    public MyCustomReader(@Autowired DataSource primaryDataSource) {
        log.info("Start to read employees");
        setDataSource(primaryDataSource);
        setSql("SELECT id, name, salary FROM employee");
        setFetchSize(1);
        setRowMapper(new EmployeeRowMapper());
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