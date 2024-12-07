package com.arun.jobms.job.impl;

import com.arun.jobms.job.Job;
import com.arun.jobms.job.JobRepository;
import com.arun.jobms.job.JobService;
import com.arun.jobms.job.client.CompanyClient;
import com.arun.jobms.job.client.ReviewClient;
import com.arun.jobms.job.dto.JobDto;
import com.arun.jobms.job.external.Company;
import com.arun.jobms.job.external.Review;
import com.arun.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.http.protocol.HTTP;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;
//    private final CompanyRepository companyRepository;

    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
//    @CircuitBreaker(name = "companyBreaker")
    @Retry(name = "companyBreaker" , fallbackMethod = "companyBreakerFallback")
    public List<JobDto> findAll() {

        List<Job> jobs = jobRepository.findAll();
        List<JobDto> jobDtos = new ArrayList<>();


        return jobs.stream().map(this::convertTODto).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e) {
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return  list;
    }

    private JobDto convertTODto(Job job) {

            Company company = companyClient.getCompany(job.getCompanyID());

            List<Review> reviews = reviewClient.getReview(job.getCompanyID());

//            List<Review> reviews = reviewResponce.getBody();

            JobDto jobDto = JobMapper.mapToJobWithCOmpanyDto(job, company, reviews);

            jobDto.setCompany(company);

        return jobDto;
    }

    @Override
    public void createJob(Job job) {
        // Handles both creating and updating a job
        jobRepository.save(job);
    }

    public JobDto getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertTODto(job);
    }

    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (ObjectNotFoundException e) {
            return false;
        }
    }

    public boolean updateJob(Long id, Job updatedJob) {
        return jobRepository.findById(id).map(existingJob -> {
            existingJob.setTitle(updatedJob.getTitle());
            existingJob.setDescription(updatedJob.getDescription());
            existingJob.setMinSalary(updatedJob.getMinSalary());
            existingJob.setMaxSalary(updatedJob.getMaxSalary());
            existingJob.setLocation(updatedJob.getLocation());
            jobRepository.save(existingJob);
            return true;
        }).orElse(false);
    }

//    public List<Job> findJobsByCompany(Long companyId) {
//        Company company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new RuntimeException("Company not found"));
//        return jobRepository.findByCompany(company);
//    }
}
