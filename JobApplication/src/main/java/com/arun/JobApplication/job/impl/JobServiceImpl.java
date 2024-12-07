package com.arun.JobApplication.job.impl;

import com.arun.JobApplication.company.Company;
import com.arun.JobApplication.company.CompanyRepository;
import com.arun.JobApplication.job.Job;
import com.arun.JobApplication.job.JobRepository;
import com.arun.JobApplication.job.JobService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        // Handles both creating and updating a job
        jobRepository.save(job);
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    public Boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (ObjectNotFoundException e) {
            return false;
        }
    }

    public Job updateJob(Long id, Job updatedJob) {
        return jobRepository.findById(id).map(existingJob -> {
            existingJob.setTitle(updatedJob.getTitle());
            existingJob.setDescription(updatedJob.getDescription());
            existingJob.setMinSalary(updatedJob.getMinSalary());
            existingJob.setMaxSalary(updatedJob.getMaxSalary());
            existingJob.setLocation(updatedJob.getLocation());
            return jobRepository.save(existingJob);
        }).orElse(null);
    }

    public List<Job> findJobsByCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return jobRepository.findByCompany(company);
    }
}
