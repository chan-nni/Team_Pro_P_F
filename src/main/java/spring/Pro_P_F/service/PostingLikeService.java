package spring.Pro_P_F.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.PostingLike;
import spring.Pro_P_F.repository.PostingLikeRepository;

@Service
public class PostingLikeService {

    private final PostingLikeRepository likeRepository;

    public PostingLikeService(PostingLikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    // 좋아요 저장
    public void saveLike(PostingLike postingLike) {
         likeRepository.save(postingLike);
    }
}
