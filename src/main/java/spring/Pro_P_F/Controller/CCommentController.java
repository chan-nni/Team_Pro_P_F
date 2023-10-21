package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.domain.CComment;
import spring.Pro_P_F.domain.Community;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.service.CCommentService;
import spring.Pro_P_F.service.MemberService;

import javax.servlet.http.HttpSession;

@Controller
public class CCommentController {
    @Autowired
    private CCommentService commentService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/ccreate")
    public String createComment(@RequestParam Long communityId, @RequestParam String content, HttpSession session) {
        CComment comment = new CComment();
        Community community = new Community();
        String mId = (String) session.getAttribute("m_id");

        community.setSeq(communityId);
        comment.setCommunity(community);
        comment.setContent(content);

        Member member = memberService.findOne(mId);
        comment.setMember(member);

        commentService.createComment(comment);

        return "redirect:/com_de?id=" + comment.getCommunity().getSeq();
    }
}
