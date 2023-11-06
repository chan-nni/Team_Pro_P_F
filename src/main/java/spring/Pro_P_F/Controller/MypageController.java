package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.domain.Series;
import spring.Pro_P_F.service.MemberService;
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

    @Autowired
    private MemberService memberService;

    @GetMapping("/pofo")
    public String port(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 3; // 페이지당 아이템 수

        String mId = (String) session.getAttribute("m_id");
        Member member = memberService.findOne(mId);

        // 특정 페이지의 포스팅 리스트 가져오기
        Page<Posting> postingPage = postingService.findPostingsByMIdPaged(member, PageRequest.of(page, pageSize));

        model.addAttribute("postings", postingPage);

        List<Series> series = seriesService.findByMId(mId);
        model.addAttribute("series", series);

        model.addAttribute("member", member);

        return "my/my_mypage";
    }


    // 개인 소개 작성 후
    @PostMapping("/M_intro")
    public String Intro_My(Member mem, HttpSession session) {
        String mId = (String) session.getAttribute("m_id");
        System.out.println("mId = " + mId);

        Member member = memberService.findOne(mId);
        member.setIntro(mem.getIntro());

        memberService.save(member);
        System.out.println(mem.getIntro());

        return "redirect:/pofo";
    }




}
