package com.arun.JobApplication.job;

import com.arun.JobApplication.job.impl.JobServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobServiceImpl jobService;

    public JobController(JobServiceImpl jobService) {
        this.jobService = jobService;
    }

    // Get all jobs
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.findAll();
        return ResponseEntity.ok(jobs);
    }

    // Get a job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if (job != null) {
            return ResponseEntity.ok(job);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Create a new job
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        jobService.createJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(job);
    }

    // Update a job
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {
        Job result = jobService.updateJob(id, updatedJob);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a job by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        boolean isDeleted = jobService.deleteJobById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Additional endpoint to fetch jobs by company
    @GetMapping("/company/{companyId}")
    public List<Job> getJobsByCompany(@PathVariable Long companyId) {
        return jobService.findJobsByCompany(companyId);
    }
}
