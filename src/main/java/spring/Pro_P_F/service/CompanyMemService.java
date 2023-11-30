package spring.Pro_P_F.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.Community;
import spring.Pro_P_F.domain.Company;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.repository.CompanyMemRepository;

import java.util.List;

@Service
public class CompanyMemService {
    @Autowired
    private CompanyMemRepository companyMemRepository;

    // 회원가입
    public void join(Company company){
        companyMemRepository.save(company);
    }

    // 로그인
    public Company findMemByCyId(String cyId) {
        return companyMemRepository.findByCyId(cyId);
    }

    public List<Company> findAllComm() {
        return companyMemRepository.findAll();
    }

    public void save(Company company) {
        companyMemRepository.save(company);
    }

    // 기업 채널 검색
    public List<Company> searchCompanies(String keyword) {
        return companyMemRepository.findByCompanynameContainingIgnoreCase(keyword);
    }

    // id 중복확인
    public boolean existsByCompany(String companyId) {
        return companyMemRepository.existsByCyId(companyId);
    }

    // 기업 정보
    public List<Company> findByCompany(String companyId) {
        return companyMemRepository.findAllByCyId(companyId);
    }
}
