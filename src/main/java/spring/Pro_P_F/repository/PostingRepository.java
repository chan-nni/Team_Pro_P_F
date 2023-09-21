package spring.Pro_P_F.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spring.Pro_P_F.domain.Community;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostingRepository {

    private final EntityManager em;

    public void save(Posting posting) {
        em.persist(posting);
    }

    public List<Posting> findAll() {
        return em.createQuery("select post from Posting post ORDER BY post.p_date desc", Posting.class)
                .getResultList();
    }

    public List<Posting> findByid(Long id) {
        return em.createQuery("select p from Posting p where p.id = :id", Posting.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Posting> findBym_id(String mId) {
        return em.createQuery("SELECT p FROM Posting p WHERE p.member.m_id = :mId", Posting.class)
                .setParameter("mId", mId)
                .getResultList();
    }

    //추천 포스팅
    public List<Posting> new_posting() {
        return em.createQuery("SELECT p FROM Posting p ORDER BY p.p_date desc", Posting.class)
                .setMaxResults(4)
                .getResultList();
    }


}
