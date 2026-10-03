package SpringBoot.Billwise.service;

import java.util.List;

import SpringBoot.Billwise.entity.ScheduledJob;

public interface ScheduledJobService {

    ScheduledJob createJob(ScheduledJob job);

    List<ScheduledJob> getAllJobs();

    ScheduledJob getJobById(Long id);

    ScheduledJob updateJob(Long id, ScheduledJob job);

    void deleteJob(Long id);

    ScheduledJob enableJob(Long id);

    ScheduledJob disableJob(Long id);

    String runJob(Long id);
}