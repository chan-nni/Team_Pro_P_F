package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.domain.Community;
import spring.Pro_P_F.domain.Company;
import spring.Pro_P_F.domain.Job;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.service.CompanyMemService;
import spring.Pro_P_F.service.JobService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CompanyController {

    @Autowired
    private JobService jobService;

    @Autowired
    private CompanyMemService companyMemService;

    // 공고 상세페이지
    @GetMapping("/em_de")
    public String Employ_detail(Model model) {
        return "company/employ_detail";
    }

    // 기업 마이페이지
    @GetMapping("/c_pofo")
    public String company(Model model, HttpSession session) {
        String cyId = (String) session.getAttribute("cy_id");
        System.out.println("공고 불러오는 cy_id = " + cyId);

        Company company = companyMemService.findMemByCyId(cyId);

        model.addAttribute("companies", company);


        List<Job> jobs = jobService.findByCompany(company);
        model.addAttribute("jobs", jobs);



        return "company/company_mypage";
    }

    // 기업 소개 작성 후
    @PostMapping("/C_intro")
    public String Intro_My(Company com, HttpSession session) {
        String cyId = (String) session.getAttribute("cy_id");
        System.out.println("cy_id = " + cyId);

        Company company = companyMemService.findMemByCyId(cyId);
        company.setIntro(com.getIntro());

        companyMemService.save(company);
        System.out.println(com.getIntro());

        return "redirect:/c_pofo";
    }

    // 기업 채널 페이지
    @GetMapping("/company_ch")
    public String company_ch(Model model) {
        List<Company> companies = companyMemService.findAllComm();

        model.addAttribute("companies", companies);

        return "company/company_channel";
    }

}
