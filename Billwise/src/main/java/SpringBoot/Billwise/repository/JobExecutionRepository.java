package SpringBoot.Billwise.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringBoot.Billwise.entity.JobExecution;
import SpringBoot.Billwise.entity.ScheduledJob;


public interface JobExecutionRepository
        extends JpaRepository<JobExecution, Long> {

    List<JobExecution> findByScheduledJobOrderByStartedAtDesc(
            ScheduledJob scheduledJob
    );
}