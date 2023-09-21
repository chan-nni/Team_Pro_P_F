package spring.Pro_P_F.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.Company;
import spring.Pro_P_F.domain.Job;
import spring.Pro_P_F.repository.CompanyMemRepository;
import spring.Pro_P_F.repository.JobRepository;

import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final CompanyMemRepository companyMemRepository; // 기업(Company) 리포지토리 추가

    @Autowired
    public JobService(JobRepository jobRepository, CompanyMemRepository companyMemRepository) {
        this.jobRepository = jobRepository;
        this.companyMemRepository = companyMemRepository;
    }


    public void saveJob(Job job) {
        jobRepository.save(job);
    }

    public List<Job> findAllComm() {
        return jobRepository.findAll();
    }

    public List<Job> findByCompany(Company company){
        return jobRepository.findByCompany(company);
    };

}
