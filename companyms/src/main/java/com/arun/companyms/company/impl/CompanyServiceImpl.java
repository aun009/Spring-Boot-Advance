package com.arun.companyms.company.impl;



import com.arun.companyms.company.Company;
import com.arun.companyms.company.CompanyRepository;
import com.arun.companyms.company.CompanyService;
import com.arun.companyms.dto.ReviewMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
//    private final JobRepository jobRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {

    }

    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    public boolean updateCompany(Company company, Long id) {
        Optional<Company> existingCompany = companyRepository.findById(id);
        if (existingCompany.isPresent()) {
            Company updatedCompany = existingCompany.get();
            updatedCompany.setName(company.getName());
            updatedCompany.setDescription(company.getDescription());
            companyRepository.save(updatedCompany);
            return true;
        }
//        throw new RuntimeException("Company not found");
        return false;
    }

    public boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        else return false;
    }


}
