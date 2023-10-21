package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.Controller.Form.CommunityForm;
import spring.Pro_P_F.domain.Community;
import spring.Pro_P_F.domain.CommunityLike;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.service.CommunityLikeService;
import spring.Pro_P_F.service.CommunityService;
import spring.Pro_P_F.service.MemberService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CommunityLikeService communityLikeService;

    // 커뮤니티 게시물 등록 페이지 로드
    @GetMapping("/com_add")
    public String com_add(Model model, HttpSession session) {
        String mId = (String) session.getAttribute("m_id");
        System.out.println("mId = " + mId);

        model.addAttribute("communityForm", new CommunityForm());
        return "my/com_add";
    }

    // 커뮤니티 게시물 등록
    @PostMapping("/com_add")
    public String communityForm(CommunityForm form, HttpSession session) {


        String mId = (String) session.getAttribute("m_id");

        Member member = memberService.findOne(mId);

        Community community = new Community();

        community.setMember(member);
        community.setTitle(form.getTitle());
        community.setContent(form.getContent());
        community.setCategory(form.getCategory());
        community.setC_date(LocalDate.now());

        communityService.saveCommunity(community);
        return "redirect:/com";
    }

    // 커뮤니티 게시물 목록
    @GetMapping("/com")
    public String list(Model model) {
        List<Community> communities = communityService.findAllComm();
        // 최신 등록 게시물 부터 보임
        Collections.reverse(communities);
        model.addAttribute("communities", communities);

        return "my/community";
    }

    // 커뮤니티 페이지 디테일
    @GetMapping("/com_de")
    public String showComDetails(@RequestParam("id") Long comId, Model model) {
        List<Community> communities = communityService.findByseq(comId);
        model.addAttribute("communities", communities);

        return "my/community_detail";
    }

    // 게시판 카테고리 선택
    @GetMapping("/community")
    public String getCommunityByCategory(@RequestParam(name = "category", required = false) String category, Model model) {
        // 카테고리가 지정되지 않았을 때 전체 목록을 가져옵니다.
        List<Community> communities;

        if (category == null || category.isEmpty()) {
            communities = communityService.getAllCommunities();
        } else {
            // 특정 카테고리의 게시물만 가져옵니다.
            communities = communityService.getCommunitiesByCategory(category);
        }

        model.addAttribute("communities", communities);
        return "my/community";
    }

    // 커뮤니티 게시물 검색(제목, 내용으로 검색)
    @GetMapping("/community_search")
    public String searchCommunity(@RequestParam(name = "keyword") String keyword, Model model) {
        System.out.println("검색어: " + keyword);
        List<Community> communities = communityService.searchCommunitiesByKeyword(keyword);
        model.addAttribute("communities", communities);
        model.addAttribute("keyword", keyword);
        return "my/community";
    }

    // 커뮤니티 좋아요
    @PostMapping("/clike")
    public String likeComm(@RequestParam Long id, HttpSession session) {
        String mId = (String) session.getAttribute("m_id");
        Member member = memberService.findOne(mId);

        List<Community> communities = communityService.findByseq(id);
        Community community = communities.get(0);

        if (community != null) {
            CommunityLike communityLike = new CommunityLike();
            communityLike.setCommunity(community);
            communityLike.setMember(member);
            communityLikeService.saveLike(communityLike);

            community.setC_like(community.getC_like() + 1);
            communityService.saveCommunity(community);
        }
        return "redirect:/com_de?id=" + id;
    }
}
