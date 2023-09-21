package spring.Pro_P_F.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.Pro_P_F.domain.Community;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    // 커뮤니티 글 등록
    // JpaRepository를 확장하므로 save 메서드는 이미 제공됩니다.

    // 커뮤니티 전체 글 목록 조회
    List<Community> findAll();

    // ID로 커뮤니티 글 조회
    List<Community> findByseq(Long cSeq);

    List<Community> findByCategory(String category);

    // 게시물 검색
    List<Community> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

}
