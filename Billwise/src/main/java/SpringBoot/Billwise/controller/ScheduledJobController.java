package SpringBoot.Billwise.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import SpringBoot.Billwise.entity.ScheduledJob;
import SpringBoot.Billwise.service.ScheduledJobService;

@RestController
@RequestMapping("/jobs")
public class ScheduledJobController {

    @Autowired
    private ScheduledJobService scheduledJobService;


    @PostMapping
    public ScheduledJob createJob(
            @RequestBody ScheduledJob job) {

        return scheduledJobService.createJob(job);
    }


    @GetMapping
    public List<ScheduledJob> getAllJobs() {

        return scheduledJobService.getAllJobs();
    }


    @GetMapping("/{id}")
    public ScheduledJob getJobById(
            @PathVariable Long id) {

        return scheduledJobService.getJobById(id);
    }


    @PutMapping("/{id}")
    public ScheduledJob updateJob(
            @PathVariable Long id,
            @RequestBody ScheduledJob job) {

        return scheduledJobService.updateJob(id, job);
    }


    @DeleteMapping("/{id}")
    public String deleteJob(
            @PathVariable Long id) {

        scheduledJobService.deleteJob(id);

        return "Scheduled job deleted successfully";
    }


    @PutMapping("/{id}/enable")
    public ScheduledJob enableJob(
            @PathVariable Long id) {

        return scheduledJobService.enableJob(id);
    }


    @PutMapping("/{id}/disable")
    public ScheduledJob disableJob(
            @PathVariable Long id) {

        return scheduledJobService.disableJob(id);
    }


    @PostMapping("/{id}/run")
    public String runJob(
            @PathVariable Long id) {

        return scheduledJobService.runJob(id);
    }
}