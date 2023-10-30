package spring.Pro_P_F.Controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.Controller.Form.PostForm;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.domain.PostingLike;
import spring.Pro_P_F.domain.Series;
import spring.Pro_P_F.service.MemberService;
import spring.Pro_P_F.service.PostingLikeService;
import spring.Pro_P_F.service.PostingService;
import spring.Pro_P_F.service.SeriesService;

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

    // 포스팅 등록 페이지 로드
    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("postForm", new PostForm());

        List<Series> categories = seriesService.getAllSeries(); // 카테고리 목록을 DB에서 가져옴
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
        posting.setP_title(form.getTitle());
        posting.setP_content(form.getContent());
        posting.setP_date(LocalDate.now());
        posting.setSeries(series);

        postingService.save(posting);
        return "redirect:/post";
    }

    // 포스팅 목록 페이지
    @GetMapping("/post")
    public String list(Model model) {
        List<Posting> postings = postingService.findAll();
        model.addAttribute("postings", postings);
        return "my/posting";
    }

    // 클릭한 포스팅 상세페이지 로드
    @GetMapping("/post_de")
    public String showPostDetails(@RequestParam("id") Long postId, Model model) {
        List<Posting> postings = postingService.findByid(postId);
        model.addAttribute("postings", postings);
        return "my/posting_detail";
    }

    // posting 좋아요
    @PostMapping("/like")
    public String likePost(@RequestParam Long postId, HttpSession session) {
        List<Posting> postings = postingService.findByid(postId);
        Posting posting = postings.get(0);

        String mId = (String) session.getAttribute("m_id");
        Member member = memberService.findOne(mId);

        if (posting != null && member != null) {
            PostingLike postingLike = new PostingLike();
            postingLike.setPosting(posting);
            postingLike.setMember(member);
            postingLikeService.saveLike(postingLike);

            posting.setP_like(posting.getP_like() + 1);
            postingService.save(posting);
        }
        return "redirect:/post_de?id=" + postId;

    }

    // 클릭한 포스팅 아이디 마이페이지 로드
    @GetMapping("/profile")
    public String userProfile(@RequestParam("memberId") String memberId, Model model) {
        // 여기에서 사용자의 프로필 정보를 가져와서 모델에 추가하는 로직을 구현합니다.
        // 예를 들어, 사용자 정보를 데이터베이스에서 조회하고 모델에 추가합니다.
        model.addAttribute("memberId", memberId);
        System.out.println("이걸 지났다네~~" + memberId);

        List<Posting> postings = postingService.findBym_id(memberId);
        model.addAttribute("postings", postings);
        return "my/mypage_other"; // 사용자 프로필 페이지로 이동하는 뷰 이름을 반환합니다.
    }

    // 클릭한 시리즈 포스팅 페이지 로드
    @GetMapping("/seriesProfile")
    public String seriesProfile(@RequestParam("memberId") String memberId, Model model) {
        return "/";
    }


    // 수정 페이지로 이동
    @GetMapping("posting_edit")
    public String posting_Edit(@RequestParam("id") Long id, Model model) {
        List<Posting> postings = postingService.findByid(id);
        model.addAttribute("postings", postings);

        List<Series> categories = seriesService.getAllSeries(); // 카테고리 목록을 DB에서 가져옴
        model.addAttribute("categories", categories); // Thymeleaf로 카테고리 목록 전달

        return "my/Edit_upload";
    }

    // 수정
    @PostMapping("posting_edit")
    public String updatePosting(@RequestParam("id") Long id, Posting posting) {
       List<Posting> searchPosting = postingService.findByid(id);

        if (searchPosting != null && !searchPosting.isEmpty()) {
            Series series = seriesService.findByName(posting.getSeries().getName());


            Posting editPosting = searchPosting.get(0);

            editPosting.setP_title(posting.getP_title());
            editPosting.setP_content(posting.getP_content());
            editPosting.setSeries(series);

            System.out.println(posting.getP_title());

            postingService.save(editPosting);

            return "redirect:/post_de?id=" + editPosting.getP_seq();
        } else {
            return "redirect:/";
        }
    }


    // 삭제
    @GetMapping("posting_delete")
    @Transactional
    public String deletePosting(@RequestParam("id") Long id) {
        postingService.deletePostingByPSeq(id);

        return "redirect:/post";
    }


}



