package spring.Pro_P_F.service;

import org.springframework.stereotype.Service;
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

}
