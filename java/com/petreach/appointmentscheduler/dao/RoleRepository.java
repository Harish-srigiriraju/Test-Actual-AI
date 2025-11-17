package com.petreach.appointmentscheduler.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petreach.appointmentscheduler.entity.user.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String roleName);
}
