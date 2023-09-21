package spring.Pro_P_F.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.Pro_P_F.domain.Company;

public interface CompanyMemRepository extends JpaRepository<Company, Long> {
    public Company findByCyId(String cyId);
}
