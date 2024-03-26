package com.yvolabs.jobservice.external;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {
    private Long id;
    private String title;
    private String description;
    private double rating;
}
