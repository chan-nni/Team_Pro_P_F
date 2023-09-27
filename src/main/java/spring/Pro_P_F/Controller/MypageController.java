package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.domain.Series;
import spring.Pro_P_F.service.PostingService;
import spring.Pro_P_F.service.SeriesService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MypageController {
    @Autowired
    private PostingService postingService;

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/pofo")
    public String port(Model model, HttpSession session) {
        String mId = (String) session.getAttribute("m_id");
        List<Posting> postings = postingService.findBym_id(mId);
        model.addAttribute("postings", postings);

        List<Series> series = seriesService.findByMId(mId);
        model.addAttribute("series", series);

        return "my/my_mypage";
    }



}
