package com.example.batDataMigration;

import java.util.List;
import java.util.Optional;

import com.example.batDataMigration.secondary.Manager;
import com.example.batDataMigration.secondary.ManagerRepository;
import lombok.extern.java.Log;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log
@Component
public class MyCustomWriter implements ItemWriter<Manager> {

    @Autowired
    ManagerRepository managerRepository ;

    @Override
    public void write(List<? extends Manager> list) throws Exception {
        for (Manager data : list) {
            //仅仅考虑保存非重复数据
            log.info(String.format("*********new date income with id:%d",data.getId()));
            Optional<Manager> manager = managerRepository.findById(data.getId());
            if(!manager.isPresent())
            {
                managerRepository.save(data);
                log.info("MyCustomWriter    : Writing data    : " + data.getId()+" : "+data.getName()+" : "+data.getSalary());
            }else
            {
                log.info("duplicate Date. will ignored!*************");
            }

        }
    }
}