package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.Controller.Form.CommunityForm;
import spring.Pro_P_F.domain.*;
import spring.Pro_P_F.service.CompanyMemService;
import spring.Pro_P_F.service.JobService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private CompanyMemService companyMemService;

    // 공고 등록 페이지 로드
    @GetMapping("/em_add")
    public String employ_add(Model model, HttpSession session) {
        String mId = (String) session.getAttribute("m_id");
        System.out.println("mId = " + mId);


        model.addAttribute("job", new Job());
        return "company/employ_add";
    }

    // 공고 등록
    @PostMapping("/em_add")
    public String employ_add2(Job job1, HttpSession session) {
        try {
            String cyId = (String) session.getAttribute("cy_id");
            System.out.println("db저장 cy_id = " + cyId);
            Company company = companyMemService.findMemByCyId(cyId);
            System.out.println("실제 저장 값 cy_id = " + company);

            Job job = new Job();

            job.setTitle(job1.getTitle());
            job.setStartdate(job1.getStartdate());
            job.setEnd_date(job1.getEnd_date());
            job.setPerson(job1.getPerson());
            job.setArea(job1.getArea());
            job.setContent(job1.getContent());
            job.setWork(job1.getWork());
            job.setEmploy(job1.getEmploy());
            job.setCompany(company);

            jobService.saveJob(job);

            return "redirect:/employ";
        } catch (Exception e) {
            // 예외 처리: 예외를 적절히 처리하고 에러 메시지를 로깅하거나 사용자에게 알림을 보낼 수 있음
            e.printStackTrace(); // 예외 정보를 콘솔에 출력
            // 사용자에게 적절한 에러 페이지 또는 메시지를 반환하거나 리다이렉션할 수 있음
            return "errorPage"; // 예시로 "errorPage"로 반환
        }
    }

    // 공고 목록
//    @GetMapping("/employ")
//    public String list(Model model) {
//        List<Job> jobs = jobService.findAllComm();
//        model.addAttribute("jobs", jobs);
//        return "company/employ";
//    }

    @GetMapping("/employ")
    public String yourPage(Model model) {
        List<Job> jobs = jobService.findAllComm();
        model.addAttribute("jobs", jobs);

        // 열거형(enum) 값을 가져와서 모델에 추가
        WorkType[] workCategories = WorkType.values();
        model.addAttribute("workCategories", workCategories);

        EmployType[] employCategories = EmployType.values();
        model.addAttribute("employCategories", employCategories);

        AreaType[] areaTypes = AreaType.values();
        model.addAttribute("areaTypes", areaTypes);

        return "company/employ";
    }

    // 공고 검색기능
    @GetMapping("/category_search")
    public String search(
            @RequestParam(value = "work", required = false) WorkType work,
            @RequestParam(value = "employ", required = false) EmployType employ,
            @RequestParam(value = "area", required = false) AreaType area,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        System.out.println("검색어: " + keyword);

        // 열거형(enum) 값을 가져와서 모델에 추가
        WorkType[] workCategories = WorkType.values();
        model.addAttribute("workCategories", workCategories);
        EmployType[] employCategories = EmployType.values();
        model.addAttribute("employCategories", employCategories);
        AreaType[] areaTypes = AreaType.values();
        model.addAttribute("areaTypes", areaTypes);

        List<Job> filteredJobs;

        // "all" 값을 null로 변환하여 처리
        if ("".equals(work)) {
            work = null;
        }

        if ("".equals(employ)) {
            employ = null;
        }

        if ("".equals(area)) {
            area = null;
        }

        // 모든 검색 조건을 고려하여 필터링
        if (work != null && employ != null && area != null) {
            filteredJobs = jobService.getJobsByWorkAndEmployAndArea(work, employ, area);
        } else if (work != null && employ != null) {
            filteredJobs = jobService.getJobsByWorkAndEmploy(work, employ);
        } else if (work != null && area != null) {
            filteredJobs = jobService.getJobsByWorkAndArea(work, area);
        } else if (employ != null && area != null) {
            filteredJobs = jobService.getJobsByEmployAndArea(employ, area);
        } else if (work != null) {
            filteredJobs = jobService.getJobsByWork(work);
        } else if (employ != null) {
            filteredJobs = jobService.getJobsByEmploy(employ);
        } else if (area != null) {
            filteredJobs = jobService.getJobsByArea(area);
        } else {
            filteredJobs = jobService.findAllComm();
        }

        if(keyword != null){
            filteredJobs = jobService.searchJobsByKeyword(keyword);
        }

        model.addAttribute("work", work);
        model.addAttribute("employ", employ);
        model.addAttribute("area", area);
        model.addAttribute("keyword", keyword);


        model.addAttribute("jobs", filteredJobs);
        return "company/employ";
    }


}

