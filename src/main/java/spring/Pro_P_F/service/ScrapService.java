package spring.Pro_P_F.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.Job;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Scrap;
import spring.Pro_P_F.repository.ScrapRepository;

import java.util.List;

@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;

    public ScrapService(ScrapRepository scrapRepository) {
        this.scrapRepository = scrapRepository;
    }

    // 저장
    public void save(Scrap scrap) {
        scrapRepository.save(scrap);
    }

    // 멤버 별 스크랩 찾기
    public List<Scrap> findByMember(Member member) {
        return scrapRepository.findByMember(member);
    }

    // 멤버 별 스크랩, 페이징
    public Page<Scrap> getScrapsByMember(Member member, Pageable pageable) {
        return scrapRepository.findByMember(member, pageable);
    }

    // 스크랩 중복 확인징
    public boolean existsByMemberAndJob(Member member, Job job) {
        return scrapRepository.existsByMemberAndJob(member, job);
    }

    public int getTotalPages(Member member, int pageSize) {
        long totalItems = scrapRepository.countByMember(member);

        if (totalItems == 0) {
            return 1; // 스크랩이 없는 경우에도 최소 1페이지를 반환
        }

        return (int) Math.ceil((double) totalItems / pageSize);
    }

    public Scrap findBySeq(Long seq) {
        return scrapRepository.findById(seq).orElse(null);
    }

    public void delete(Scrap scrap) {
        scrapRepository.delete(scrap);
    }
}
