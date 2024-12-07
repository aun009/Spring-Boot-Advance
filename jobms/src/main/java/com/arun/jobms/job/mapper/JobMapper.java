package com.arun.jobms.job.mapper;

import com.arun.jobms.job.Job;
import com.arun.jobms.job.dto.JobDto;
import com.arun.jobms.job.external.Company;
import com.arun.jobms.job.external.Review;

import java.util.List;

public class JobMapper{
    public static JobDto mapToJobWithCOmpanyDto(
            Job job,
            Company company,
            List<Review> reviews
    ) {
        JobDto jobDto = new JobDto();
        jobDto.setId(job.getId());
        jobDto.setTitle(job.getTitle());
        jobDto.setDescription(job.getDescription());
        jobDto.setLocation(job.getLocation());
        jobDto.setMaxSalary(job.getMaxSalary());
        jobDto.setMinSalary(job.getMinSalary());
        jobDto.setCompany(company);
        jobDto.setReview(reviews);
        return jobDto;
    }
}
