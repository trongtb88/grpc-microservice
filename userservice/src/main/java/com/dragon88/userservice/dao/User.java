package com.dragon88.userservice.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private Long id;

    private String name;
}
