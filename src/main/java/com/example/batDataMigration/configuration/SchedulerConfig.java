package com.example.batDataMigration.configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.example.batDataMigration.primary.Employee;
import com.example.batDataMigration.primary.EmployeeRepository;
import com.example.batDataMigration.secondary.Manager;
import com.example.batDataMigration.secondary.ManagerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ManagerRepository managerRepository;



    @Scheduled(fixedDelay = 24*3600*1000, initialDelay = 5000)
//    @Scheduled(cron = "0 */10 * * * *")  //Every 10 minutes
    public void scheduleByFixedRate() throws Exception {
        System.out.println("Batch job starting");
        List<Employee> employeeList = employeeRepository.findAll();
        for(Employee employee : employeeList) {
            ModelMapper modelMapper = new ModelMapper();
            TypeMap<Employee, Manager> propertyMapper = modelMapper.createTypeMap(Employee.class,Manager.class);
            propertyMapper.addMappings(mapper -> mapper.skip(Manager::setManagerId));
            Manager manager = modelMapper.map(employee, Manager.class);
            managerRepository.save(manager);
        }
        System.out.println("********Batch job executed successfully*************\n");
    }
}