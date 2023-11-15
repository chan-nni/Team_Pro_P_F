package spring.Pro_P_F.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.Company;
import spring.Pro_P_F.domain.Follow;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.repository.FollowRepository;

import java.util.List;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    // 팔로우 했는지 확인
    public boolean isFollowing(Member member, Company company) {
        return followRepository.existsByMemberAndCompany(member, company);
    }

    // 팔로우 하기
    public void follow(Member member, Company company) {
        if (!isFollowing(member, company)) {
            Follow follow = new Follow();
            follow.setMember(member);
            follow.setCompany(company);
            followRepository.save(follow);
        }
    }

    // 특정 회원의 팔로우 리스트
    public List<Follow> getFollowedCompanies(Member member) {
        return followRepository.findByMember(member);
    }
}
