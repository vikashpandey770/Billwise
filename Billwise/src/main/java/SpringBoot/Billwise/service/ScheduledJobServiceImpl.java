package SpringBoot.Billwise.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringBoot.Billwise.entity.ScheduledJob;
import SpringBoot.Billwise.repository.ScheduledJobRepository;

@Service
public class ScheduledJobServiceImpl
        implements ScheduledJobService {

    @Autowired
    private ScheduledJobRepository scheduledJobRepository;

    @Autowired
    private ReminderService reminderService;


    @Override
    public ScheduledJob createJob(ScheduledJob job) {

        if (job.getEnabled() == null) {
            job.setEnabled(true);
        }

        return scheduledJobRepository.save(job);
    }


    @Override
    public List<ScheduledJob> getAllJobs() {

        return scheduledJobRepository.findAll();
    }


    @Override
    public ScheduledJob getJobById(Long id) {

        return scheduledJobRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Scheduled Job not found with id: " + id
                    )
                );
    }


    @Override
    public ScheduledJob updateJob(
            Long id,
            ScheduledJob job) {

        ScheduledJob existing =
                getJobById(id);

        existing.setJobName(job.getJobName());
        existing.setEnabled(job.getEnabled());
        existing.setSchedule(job.getSchedule());

        return scheduledJobRepository.save(existing);
    }


    @Override
    public void deleteJob(Long id) {

        ScheduledJob job =
                getJobById(id);

        scheduledJobRepository.delete(job);
    }


    @Override
    public ScheduledJob enableJob(Long id) {

        ScheduledJob job =
                getJobById(id);

        job.setEnabled(true);

        return scheduledJobRepository.save(job);
    }


    @Override
    public ScheduledJob disableJob(Long id) {

        ScheduledJob job =
                getJobById(id);

        job.setEnabled(false);

        return scheduledJobRepository.save(job);
    }

    @Override
    public String runJob(Long id) {

        ScheduledJob job = getJobById(id);

        if (!Boolean.TRUE.equals(job.getEnabled())) {
            return "Job is disabled";
        }

        String result = reminderService.processReminders();

        job.setLastRun(LocalDateTime.now());
        scheduledJobRepository.save(job);

        return result;
    }
}