package com.petreach.appointmentscheduler.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.petreach.appointmentscheduler.entity.BaseEntity;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
