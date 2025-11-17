package com.petreach.appointmentscheduler.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petreach.appointmentscheduler.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

}
