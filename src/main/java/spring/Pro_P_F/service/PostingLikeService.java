package spring.Pro_P_F.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.domain.PostingLike;
import spring.Pro_P_F.repository.PostingLikeRepository;

@Service
public class PostingLikeService {

    private final PostingLikeRepository likeRepository;

    public PostingLikeService(PostingLikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    // 좋아요를 했는지 확인
    public boolean hasLiked(Member member, Posting posting) {
        return likeRepository.existsByMemberAndPosting(member, posting);
    }

    // 좋아요 취소
    public void unlikePost(Member member, Posting posting) {
        PostingLike postingLike = likeRepository.findByMemberAndPosting(member, posting);
        if (postingLike != null) {
            likeRepository.delete(postingLike);
        }
    }


    // 좋아요 저장
    public void saveLike(PostingLike postingLike) {
         likeRepository.save(postingLike);
    }
}
