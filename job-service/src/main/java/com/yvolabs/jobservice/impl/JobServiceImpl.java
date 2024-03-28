package com.yvolabs.jobservice.impl;

import com.yvolabs.jobservice.Job;
import com.yvolabs.jobservice.JobRepository;
import com.yvolabs.jobservice.JobService;
import com.yvolabs.jobservice.clients.CompanyClient;
import com.yvolabs.jobservice.clients.ReviewClient;
import com.yvolabs.jobservice.dto.JobDto;
import com.yvolabs.jobservice.external.Company;
import com.yvolabs.jobservice.external.Review;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.yvolabs.jobservice.mapper.JobMapper.mapToJobWithCompanyDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;
    int attempt = 0;

    @Override
//    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDto> findAll() {
        log.info("Retry Attempts Count: {}", ++attempt);
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::convertToDto).collect(Collectors.toList());

    }

    public List<String> companyBreakerFallback(Exception e){
        ArrayList<String> list = new ArrayList<>();
        list.add("Dummy fall back message in event of circuit breaker is open.");
        return list;

    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDto getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        assert job != null;
        return convertToDto(job);

    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            if (updatedJob.getTitle() != null) job.setTitle(updatedJob.getTitle());
            if (updatedJob.getDescription() != null) job.setDescription(updatedJob.getDescription());
            if (updatedJob.getMinSalary() != null) job.setMinSalary(updatedJob.getMinSalary());
            if (updatedJob.getMaxSalary() != null) job.setMaxSalary(updatedJob.getMaxSalary());
            if (updatedJob.getLocation() != null) job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }

    private JobDto convertToDto(Job job) {
        //External API calls
        Company company = companyClient.getCompany(job.getCompanyId());

        List<Review> reviews = reviewClient.getReviews(company.getId());

        return mapToJobWithCompanyDto(job, company, reviews);

    }

}
