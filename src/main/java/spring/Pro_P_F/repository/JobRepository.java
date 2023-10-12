package spring.Pro_P_F.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.Pro_P_F.domain.*;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAll();

    List<Job> findByCompany(Company company);

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
