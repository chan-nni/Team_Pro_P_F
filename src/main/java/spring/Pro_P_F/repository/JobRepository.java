package spring.Pro_P_F.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.Pro_P_F.domain.*;

import java.time.LocalDate;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAll();

    // 팔로우한 기업의 공고 보기
    List<Job> findByCompanyIn(List<Company> companies);

    List<Job> findByEnddateAfterAndStartdateBeforeAndStatusNot(LocalDate enddate, LocalDate startdate, JobStatus status);

    List<Job> findByEnddateBeforeAndStatusNot(LocalDate enddate, JobStatus status);

    List<Job> findByStartdateAfterAndStatusNot(LocalDate startdate, JobStatus status);

    void deleteBySeq(Long id);

    List<Job> findByCompany(Company company);

    List<Job> findBySeq(Long jSeq);

    List<Job> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

    List<Job> findTop4ByOrderByStartdateDesc();

    List<Job> findByWork(WorkType work);
    List<Job> findByEmploy(EmployType employ);
    List<Job> findByArea(AreaType area);

    // 모든 검색 조건을 고려하여 필터링
    List<Job> findByWorkAndEmployAndArea(WorkType work, EmployType employ, AreaType area);
    List<Job> findByWorkAndEmploy(WorkType work, EmployType employ);
    List<Job> findByWorkAndArea(WorkType work, AreaType area);
    List<Job> findByEmployAndArea(EmployType employ, AreaType area);

}
