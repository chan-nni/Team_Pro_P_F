package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.Pro_P_F.Controller.Form.CompanyForm;
import spring.Pro_P_F.domain.Company;
import spring.Pro_P_F.domain.Posting;
import spring.Pro_P_F.service.CompanyMemService;
import spring.Pro_P_F.service.PostingService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CompanyMemController {

    @Autowired
    private CompanyMemService companyMemService;

    @Autowired
    private PostingService postingService;

    @GetMapping("/c_join")
    public String join(Model model) {
        model.addAttribute("companyForm", new CompanyForm());
        return "company/company_join";
    }

    @PostMapping("/c_join")
    public String memberForm(CompanyForm form) {
        Company company = new Company();

        company.setCyId(form.getId());
        company.setCy_pwd(form.getPwd());
        company.setCompany_name(form.getCompany_name());
        company.setManager_name(form.getName());
        company.setManager_phone(form.getPhone());
        company.setManager_email(form.getEmail());

        companyMemService.join(company);
        return "company/company_login";
    }

    @GetMapping("/c_login")
    public String company_login (Model model) {
        model.addAttribute("companyForm", new CompanyForm());
        return "company/company_login";
    }

    // login 성공/실패
    @PostMapping("/c_login")
    public String loginId(CompanyForm form, HttpSession session, Model model) {
        String cyId = form.getId();
        String cyPwd = form.getPwd();

        // 입력된 아이디로 회원을 조회합니다.
        Company company = companyMemService.findMemByCyId(cyId);
        System.out.println("이거입니다 +" + cyId);

        if (company == null) {
            model.addAttribute("errorMessage", "존재하지 않는 아이디입니다");
            return "company/company_login";
        } else {
            // 회원이 존재하면 비밀번호를 확인합니다.
            if (company.getCy_pwd().equals(cyPwd)) {
                // 아이디와 비밀번호가 일치하는 경우 세션에 아이디를 저장하고 로그인 성공 처리를 합니다.
                session.setAttribute("cy_id", cyId);
                return "redirect:/c_main";
            } else {
                // 비밀번호가 일치하지 않는 경우 에러 메시지를 처리할 수 있습니다.
                model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다");
                return "company/company_login";
            }
        }
    }


    // 메인 페이지 이동
    @GetMapping("/c_main")
    public String mainHome(HttpSession session, Model model) {
        String cyId = (String) session.getAttribute("cy_id");
        model.addAttribute("cy_id", cyId);
        System.out.println("이걸 지났다네~~" + cyId);

        List<Posting> new_posting = postingService.new_posting();
        model.addAttribute("new_posting", new_posting);

        return "home/company_index";
    }


}

