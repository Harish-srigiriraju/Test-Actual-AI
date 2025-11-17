package com.petreach.appointmentscheduler.service;

import java.io.File;
import java.util.List;

import com.petreach.appointmentscheduler.entity.Invoice;
import com.petreach.appointmentscheduler.security.CustomUserDetails;

public interface InvoiceService {
    void createNewInvoice(Invoice invoice);

    Invoice getInvoiceByAppointmentId(int appointmentId);

    Invoice getInvoiceById(int invoiceId);

    List<Invoice> getAllInvoices();

    void changeInvoiceStatusToPaid(int invoiceId);

    void issueInvoicesForConfirmedAppointments();

    String generateInvoiceNumber();

    File generatePdfForInvoice(int invoiceId);

    boolean isUserAllowedToDownloadInvoice(CustomUserDetails user, Invoice invoice);
}

