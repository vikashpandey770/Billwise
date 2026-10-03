package SpringBoot.Billwise.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringBoot.Billwise.entity.ScheduledJob;


public interface ScheduledJobRepository
        extends JpaRepository<ScheduledJob, Long> {

    Optional<ScheduledJob> findByJobName(String jobName);
}