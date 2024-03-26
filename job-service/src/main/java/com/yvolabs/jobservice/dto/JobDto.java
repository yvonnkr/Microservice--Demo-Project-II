package com.yvolabs.jobservice.dto;

import com.yvolabs.jobservice.external.Company;
import com.yvolabs.jobservice.external.Review;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JobDto {

    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Company company;
    private List<Review> reviews;
}
