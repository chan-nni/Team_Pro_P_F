package spring.Pro_P_F.Controller.PageMove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.Pro_P_F.Controller.Form.MemberForm;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.service.MemberService;

@Controller
public class MyController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/scrap")
    public String Scrap(Model model) {
        return "my/scrap";
    }


}
