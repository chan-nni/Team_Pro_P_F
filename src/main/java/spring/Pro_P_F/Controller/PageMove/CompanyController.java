package spring.Pro_P_F.Controller.PageMove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.Pro_P_F.domain.Company;
import spring.Pro_P_F.domain.Job;
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

    @GetMapping("/em_de")
    public String Employ_detail(Model model) {
        return "company/employ_detail";
    }

    @GetMapping("/c_pofo")
    public String company(Model model, HttpSession session) {
        String cyId = (String) session.getAttribute("cy_id");
        System.out.println("공고 불러오는 cy_id = " + cyId);

        Company company = companyMemService.findMemByCyId(cyId);

        model.addAttribute("company", company);


        List<Job> jobs = jobService.findByCompany(company);
        model.addAttribute("jobs", jobs);



        return "company/company_mypage";
    }

    @GetMapping("/company_ch")
    public String company_ch(Model model) {
        return "company/company_channel";
    }


}
