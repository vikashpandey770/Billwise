package SpringBoot.Billwise.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import SpringBoot.Billwise.entity.ReminderAttempt;
import SpringBoot.Billwise.service.ReminderHistoryService;

@RestController
@RequestMapping("/reminders/history")
public class ReminderHistoryController {

    @Autowired
    private ReminderHistoryService reminderHistoryService;

    @GetMapping
    public List<ReminderAttempt> getAllHistory() {

        return reminderHistoryService.getAllHistory();
    }

    @GetMapping("/{id}")
    public ReminderAttempt getHistoryById(
            @PathVariable Long id) {

        return reminderHistoryService.getHistoryById(id);
    }

    @GetMapping("/invoice/{invoiceId}")
    public List<ReminderAttempt> getHistoryByInvoice(
            @PathVariable Long invoiceId) {

        return reminderHistoryService
                .getHistoryByInvoice(invoiceId);
    }
}