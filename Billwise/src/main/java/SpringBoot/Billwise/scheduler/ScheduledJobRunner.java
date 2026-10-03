package SpringBoot.Billwise.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import SpringBoot.Billwise.service.ReminderService;

@Component
public class ScheduledJobRunner {

    @Autowired
    private ReminderService reminderService;

    @Scheduled(fixedRate = 60000)
    public void runReminderJob() {

        System.out.println("Automatic Reminder Job Started...");

        String result = reminderService.processReminders();

        System.out.println(result);

        System.out.println("Automatic Reminder Job Completed...");
    }
}