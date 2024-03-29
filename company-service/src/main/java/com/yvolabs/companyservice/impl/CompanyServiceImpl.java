package com.yvolabs.companyservice.impl;

import com.yvolabs.companyservice.Company;
import com.yvolabs.companyservice.CompanyRepository;
import com.yvolabs.companyservice.CompanyService;
import com.yvolabs.companyservice.dto.ReviewMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company company, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company companyToUpdate = companyOptional.get();
            if (company.getDescription() != null) companyToUpdate.setDescription(company.getDescription());
            if (company.getName() != null) companyToUpdate.setName(company.getName());
            companyRepository.save(companyToUpdate);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Company createCompany(Company company) {
        companyRepository.save(company);
        return company;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElse(null);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);

    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        log.info("Review Message Consumed: {}", reviewMessage.toString());
        //logic todo

    }
}
