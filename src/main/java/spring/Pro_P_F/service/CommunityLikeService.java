package spring.Pro_P_F.service;

import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.*;
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

    // 좋아요를 했는지 확인
    public boolean hasLiked(Member member, Community community) {
        return likeRepository.existsByMemberAndCommunity(member, community);
    }

    // 좋아요 취소
    public void unlikeCommunity(Member member, Community community) {
        CommunityLike communityLike = likeRepository.findByMemberAndCommunity(member, community);
        if (communityLike != null) {
            likeRepository.delete(communityLike);
        }
    }
}
