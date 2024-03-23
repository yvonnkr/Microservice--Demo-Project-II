package com.yvolabs.companyservice;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    boolean updateCompany(Company company, Long id);

    Company createCompany(Company company);

    Company getCompany(Long id);

    void deleteCompany(Long id);
}
