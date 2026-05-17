package com.taskqueue.task_queue.repository;

import com.taskqueue.task_queue.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Worker uses this to find next job to process
    @Query("SELECT j FROM Job j WHERE j.status = 'PENDING' " +
            "AND j.scheduledAt <= :now " +
            "ORDER BY CASE j.priority " +
            "WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 ELSE 3 END, " +
            "j.createdAt ASC")
    List<Job> findPendingJobs(@Param("now") LocalDateTime now);

    List<Job> findByStatus(String status);
    long countByStatus(String status);
}