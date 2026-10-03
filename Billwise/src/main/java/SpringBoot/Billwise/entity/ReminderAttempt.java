package SpringBoot.Billwise.entity;


import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(
    name = "reminder_attempts",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_invoice_reminder_period",
            columnNames = {"invoice_id", "reminder_period"}
        )
    }
)
public class ReminderAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Enumerated(EnumType.STRING)
    @Column(name = "reminder_period", nullable = false)
    private ReminderPeriod reminderPeriod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReminderStatus status;

    private LocalDateTime attemptedAt;

    private String message;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public ReminderPeriod getReminderPeriod() {
        return reminderPeriod;
    }

    public void setReminderPeriod(ReminderPeriod reminderPeriod) {
        this.reminderPeriod = reminderPeriod;
    }

    public ReminderStatus getStatus() {
        return status;
    }

    public void setStatus(ReminderStatus status) {
        this.status = status;
    }

    public LocalDateTime getAttemptedAt() {
        return attemptedAt;
    }

    public void setAttemptedAt(LocalDateTime attemptedAt) {
        this.attemptedAt = attemptedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}