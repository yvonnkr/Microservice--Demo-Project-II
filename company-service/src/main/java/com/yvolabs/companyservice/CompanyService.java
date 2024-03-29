package com.yvolabs.companyservice;

import com.yvolabs.companyservice.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    boolean updateCompany(Company company, Long id);

    Company createCompany(Company company);

    Company getCompanyById(Long id);

    void deleteCompany(Long id);

    void updateCompanyRating(ReviewMessage reviewMessage);
}
