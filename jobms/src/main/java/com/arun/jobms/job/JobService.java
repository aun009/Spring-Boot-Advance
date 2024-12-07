package com.arun.jobms.job;

import com.arun.jobms.job.dto.JobDto;

import java.util.List;

public interface JobService {
    List<JobDto> findAll();
    void createJob(Job job);
    JobDto getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job updatedJob);
}
