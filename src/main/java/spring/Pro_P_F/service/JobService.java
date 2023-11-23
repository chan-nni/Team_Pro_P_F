package spring.Pro_P_F.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.*;
import spring.Pro_P_F.repository.CompanyMemRepository;
import spring.Pro_P_F.repository.JobRepository;
import spring.Pro_P_F.repository.JobSpecifications;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final CompanyMemRepository companyMemRepository; // 기업(Company) 리포지토리 추가

    private static final Logger logger = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private FollowService followService;

    // 팔로우 한 기업 공고 리스트 보기
    public List<Job> getJobForFollowCompanies(Member member){
        List<Company> followCompanies = followService.getFollowedCompanies(member)
                .stream()
                .map(Follow::getCompany)
                .collect(Collectors.toList());

        return jobRepository.findByCompanyIn(followCompanies);
    }

    public void updateJobStatus() {
        LocalDate currentDate = LocalDate.now();

        List<Job> openJobs = jobRepository.findByEnddateAfterAndStartdateBeforeAndStatusNot(currentDate, currentDate, JobStatus.CLOSE);
        openJobs.forEach(job -> job.setStatus(JobStatus.OPEN));

        List<Job> closeJobs = jobRepository.findByEnddateBeforeAndStatusNot(currentDate, JobStatus.CLOSE);
        closeJobs.forEach(job -> job.setStatus(JobStatus.CLOSE));

        List<Job> beforeJobs = jobRepository.findByStartdateAfterAndStatusNot(currentDate, JobStatus.BEFORE);
        beforeJobs.forEach(job -> job.setStatus(JobStatus.BEFORE));

        jobRepository.saveAll(openJobs);
        jobRepository.saveAll(closeJobs);
        jobRepository.saveAll(beforeJobs);
    }


    // 페이징 처리
    public Page<Job> findAllJobsPaged(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    // 4개만 출력
    public List<Job> getTop4() {
        return jobRepository.findTop4ByOrderByStartdateDesc();
    }

    public List<Job> findBySeq(Long id) {
        return jobRepository.findBySeq(id);
    }

    @Autowired
    public JobService(JobRepository jobRepository, CompanyMemRepository companyMemRepository) {
        this.jobRepository = jobRepository;
        this.companyMemRepository = companyMemRepository;
    }

    // 공고 삭제
    public void deleteJobBySeq(Long id) {
        jobRepository.deleteBySeq(id);
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

    // 검색어로 커뮤니티 게시물 검색
    public List<Job> searchJobsByKeyword(String keyword) {
        logger.debug("Searching communities by keyword: {}", keyword);

        // 검색어를 소문자로 변환하여 커뮤니티 게시물을 검색하는 로직을 구현
        keyword = keyword.toLowerCase();
        // 예를 들어, 커뮤니티 게시물의 제목 또는 내용에서 검색할 수 있습니다.
        // 검색 결과를 반환
        List<Job> result = jobRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);

        logger.debug("Found {} jobs matching the keyword.", result.size());

        return result;
    }

    public List<Job> getJobsByWork(WorkType work) {
        return jobRepository.findByWork(work);
    }

    public List<Job> getJobsByEmploy(EmployType employ) {
        return jobRepository.findByEmploy(employ);
    }

    public List<Job> getJobsByArea(AreaType area) {
        return jobRepository.findByArea(area);
    }

    public List<Job> getJobsByWorkAndEmployAndArea(WorkType work, EmployType employ, AreaType area) {
        return jobRepository.findByWorkAndEmployAndArea(work, employ, area);
    }

    public List<Job> getJobsByWorkAndEmploy(WorkType work, EmployType employ) {
        return jobRepository.findByWorkAndEmploy(work, employ);
    }

    public List<Job> getJobsByWorkAndArea(WorkType work, AreaType area) {
        return jobRepository.findByWorkAndArea(work, area);
    }

    public List<Job> getJobsByEmployAndArea(EmployType employ, AreaType area) {
        return jobRepository.findByEmployAndArea(employ, area);
    }

    public Page<Job> findPagedJobsByFilter(WorkType work, EmployType employ, AreaType area, String keyword, JobStatus jobStatus, int page, int size) {
        Specification<Job> spec = JobSpecifications.filterBy(work, employ, area, keyword, jobStatus);
        return jobRepository.findAll(spec, PageRequest.of(page, size));
    }

}
