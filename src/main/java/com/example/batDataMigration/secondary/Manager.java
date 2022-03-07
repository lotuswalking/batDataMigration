package com.example.batDataMigration.secondary;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Manager {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long managerId;
    private String name;
    private int salary;
}