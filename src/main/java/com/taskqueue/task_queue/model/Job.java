package com.taskqueue.task_queue.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private String status = "PENDING";

    private String priority = "MEDIUM";

    private int retryCount = 0;

    private int maxRetries = 3;

    @Column(columnDefinition = "TEXT")
    private String errorMsg;

    private LocalDateTime scheduledAt = LocalDateTime.now();

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}