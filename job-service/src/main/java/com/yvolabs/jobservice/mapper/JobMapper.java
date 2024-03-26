package com.yvolabs.jobservice.mapper;

import com.yvolabs.jobservice.Job;
import com.yvolabs.jobservice.dto.JobDto;
import com.yvolabs.jobservice.external.Company;
import com.yvolabs.jobservice.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDto mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews) {

        return JobDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .location(job.getLocation())
                .company(company)
                .reviews(reviews)
                .build();

    }

}
