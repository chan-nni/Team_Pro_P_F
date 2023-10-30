package spring.Pro_P_F.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.Pro_P_F.domain.Community;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
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

        return posting.getP_seq();
    }

    public void deletePostingByPSeq(Long id) {
        postingRepository.deleteByPSeq(id);
    }

    public List<Posting> findAll() {
        return postingRepository.findAll();
    }

    public List<Posting> findByid(Long id){
        return postingRepository.findByid(id);
    }

    public List<Posting> findBym_id(String member){
        return postingRepository.findBym_id(member);
    }

    // 내용 가져오기
    public String getContentById(Long id) {
        List<Posting> posting = postingRepository.findByid(id);
        if (posting != null) {
            Posting dbposting = posting.get(0);
            return dbposting.getP_content();
        }
        return null;
    }

    public List<Posting> findAllOrderedBySeqDesc() {
        return postingRepository.findAllOrderedBySeqDesc();
    }

    public List<Posting> new_posting(){
        return postingRepository.new_posting();
    }
}
