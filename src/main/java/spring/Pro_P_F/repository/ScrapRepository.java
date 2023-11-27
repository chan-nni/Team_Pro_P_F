package spring.Pro_P_F.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.Pro_P_F.domain.Job;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Scrap;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    // 멤버 별 스크랩 찾기
    List<Scrap> findByMember(Member member);

    // 멤버 결 스크랩 찾기, 페이징
    Page<Scrap> findByMember(Member member, Pageable pageable);

    // 스크랩 중복 확인
    boolean existsByMemberAndJob(Member member, Job job);

    long countByMember(Member member);
}
