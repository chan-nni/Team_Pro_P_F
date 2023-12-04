package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.domain.Follow;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.domain.Series;
import spring.Pro_P_F.service.FollowService;
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

    @Autowired
    private FollowService followService;

    @GetMapping("/pofo")
    public String port(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 9; // 페이지당 아이템 수

        String mId = (String) session.getAttribute("m_id");
        Member member = memberService.findOne(mId);
        String mgit = member.getMgit();
        model.addAttribute("mgit", mgit);
        System.out.println("mgit = " + mgit);

        // 특정 페이지의 포스팅 리스트 가져오기
        Page<Posting> postingPage = postingService.findPostingsByMIdPaged(member, PageRequest.of(page, pageSize));
        model.addAttribute("postings", postingPage);

        // 시리즈 리스트
        List<Series> series = seriesService.findByMId(mId);
        model.addAttribute("series", series);

        model.addAttribute("member", member);

        // 팔로우 리스트 갯수 가져옴
        List<Follow> followList = followService.getFollowedCompanies(member);
        model.addAttribute("followCount", followList.size());

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


    // 팔로우 리스트
    @GetMapping("/followList")
    public String getFollowedCompanies(Model model, HttpSession session) {
        String mId = (String) session.getAttribute("m_id");
        Member member = memberService.findOne(mId);

        List<Follow> followedCompanies = followService.getFollowedCompanies(member);

        model.addAttribute("followCompanies", followedCompanies);

        return "my/followedCompanies";
    }

    // 개인정보 수정페이지로 이동
    @GetMapping("/m_edit")
    public String memberEdit(Model model, HttpSession session) {
        String mId = (String) session.getAttribute("m_id");
        List<Member> members = memberService.findByMember(mId);
        model.addAttribute("members", members);

        return "my/member_edit";
    }

    // 개인정보 수정
    @PostMapping("m_edit")
    public String updateMember(HttpSession session, Member member) {
        String mId = (String) session.getAttribute("m_id");
        Member editMember = memberService.findOne(mId);
        System.out.println("id = " + mId + "2번?");

        if (editMember != null) {
            editMember.setM_name(member.getM_name());
            editMember.setM_pwd(member.getM_pwd());
            editMember.setM_email(member.getM_email());
            editMember.setMphone(member.getMphone());
            editMember.setMgit(member.getMgit());

            System.out.println("지나갑니다");

            memberService.save(editMember);

            return "redirect:/pofo";
        } else {
            return "redirect:/m_edit";
        }
    }
}
