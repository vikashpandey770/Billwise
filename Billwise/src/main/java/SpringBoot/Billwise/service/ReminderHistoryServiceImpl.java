package SpringBoot.Billwise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringBoot.Billwise.entity.ReminderAttempt;
import SpringBoot.Billwise.repository.ReminderAttemptRepository;

@Service
public class ReminderHistoryServiceImpl
        implements ReminderHistoryService {

    @Autowired
    private ReminderAttemptRepository reminderAttemptRepository;

    @Override
    public List<ReminderAttempt> getAllHistory() {
        return reminderAttemptRepository.findAll();
    }
    @Override
    public ReminderAttempt getHistoryById(Long id) {
        return reminderAttemptRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Reminder history not found with id: " + id
                    )
                );
    }
    @Override
    public List<ReminderAttempt> getHistoryByInvoice(Long invoiceId) {
        return reminderAttemptRepository
                .findAll()
                .stream()
                .filter(attempt ->
                    attempt.getInvoice() != null &&
                    attempt.getInvoice().getId().equals(invoiceId))
                .toList();
    }
}