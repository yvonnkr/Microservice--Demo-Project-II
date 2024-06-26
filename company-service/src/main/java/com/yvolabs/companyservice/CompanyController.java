package com.yvolabs.companyservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping

    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        Company saved = companyService.createCompany(company);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{resourceId}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body("Company added successfully");
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        log.debug("TESTING DEBUG LEVEL ACTUATOR LOGGING");
        return ResponseEntity.ok(companyService.getAllCompanies());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        return company != null ?
                ResponseEntity.ok(company) :
                ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        boolean updateCompany = companyService.updateCompany(company, id);
        return updateCompany ?
                ResponseEntity.ok("Company updated successfully") :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok("Company deleted");

    }


}
