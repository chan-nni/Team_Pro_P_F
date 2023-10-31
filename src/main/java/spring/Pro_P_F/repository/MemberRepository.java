package spring.Pro_P_F.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.Pro_P_F.domain.Member;

import javax.persistence.EntityManager;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByMid(String id);
}
//    private final EntityManager em;
//
//    public void join(Member member) {
//        em.persist(member);
//    }
//
//    public Member findOne(String id) {
//        return em.find(Member.class, id);
//    }
//
//}
