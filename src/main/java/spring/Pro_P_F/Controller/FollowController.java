package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.Pro_P_F.domain.Company;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.repository.CompanyMemRepository;
import spring.Pro_P_F.repository.MemberRepository;
import spring.Pro_P_F.service.FollowService;

import javax.servlet.http.HttpSession;

@Controller
public class FollowController {
    @Autowired
    private FollowService followService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CompanyMemRepository companyMemRepository;

    @GetMapping("/follow")
    public String followCompany(@RequestParam("id") Long companyId, HttpSession session, RedirectAttributes redirectAttributes) {
        // 현재 로그인한 사용자 정보 가져오기
        String mId = (String) session.getAttribute("m_id");
        Member member = memberRepository.findByMid(mId);

        // 팔로우할 기업 회원 정보 가져오기
        Company company = companyMemRepository.findById(companyId).orElse(null);

        // 팔로우 했는지 확인
        boolean followCheck = followService.isFollowing(member, company);

        // 팔로우 기능 수행
        if (member != null && company != null && !followCheck) {
            followService.follow(member, company);

            redirectAttributes.addFlashAttribute("message", "팔로우 했습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "이미 팔로우 한 기업입니다");
        }

        return "redirect:/company_ch"; // 팔로우 후 리다이렉트할 경로 설정
    }
}
