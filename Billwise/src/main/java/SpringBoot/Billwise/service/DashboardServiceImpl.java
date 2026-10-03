package SpringBoot.Billwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringBoot.Billwise.dto.DashboardSummary;
import SpringBoot.Billwise.entity.InvoiceStatus;
import SpringBoot.Billwise.entity.ReminderStatus;
import SpringBoot.Billwise.repository.CustomerRepository;
import SpringBoot.Billwise.repository.InvoiceRepository;
import SpringBoot.Billwise.repository.ReminderAttemptRepository;

@Service
public class DashboardServiceImpl
        implements DashboardService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ReminderAttemptRepository reminderAttemptRepository;

    @Override
    public DashboardSummary getDashboardSummary() {

        DashboardSummary summary = new DashboardSummary();

        // Total Customers
        summary.setTotalCustomers(
                customerRepository.count());

        // Total Invoices
        summary.setTotalInvoices(
                invoiceRepository.count());
        summary.setPaidInvoices(
                invoiceRepository
                        .findByStatus(InvoiceStatus.PAID)
                        .size());        // Pending Invoices
        summary.setPendingInvoices(
                invoiceRepository
                        .findByStatus(InvoiceStatus.PENDING)
                        .size());
        
        summary.setOverdueInvoices(
                invoiceRepository
                        .findByStatus(InvoiceStatus.OVERDUE)
                        .size());

        // Total Reminder Attempts
        summary.setTotalReminderAttempts(
                reminderAttemptRepository.count());

        // Successful Reminders
        summary.setSuccessfulReminders(
                reminderAttemptRepository
                        .countByStatus(ReminderStatus.SUCCESS));

        // Failed Reminders
        summary.setFailedReminders(
                reminderAttemptRepository
                        .countByStatus(ReminderStatus.FAILED));

        return summary;
    }
}