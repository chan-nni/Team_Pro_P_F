package spring.Pro_P_F.service;

import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.CommunityLike;
import spring.Pro_P_F.repository.CommunityLikeRepository;

@Service
public class CommunityLikeService {
    private final CommunityLikeRepository likeRepository;

    public CommunityLikeService(CommunityLikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public void saveLike(CommunityLike communityLike) {
        likeRepository.save(communityLike);
    }
}
