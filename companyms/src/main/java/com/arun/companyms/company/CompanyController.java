package com.arun.companyms.company;



import com.arun.companyms.company.impl.CompanyServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        companyService.getAllCompanies();
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if(company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        companyService.createCompany(company);
        return new ResponseEntity<>("Company added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@RequestBody Company company, @PathVariable Long id) {
        companyService.updateCompany(company, id);
        return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        boolean isDEleted = companyService.deleteCompanyById(id);
        if(isDEleted) {
            companyService.deleteCompanyById(id);
            return new ResponseEntity<>("Company deleated successfully", HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<>("Company not deleated", HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/{id}/jobs")
//    public List<Job> getJobsByCompanyId(@PathVariable Long id) {
//        return companyService.getJobsByCompanyId(id);
//    }
}
