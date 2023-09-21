package spring.Pro_P_F.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.Company;
import spring.Pro_P_F.repository.CompanyMemRepository;

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
}
