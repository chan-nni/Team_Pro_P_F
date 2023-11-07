package spring.Pro_P_F.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Scrap;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    // 멤버 별 스크랩 찾기
    List<Scrap> findByMember(Member member);
}
