package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.Pro_P_F.Controller.Form.PostForm;
import spring.Pro_P_F.domain.*;
import spring.Pro_P_F.service.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PostingController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PostingService postingService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private PostingLikeService postingLikeService;

    @Autowired
    private FollowService followService;

    // 포스팅 등록 페이지 로드
    @GetMapping("/upload")
    public String upload(Model model, HttpSession session) {
        model.addAttribute("postForm", new PostForm());
        String mId = (String) session.getAttribute("m_id");

        List<Series> categories = seriesService.findByMId(mId); // 카테고리 목록을 DB에서 가져옴
        model.addAttribute("categories", categories); // Thymeleaf로 카테고리 목록 전달


        return "my/upload";
    }

    // 포스팅 업로드
    @PostMapping("/upload")
    public String postForm(PostForm form, HttpSession session) {

        String mId = (String) session.getAttribute("m_id");
        System.out.println("mId = " + mId);

        Member member = memberService.findOne(mId);

        String seriesName = form.getSeries().getName(); // 선택한 시리즈의 이름
        Series series = seriesService.findByName(seriesName);


        Posting posting = new Posting();

        posting.setMember(member);
        posting.setTitle(form.getTitle());
        posting.setContent(form.getContent());
        posting.setDate(LocalDate.now());
        posting.setSeries(series);

        postingService.save(posting);
        return "redirect:/post";
    }

    // 포스팅 목록 페이지
