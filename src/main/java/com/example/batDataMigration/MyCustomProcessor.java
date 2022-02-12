package com.example.batDataMigration;

import com.example.batDataMigration.primary.Employee;
import com.example.batDataMigration.secondary.Manager;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


@Component
public class MyCustomProcessor implements ItemProcessor<Employee, Manager> {

    @Override
    public Manager process(Employee emp) throws Exception {
        System.out.println("MyBatchProcessor : Processing data : " + emp);
        //这个方法不错，用ModelMapper，将所有的的字段自动进行对应，这样字段就不需要一一拷贝了
        ModelMapper modelMapper = new ModelMapper();
        Manager manager = modelMapper.map(emp, Manager.class);
        //-------------------------------------------------------------------
//        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
//        manager.setId(emp.getId());
//        manager.setName(emp.getName().toUpperCase());
//        manager.setSalary(emp.getSalary());
        return manager;
    }
}