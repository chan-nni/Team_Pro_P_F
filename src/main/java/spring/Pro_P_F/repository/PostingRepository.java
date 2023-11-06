package spring.Pro_P_F.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.domain.Series;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {

    List<Posting> findBySeqOrderBySeqDesc(Long Seq);

    void deleteBySeq(Long Seq);

    List<Posting> findAllByOrderByDateDesc();

    List<Posting> findBySeq(Long id);

    List<Posting> findByMember_Mid(String mId);

    List<Posting> findBySeries(Series series);

    List<Posting> findTop4ByOrderByDateDesc();

    List<Posting> findByTitleContainingOrContentContaining(String title, String content);

    // 마이페이지 페이징
    Page<Posting> findByMember(Member member, Pageable pageable);

}
