package spring.Pro_P_F.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.domain.PostingLike;

public interface PostingLikeRepository extends JpaRepository<PostingLike, Long> {

    // 멤버와 포스팅을 기반으로 좋아요가 존재하는지 확인하는 메서드
    boolean existsByMemberAndPosting(Member member, Posting posting);

    // 멤버와 포스팅을 기반으로 특정 좋아요 엔티티를 가져오는 메서드
    PostingLike findByMemberAndPosting(Member member, Posting posting);


}
