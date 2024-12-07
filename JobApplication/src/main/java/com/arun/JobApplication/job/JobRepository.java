package com.arun.JobApplication.job;

import com.arun.JobApplication.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompany(Company company);
}
