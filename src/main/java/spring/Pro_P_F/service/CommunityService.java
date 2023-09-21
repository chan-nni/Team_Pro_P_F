package spring.Pro_P_F.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.Pro_P_F.domain.Community;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.repository.CommunityRepository;


import java.util.List;

@Service
public class CommunityService {

    private static final Logger logger = LoggerFactory.getLogger(CommunityService.class);


    private final CommunityRepository communityRepository;

    @Autowired
    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    // 모든 커뮤니티 게시물을 가져오는 메서드
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    // 특정 카테고리의 커뮤니티 게시물을 가져오는 메서드
    public List<Community> getCommunitiesByCategory(String category) {
        return communityRepository.findByCategory(category);
    }

    // 커뮤니티 게시물 저장 메서드
    public void saveCommunity(Community community) {
        communityRepository.save(community);
    }

    // 커뮤니티 게시물 삭제 메서드
    public void deleteCommunity(Long id) {
        communityRepository.deleteById(id);
    }
    public List<Community> findAllComm() {
        return communityRepository.findAll();
    }

    public List<Community> findByseq(Long id){
        return communityRepository.findByseq(id);
    }

    // 검색어로 커뮤니티 게시물 검색
    public List<Community> searchCommunitiesByKeyword(String keyword) {
        logger.debug("Searching communities by keyword: {}", keyword);

        // 검색어를 소문자로 변환하여 커뮤니티 게시물을 검색하는 로직을 구현
        keyword = keyword.toLowerCase();
        // 예를 들어, 커뮤니티 게시물의 제목 또는 내용에서 검색할 수 있습니다.
        // 검색 결과를 반환
        List<Community> result = communityRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);

        logger.debug("Found {} communities matching the keyword.", result.size());

        return result;
    }

}
