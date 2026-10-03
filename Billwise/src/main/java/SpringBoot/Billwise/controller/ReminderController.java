package SpringBoot.Billwise.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import SpringBoot.Billwise.service.ReminderService;

@RestController
@RequestMapping("/reminders")
public class ReminderController {
    @Autowired
    private ReminderService reminderService;

    @PostMapping("/run")
    public String runReminders() {
        return reminderService.processReminders();
    }
}