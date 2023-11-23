package spring.Pro_P_F.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.Pro_P_F.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByMid(String id); // 단일 엔터티를 반환하는 메서드

    List<Member> findAllByMid(String id); // 여러 엔터티를 반환하는 메서드
}

