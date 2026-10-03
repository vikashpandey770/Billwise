package SpringBoot.Billwise.service;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringBoot.Billwise.entity.Invoice;
import SpringBoot.Billwise.entity.InvoiceStatus;
import SpringBoot.Billwise.entity.ReminderAttempt;
import SpringBoot.Billwise.entity.ReminderPeriod;
import SpringBoot.Billwise.entity.ReminderStatus;
import SpringBoot.Billwise.repository.InvoiceRepository;
import SpringBoot.Billwise.repository.ReminderAttemptRepository;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ReminderAttemptRepository reminderAttemptRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public String processReminders() {

        LocalDate today = LocalDate.now();

        List<Invoice> invoices = invoiceRepository.findAll();

        int success = 0;
        int skipped = 0;
        int failed = 0;

        for (Invoice invoice : invoices) {

            // PAID invoice ko skip karo
            if (invoice.getStatus() == InvoiceStatus.PAID) {
                skipped++;
                continue;
            }

            ReminderPeriod period = getReminderPeriod(invoice, today);

            // Agar reminder eligible nahi hai
            if (period == null) {
                skipped++;
                continue;
            }

            // Duplicate check
            ReminderAttempt existing =
                    reminderAttemptRepository
                            .findByInvoiceAndReminderPeriod(invoice, period)
                            .orElse(null);

            // Already successful hai to dobara mat bhejo
            if (existing != null &&
                    existing.getStatus() == ReminderStatus.SUCCESS) {

                skipped++;
                continue;
            }

            try {

                boolean sent = notificationService.sendReminder(
                        invoice,
                        period.name()
                );

                if (sent) {

                    if (existing == null) {

                        ReminderAttempt attempt = new ReminderAttempt();

                        attempt.setInvoice(invoice);
                        attempt.setReminderPeriod(period);
                        attempt.setStatus(ReminderStatus.SUCCESS);
                        attempt.setAttemptedAt(LocalDateTime.now());
                        attempt.setMessage("Reminder sent successfully");

                        reminderAttemptRepository.save(attempt);

                    } else {

                        // Previous attempt FAILED thi
                        existing.setStatus(ReminderStatus.SUCCESS);
                        existing.setAttemptedAt(LocalDateTime.now());
                        existing.setMessage("Reminder retry successful");

                        reminderAttemptRepository.save(existing);
                    }

                    success++;

                } else {

                    saveFailedAttempt(
                            invoice,
                            period,
                            existing,
                            "Reminder sending failed"
                    );

                    failed++;
                }

            } catch (Exception e) {

                saveFailedAttempt(
                        invoice,
                        period,
                        existing,
                        e.getMessage()
                );

                failed++;
            }
        }

        return "Reminder processing completed. Success: "
                + success
                + ", Failed: "
                + failed
                + ", Skipped: "
                + skipped;
    }

    private ReminderPeriod getReminderPeriod(
            Invoice invoice,
            LocalDate today) {

        LocalDate dueDate = invoice.getDueDate();

        // 1 day before due date
        if (dueDate.equals(today.plusDays(1))) {
            return ReminderPeriod.UPCOMING;
        }

        // Due today
        if (dueDate.equals(today)) {
            return ReminderPeriod.DUE_TODAY;
        }

        // Overdue
        if (dueDate.isBefore(today)) {
            return ReminderPeriod.OVERDUE;
        }

        return null;
    }

    private void saveFailedAttempt(
            Invoice invoice,
            ReminderPeriod period,
            ReminderAttempt existing,
            String message) {

        if (existing == null) {

            ReminderAttempt attempt = new ReminderAttempt();

            attempt.setInvoice(invoice);
            attempt.setReminderPeriod(period);
            attempt.setStatus(ReminderStatus.FAILED);
            attempt.setAttemptedAt(LocalDateTime.now());
            attempt.setMessage(message);

            reminderAttemptRepository.save(attempt);

        } else {

            existing.setStatus(ReminderStatus.FAILED);
            existing.setAttemptedAt(LocalDateTime.now());
            existing.setMessage(message);

            reminderAttemptRepository.save(existing);
        }
    }
}