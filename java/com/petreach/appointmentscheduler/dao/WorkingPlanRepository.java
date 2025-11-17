package com.petreach.appointmentscheduler.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petreach.appointmentscheduler.entity.WorkingPlan;

public interface WorkingPlanRepository extends JpaRepository<WorkingPlan, Integer> {
    @Query("select w from WorkingPlan w where w.provider.id = :providerId")
    WorkingPlan getWorkingPlanByProviderId(@Param("providerId") int providerId);
}
