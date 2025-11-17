package com.petreach.appointmentscheduler.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petreach.appointmentscheduler.entity.ExchangeRequest;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, Integer> {
}
