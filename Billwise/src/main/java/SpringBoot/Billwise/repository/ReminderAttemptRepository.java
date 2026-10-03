package SpringBoot.Billwise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringBoot.Billwise.entity.Invoice;
import SpringBoot.Billwise.entity.ReminderAttempt;
import SpringBoot.Billwise.entity.ReminderPeriod;
import SpringBoot.Billwise.entity.ReminderStatus;

public interface ReminderAttemptRepository
        extends JpaRepository<ReminderAttempt, Long> {

    Optional<ReminderAttempt> findByInvoiceAndReminderPeriod(
            Invoice invoice,
            ReminderPeriod reminderPeriod);

    long countByStatus(ReminderStatus status);
}