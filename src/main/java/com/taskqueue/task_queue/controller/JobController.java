package com.taskqueue.task_queue.controller;

import com.taskqueue.task_queue.model.Job;
import com.taskqueue.task_queue.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // Submit a new job
    @PostMapping("/submit")
    public ResponseEntity<Job> submitJob(@RequestBody Map<String, String> request) {
        Job job = new Job();
        job.setType(request.get("type"));
        job.setPayload(request.get("payload"));
        job.setPriority(request.getOrDefault("priority", "MEDIUM"));
        job.setStatus("PENDING");
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        job.setScheduledAt(LocalDateTime.now());
        Job saved = jobRepository.save(job);
        return ResponseEntity.ok(saved);
    }

    // Get job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Long id) {
        return jobRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all jobs
    @GetMapping("/all")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobRepository.findAll());
    }

    // Get jobs by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Job>> getJobsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(jobRepository.findByStatus(status));
    }

    // Get queue stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(Map.of(
                "pending", jobRepository.countByStatus("PENDING"),
                "running", jobRepository.countByStatus("RUNNING"),
                "success", jobRepository.countByStatus("SUCCESS"),
                "failed", jobRepository.countByStatus("FAILED"),
                "dead", jobRepository.countByStatus("DEAD")
        ));
    }
}