package spring.Pro_P_F.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.Pro_P_F.domain.Company;
import spring.Pro_P_F.domain.Follow;
import spring.Pro_P_F.domain.Member;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 이미 팔로우한 기업인지 확인하기 위한 메서드
    boolean existsByMemberAndCompany(Member member, Company company);

    // 특정 회원의 팔로우 리스트
    List<Follow> findByMember(Member member);

}
