package com.yvolabs.companyservice.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yvolabs.companyservice.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double rating;


    @JsonIgnore
    @ManyToOne
    private Company company;
}
