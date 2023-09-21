package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.service.PostingService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MypageController {
    @Autowired
    private PostingService postingService;

    @GetMapping("/pofo")
    public String port(Model model, HttpSession session) {
        String mId = (String) session.getAttribute("m_id");
        List<Posting> postings = postingService.findBym_id(mId);
        model.addAttribute("postings", postings);

        return "my/my_mypage";
    }


//    @GetMapping("/post_de")
//    public String showPostDetails(@RequestParam("id") Long postId, Model model) {
//        List<Posting> postings = postingService.findByid(postId);
//        model.addAttribute("postings", postings);
//        return "my/posting_detail";
//    }

}
