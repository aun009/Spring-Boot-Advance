package com.arun.JobApplication.company.impl;

import com.arun.JobApplication.company.Company;
import com.arun.JobApplication.company.CompanyRepository;
import com.arun.JobApplication.job.Job;
import com.arun.JobApplication.job.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl {

    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, JobRepository jobRepository) {
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company company) {
        Optional<Company> existingCompany = companyRepository.findById(id);
        if (existingCompany.isPresent()) {
            Company updatedCompany = existingCompany.get();
            updatedCompany.setName(company.getName());
            updatedCompany.setDescription(company.getDescription());
            updatedCompany.setJobs(company.getJobs());
            return companyRepository.save(updatedCompany);
        }
        throw new RuntimeException("Company not found");
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public List<Job> getJobsByCompanyId(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return jobRepository.findByCompany(company);
    }

}