//    @GetMapping("/post")
//    public String list(Model model) {
//        List<Posting> postings = postingService.findAllOrderedBySeqDesc();
//        model.addAttribute("postings", postings);
//        return "my/posting";
//    }

    // 최신 포스팅 목록
    @GetMapping("/post")
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 9, Sort.by("date").descending()); // "postingDate" 필드를 기준으로 내림차순 정렬

        Page<Posting> postings = postingService.findAllPostingsPaged(pageable);

        model.addAttribute("postings", postings);
        return "my/posting";
    }

    // 인기순 포스팅 목록
    @GetMapping("/postLike")
    public String Likelist(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 9, Sort.by("plike").descending()); // "postingDate" 필드를 기준으로 내림차순 정렬

        Page<Posting> postings = postingService.findAllPostingsPaged(pageable);

        model.addAttribute("postings", postings);
        return "my/posting";
    }

    // 한 포스팅 상세페이지 로드
    @GetMapping("/post_de")
    public String showPostDetails(@RequestParam("id") Long postId, Model model) {
        List<Posting> postings = postingService.findByid(postId);
        model.addAttribute("postings", postings);
        return "my/posting_detail";
    }

    // posting 좋아요 & 좋아요 취소
    @PostMapping("/like")
    public String toggleLike(@RequestParam Long postId, HttpSession session) {
        String mId = (String) session.getAttribute("m_id");

        // 해당 멤버와 포스트에 대한 정보 가져오기
        Member member = memberService.findOne(mId);
        Posting posting = postingService.findById(postId);

        if (member != null && posting != null) {
            // 이미 좋아요를 눌렀는지 확인
            boolean hasLiked = postingLikeService.hasLiked(member, posting);

            if (hasLiked) {
                // 이미 좋아요를 누른 경우 -> 좋아요 취소
                postingLikeService.unlikePost(member, posting);
                posting.setPlike(posting.getPlike() - 1);
            } else {
                // 좋아요를 누르지 않은 경우 -> 좋아요 추가
                PostingLike postingLike = new PostingLike();
                postingLike.setPosting(posting);
                postingLike.setMember(member);
                postingLikeService.saveLike(postingLike);
                posting.setPlike(posting.getPlike() + 1);
            }

            postingService.save(posting);
        }

        return "redirect:/post_de?id=" + postId;
    }

    // 클릭한 포스팅 아이디 마이페이지 로드
    @GetMapping("/profile")
    public String userProfile(@RequestParam("memberId") String memberId, Model model,
                              @RequestParam(defaultValue = "0") int page) {
        int pageSize = 9;

        model.addAttribute("memberId", memberId);

        Member member = memberService.findOne(memberId);
        String mgit = member.getMgit();
        model.addAttribute("mgit", mgit);

        model.addAttribute("member", member);

        // 특정 페이지의 포스팅 리스트 가져오기
        Page<Posting> postingPage = postingService.findPostingsByMIdPaged(member, PageRequest.of(page, pageSize));
        model.addAttribute("postings", postingPage);

        // 시리징
        List<Series> series = seriesService.findByMId(memberId);
        model.addAttribute("series", series);

        // 팔로우 리스트 갯수 가져옴
        List<Follow> followList = followService.getFollowedCompanies(member);
        model.addAttribute("followCount", followList.size());

        return "my/mypage_other"; // 사용자 프로필 페이지로 이동하는 뷰 이름을 반환합니다.
    }

    // 수정 페이지로 이동
    @GetMapping("posting_edit")
    public String posting_Edit(@RequestParam("id") Long id, Model model, HttpSession session) {
        List<Posting> postings = postingService.findByid(id);
        model.addAttribute("postings", postings);

        String mId = (String) session.getAttribute("m_id");
        List<Series> categories = seriesService.findByMId(mId); // 카테고리 목록을 DB에서 가져옴
        model.addAttribute("categories", categories); // Thymeleaf로 카테고리 목록 전달

        String dbContent = postingService.getContentById(id);
        model.addAttribute("dbContent", dbContent);

        return "my/Edit_upload";
    }

    // 수정
    @PostMapping("posting_edit")
    public String updatePosting(@RequestParam("id") Long id, Posting posting) {
        List<Posting> searchPosting = postingService.findByid(id);

        if (searchPosting != null && !searchPosting.isEmpty()) {
            Series series = seriesService.findByName(posting.getSeries().getName());


            Posting editPosting = searchPosting.get(0);

            editPosting.setTitle(posting.getTitle());
            editPosting.setContent(posting.getContent());
            editPosting.setSeries(series);

            System.out.println(posting.getTitle());

            postingService.save(editPosting);

            return "redirect:/post_de?id=" + editPosting.getSeq();
        } else {
            return "redirect:/";
        }
    }


    // 삭제
    @GetMapping("posting_delete")
    @Transactional
    public String deletePosting(@RequestParam("id") Long id) {
        postingService.deletePostingBySeq(id);

        return "redirect:/post";
    }

    @GetMapping("/posting_search")
    public String search(@RequestParam("keyword") String keyword, Model model, @RequestParam(defaultValue = "0") int page, HttpSession session) {
        int pageSize = 9;

        String mId = (String) session.getAttribute("m_id");
        Member member = memberService.findOne(mId);

        Page<Posting> postings = postingService.searchPostings(keyword, PageRequest.of(page, pageSize));
        model.addAttribute("postings", postings);

        // 시리즈 리스트
        List<Series> series = seriesService.findByMId(mId);
        model.addAttribute("series", series);

        String mgit = member.getMgit();
        model.addAttribute("mgit", mgit);

        model.addAttribute("member", member);

        // 팔로우 리스트 갯수 가져옴
        List<Follow> followList = followService.getFollowedCompanies(member);
        model.addAttribute("followCount", followList.size());
        return "my/my_mypage"; // 검색 결과를 표시할 Thymeleaf 템플릿
    }

    @GetMapping("/posting_search_other")
    public String searchOtherPostings(@RequestParam("memberId") String memberId,
                                      @RequestParam("keyword") String keyword,
                                      Model model,
                                      @RequestParam(defaultValue = "0") int page) {
        int pageSize = 9;

        model.addAttribute("memberId", memberId);

        Member member = memberService.findOne(memberId);
        String mgit = member.getMgit();
        model.addAttribute("mgit", mgit);

        model.addAttribute("member", member);

        // 특정 페이지의 포스팅 리스트 가져오기
        Page<Posting> postingPage = postingService.searchPostingsByKeywordAndMIdPaged(keyword, member, PageRequest.of(page, pageSize));
        model.addAttribute("postings", postingPage);

        // 시리즈
        List<Series> series = seriesService.findByMId(memberId);
        model.addAttribute("series", series);

        // 팔로우 리스트 갯수 가져옴
        List<Follow> followList = followService.getFollowedCompanies(member);
        model.addAttribute("followCount", followList.size());

        return "my/mypage_other";
    }


    @GetMapping("/posting_other_search")
    public String other_search(@RequestParam("keyword") String keyword, Model model,
                               @RequestParam(defaultValue = "0") int page) {

        int pageSize = 9;

        Page<Posting> postings = postingService.searchPostings(keyword, PageRequest.of(page, pageSize));

        model.addAttribute("postings", postings);
        return "my/mypage_other"; // 검색 결과를 표시할 Thymeleaf 템플릿
    }

    // 시리즈별 포스팅 목록
    @GetMapping("/series_posting")
    public String Series_Posting(@RequestParam("id") Long seriesId, Model model) {
        Series series = seriesService.findBySeq(seriesId);
        List<Posting> seriesPostings = postingService.findBySeries(series);
        model.addAttribute("seriesPostings", seriesPostings);
        return "my/series";
    }


    // 검색
    @GetMapping("p_search")
    public String searchPostings(@RequestParam String keyword, @RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 9;

        Page<Posting> postings = postingService.searchPostings(keyword, PageRequest.of(page, pageSize));

        model.addAttribute("postings", postings);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postings.getTotalPages());

        return "my/postingSearch";
    }

    // 시리즈 목록에서 포스팅 검색
    @GetMapping("/sp_search")
    public String searchSPostings(@RequestParam("keyword") String keyword, Model model) {
        List<Posting> seriesPostings = postingService.findByKeyword(keyword);
        model.addAttribute("seriesPostings", seriesPostings);
        model.addAttribute("keyword", keyword);
        return "my/series";
    }

}



