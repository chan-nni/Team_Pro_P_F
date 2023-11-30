package spring.Pro_P_F.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.Pro_P_F.domain.Community;
import spring.Pro_P_F.domain.Company;
import spring.Pro_P_F.domain.Member;

import java.util.List;

public interface CompanyMemRepository extends JpaRepository<Company, Long> {
    public Company findByCyId(String cyId);

    List<Company> findAll();

    // 기업 채널 검색
    List<Company> findByCompanynameContainingIgnoreCase(String keyword);

    boolean existsByCyId(String companyId);

    // 기업 정보
    List<Company> findAllByCyId(String id);
}
