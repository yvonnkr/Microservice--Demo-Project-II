package com.yvolabs.jobservice.dto;

import com.yvolabs.jobservice.Job;
import com.yvolabs.jobservice.external.Company;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobWithCompanyDTO {

    private Job job;
    private Company company;
}
