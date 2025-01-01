package com.yousseftayek.springdatajpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Mission {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String period;
    @ManyToMany(mappedBy = "missions")
    List<Employee> employees;
}
