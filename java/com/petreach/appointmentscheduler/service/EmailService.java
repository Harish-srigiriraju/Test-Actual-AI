package com.petreach.appointmentscheduler.service;

import org.thymeleaf.context.Context;

import com.petreach.appointmentscheduler.entity.Appointment;
import com.petreach.appointmentscheduler.entity.ChatMessage;
import com.petreach.appointmentscheduler.entity.ExchangeRequest;
import com.petreach.appointmentscheduler.entity.Invoice;

import java.io.File;

public interface EmailService {
    void sendEmail(String to, String subject, String templateName, Context templateContext, File attachment);

    void sendAppointmentFinishedNotification(Appointment appointment);

    void sendAppointmentRejectionRequestedNotification(Appointment appointment);

    void sendNewAppointmentScheduledNotification(Appointment appointment);

    void sendAppointmentCanceledByCustomerNotification(Appointment appointment);

    void sendAppointmentCanceledByProviderNotification(Appointment appointment);

    void sendInvoice(Invoice invoice);

    void sendAppointmentRejectionAcceptedNotification(Appointment appointment);

    void sendNewChatMessageNotification(ChatMessage appointment);

    void sendNewExchangeRequestedNotification(Appointment oldAppointment, Appointment newAppointment);

    void sendExchangeRequestAcceptedNotification(ExchangeRequest exchangeRequest);

    void sendExchangeRequestRejectedNotification(ExchangeRequest exchangeRequest);
}
