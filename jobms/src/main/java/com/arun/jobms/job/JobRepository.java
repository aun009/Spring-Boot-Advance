package com.arun.jobms.job;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
//    List<Job> findByCompany(Company company);
}
