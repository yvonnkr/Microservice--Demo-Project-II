package com.yvolabs.companyservice;

import com.yvolabs.companyservice.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "company")
    private List<Review> reviews;

    // todo
//    @JsonIgnore
//    @OneToMany(mappedBy = "company")
//    private List<Job> jobs;

}
