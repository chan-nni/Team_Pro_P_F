package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.PComment;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.service.MemberService;
import spring.Pro_P_F.service.PCommentService;

import javax.servlet.http.HttpSession;

@Controller
public class PCommentController {
    @Autowired
    private PCommentService commentService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/pcreate")
    public String createComment(@RequestParam Long postId, @RequestParam String content, HttpSession session) {
        PComment comment = new PComment();
        Posting posting = new Posting();
        String mId = (String) session.getAttribute("m_id");

        posting.setP_seq(postId);
        comment.setPosting(posting);
        comment.setContent(content);

        Member member = memberService.findOne(mId);
        comment.setMember(member);

        commentService.createComment(comment);

        return "redirect:/post_de?id=" + postId;
    }
}
