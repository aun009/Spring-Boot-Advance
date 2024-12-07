package com.arun.jobms.job;


import com.arun.jobms.job.dto.JobDto;
import com.arun.jobms.job.impl.JobServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobServiceImpl jobService) {
        this.jobService = jobService;
    }

    // Get all jobs
    @GetMapping
    public ResponseEntity<List<JobDto>> getAllJobs() {
        List<JobDto> jobs = jobService.findAll();
        return ResponseEntity.ok(jobs);
    }

    // Get a job by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getJobById(@PathVariable Long id) {
        JobDto jobDto = jobService.getJobById(id);
        if (jobDto != null) {
            return ResponseEntity.ok(jobDto);
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
    // Update a job
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {
        boolean isUpdated = jobService.updateJob(id, updatedJob);
        if (isUpdated) {
            return ResponseEntity.ok().build();
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
//    @GetMapping("/company/{companyId}")
//    public List<Job> getJobsByCompany(@PathVariable Long companyId) {
//        return jobService.findJobsByCompany(companyId);
//    }
}
