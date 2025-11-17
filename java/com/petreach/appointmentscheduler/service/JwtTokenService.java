package com.petreach.appointmentscheduler.service;

import java.time.LocalDateTime;
import java.util.Date;

import com.petreach.appointmentscheduler.entity.Appointment;

public interface JwtTokenService {
    String generateAppointmentRejectionToken(Appointment appointment);

    String generateAcceptRejectionToken(Appointment appointment);

    boolean validateToken(String token);

    int getAppointmentIdFromToken(String token);

    int getCustomerIdFromToken(String token);

    int getProviderIdFromToken(String token);

    ////
    Date convertLocalDateTimeToDate(LocalDateTime localDateTime);
}
