package com.arun.jobms.job.dto;

import com.arun.jobms.job.external.Company;
import com.arun.jobms.job.external.Review;
import lombok.Data;

import java.util.List;

@Data
public class JobDto {
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;

    private Company company;
    private List<Review> review;
}
