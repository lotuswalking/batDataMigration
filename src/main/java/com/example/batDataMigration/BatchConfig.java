package com.example.batDataMigration;

import com.example.batDataMigration.primary.Employee;
import com.example.batDataMigration.secondary.Manager;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;
import org.springframework.batch.item.database.orm.HibernateNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    MyCustomReader myCustomReader;

    @Autowired
    MyCustomWriter myCustomWriter;

    @Autowired
    MyCustomProcessor myCustomProcessor;
    @Autowired
    SessionFactory sessionFactory;

    @Bean
    public Job createJob() {
        try {
            return jobBuilderFactory.get("MyJob")
                    .incrementer(new RunIdIncrementer())
                    .flow(createStep())
                    .end()
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public Step createStep() throws Exception {
        HibernateNativeQueryProvider<Employee> provider = new HibernateNativeQueryProvider<>();
        provider.setEntityClass(Employee.class);
        provider.setSqlQuery("select * from Employee");
        provider.afterPropertiesSet();

        HibernateCursorItemReader<Employee> reader = new HibernateCursorItemReaderBuilder<Employee>()
                .name("employeeReader")
                .sessionFactory(this.sessionFactory)
                .queryProvider(provider)
                .fetchSize(1)
                .build();
        return stepBuilderFactory.get("MyStep")
                .<Employee, Manager> chunk(1)
                .reader(reader)
                .processor(myCustomProcessor)
                .writer(myCustomWriter)
                .build();
    }
}