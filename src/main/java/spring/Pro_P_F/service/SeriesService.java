package spring.Pro_P_F.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Series;
import spring.Pro_P_F.repository.SeriesRepository;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private MemberService memberService;


    @Autowired
    private HttpSession session;

    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    public void addSeries(String seriesName) {

        LocalDate currentDate = LocalDate.now();

        Series series = new Series();
        series.setName(seriesName);
        series.setS_date(currentDate);

        String mId = (String) session.getAttribute("m_id");

        Member member = memberService.findOne(mId);
        series.setMember(member);


        seriesRepository.save(series);
    }

    public void updateSeries(Long seriesId, String seriesName) {
        Optional<Series> optionalSeries = seriesRepository.findById(seriesId);
        if (optionalSeries.isPresent()) {
            Series series = optionalSeries.get();
            series.setName(seriesName);
            seriesRepository.save(series);
        }
    }

    // 시리즈 이름으로 시리즈 엔티티를 찾는 메서드
    public Series findByName(String s_name) {
        return seriesRepository.findByName(s_name);
    }
}
