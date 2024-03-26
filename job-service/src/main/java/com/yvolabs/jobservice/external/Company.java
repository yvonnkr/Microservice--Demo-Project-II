package com.yvolabs.jobservice.external;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Company {
    private Long id;
    private String name;
    private String description;
}
