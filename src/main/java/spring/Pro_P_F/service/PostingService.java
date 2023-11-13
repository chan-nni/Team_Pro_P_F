package spring.Pro_P_F.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.domain.Series;
import spring.Pro_P_F.repository.PostingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostingService {

    private final PostingRepository postingRepository;

    @Transactional
    public Long save(Posting posting) {
        postingRepository.save(posting);

        return posting.getSeq();
    }

    public void deletePostingBySeq(Long id) {
        postingRepository.deleteBySeq(id);
    }

    public List<Posting> findAll() {
        return postingRepository.findAll();
    }

    public List<Posting> findByid(Long id){
        return postingRepository.findBySeq(id);
    }

    public Posting findById(Long id) {
        return postingRepository.findById(id).orElse(null);
    }

    public List<Posting> findBym_id(String member){
        return postingRepository.findByMember_Mid(member);
    }

    // 시리즈 별 포스팅 목록
    public List<Posting> findBySeries(Series series){
        return postingRepository.findBySeries(series);
    }

    // 내용 가져오기
    public String getContentById(Long id) {
        List<Posting> posting = postingRepository.findBySeq(id);
        if (posting != null) {
            Posting dbposting = posting.get(0);
            return dbposting.getContent();
        }
        return null;
    }

    public List<Posting> findAllOrderedBySeqDesc() {
        return postingRepository.findAllByOrderByDateDesc();
    }

    public List<Posting> new_posting(){
        return postingRepository.findTop4ByOrderByDateDesc();
    }

    // 페이징 처리
    public Page<Posting> findAllPostingsPaged(Pageable pageable) {
        return postingRepository.findAll(pageable);
    }

    public List<Posting> findByKeyword(String keyword) {
        return postingRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    // 마이페이지 포스팅 페이징 처리
    public Page<Posting> findPostingsByMIdPaged(Member member, Pageable pageable) {
        return postingRepository.findByMember(member, pageable);
    }

}
