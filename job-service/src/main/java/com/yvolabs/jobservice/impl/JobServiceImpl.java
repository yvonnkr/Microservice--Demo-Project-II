package com.yvolabs.jobservice.impl;

import com.yvolabs.jobservice.Job;
import com.yvolabs.jobservice.JobRepository;
import com.yvolabs.jobservice.JobService;
import com.yvolabs.jobservice.dto.JobWithCompanyDTO;
import com.yvolabs.jobservice.external.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::convertToDto).collect(Collectors.toList());

    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
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

    private JobWithCompanyDTO convertToDto(Job job) {
        //External API call
        String url = "http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId();
        Company company = restTemplate.getForObject(url, Company.class);

        return JobWithCompanyDTO.builder()
                .job(job)
                .company(company)
                .build();
    }

}
