package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.Pro_P_F.Controller.Form.CommunityForm;
import spring.Pro_P_F.domain.*;
import spring.Pro_P_F.service.CompanyMemService;
import spring.Pro_P_F.service.JobService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public String employ_add2(Job job1, HttpSession session, Model model) {
        try {
            String cyId = (String) session.getAttribute("cy_id");
            System.out.println("db저장 cy_id = " + cyId);
            Company company = companyMemService.findMemByCyId(cyId);
            System.out.println("실제 저장 값 cy_id = " + company);

            Job job = new Job();

            job.setTitle(job1.getTitle());
            job.setStartdate(job1.getStartdate());
            job.setEnddate(job1.getEnddate());
            job.setPerson(job1.getPerson());
            job.setArea(job1.getArea());
            job.setContent(job1.getContent());
            job.setWork(job1.getWork());
            job.setEmploy(job1.getEmploy());
            job.setCompany(company);

            // 시작 날짜가 마감 날짜보다 늦으면 에러 메시지를 반환
            LocalDate startDate = job.getStartdate().atStartOfDay().toLocalDate();
            LocalDate endDate = job.getEnddate().atStartOfDay().toLocalDate();

            if (startDate.isAfter(endDate)) {
                model.addAttribute("error", "시작 날짜는 마감 날짜보다 늦을 수 없습니다.");
                return "company/employ_add";
            }

            jobService.saveJob(job);

            return "redirect:/employ";
        } catch (Exception e) {
            // 예외 처리: 예외를 적절히 처리하고 에러 메시지를 로깅하거나 사용자에게 알림을 보낼 수 있음
            e.printStackTrace(); // 예외 정보를 콘솔에 출력
            // 사용자에게 적절한 에러 페이지 또는 메시지를 반환하거나 리다이렉션할 수 있음
            return "errorPage"; // 예시로 "errorPage"로 반환
        }
    }

    @GetMapping("/employ")
    public String yourPage(@RequestParam(defaultValue = "0") int page, Model model) {
        jobService.updateJobStatus();

        Pageable pageable = PageRequest.of(page, 3); // 페이지당 9개 아이템

        Page<Job> jobs = jobService.findAllJobsPaged(pageable);
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
        System.out.println("지역: " + area);
        System.out.println("형태: " + employ);
        System.out.println("일: " + work);

        // 열거형(enum) 값을 가져와서 모델에 추가
        WorkType[] workCategories = WorkType.values();
        model.addAttribute("workCategories", workCategories);
        EmployType[] employCategories = EmployType.values();
        model.addAttribute("employCategories", employCategories);
        AreaType[] areaTypes = AreaType.values();
        model.addAttribute("areaTypes", areaTypes);

        List<Job> filteredJobs = new ArrayList<>();

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
            System.out.println("지나긴 해?");
        } else if (area != null) {
            filteredJobs = jobService.getJobsByArea(area);
        } else {
            filteredJobs = jobService.findAllComm();
            System.out.println("이걸 지나냐?");
        }

        if (keyword != null && !keyword.isEmpty()) {
            List<Job> keywordFilteredJobs = jobService.searchJobsByKeyword(keyword);
            filteredJobs.retainAll(keywordFilteredJobs);  // 병합한 후 중복된 항목만 남김
        }

        model.addAttribute("work", work);
        model.addAttribute("employ", employ);
        model.addAttribute("area", area);
        model.addAttribute("keyword", keyword);


        model.addAttribute("jobs", filteredJobs);
        return "company/employSearch";
    }


    // 공고 상세보기
    @GetMapping("job_de")
    public String job_Detail(@RequestParam("id") Long jobId, Model model) {
        List<Job> jobs = jobService.findBySeq(jobId);
        model.addAttribute("jobs", jobs);

        return "company/employ_detail";
    }

    // 공고 수정페이지로 이동
    @GetMapping("job_edit")
    public String job_Edit(@RequestParam("id") Long jobId, Model model) {
        List<Job> jobs = jobService.findBySeq(jobId);
        model.addAttribute("jobs", jobs);

        // 열거형(enum) 값을 가져와서 모델에 추가
        WorkType[] workCategories = WorkType.values();
        model.addAttribute("workCategories", workCategories);

        EmployType[] employCategories = EmployType.values();
        model.addAttribute("employCategories", employCategories);

        AreaType[] areaTypes = AreaType.values();
        model.addAttribute("areaTypes", areaTypes);

        return "company/employ_edit";
    }

    // 수정
    @PostMapping("job_edit")
    public String updateJob(@RequestParam("id") Long id, Job job) {

        List<Job> editJob = jobService.findBySeq(id);
        System.out.println("뭐냐 이건" + id);
        if (editJob != null && !editJob.isEmpty()) {
            Job existingJob = editJob.get(0);

            existingJob.setTitle(job.getTitle());
            existingJob.setStartdate(job.getStartdate());
            existingJob.setEnddate(job.getEnddate());
            existingJob.setPerson(job.getPerson());
            existingJob.setContent(job.getContent());
            existingJob.setWork(job.getWork());
            existingJob.setArea(job.getArea());

            jobService.saveJob(existingJob);

            // 성공적으로 수정한 경우 리디렉션
            return "redirect:/c_pofo";
        } else {
            // 해당 ID의 작업을 찾지 못한 경우에 대한 처리
            // 리디렉션 또는 오류 메시지 표시 등을 수행
            return "redirect:/"; // 예시로 홈페이지로 리디렉션
        }
    }

    // 삭제
    @GetMapping("job_delete")
    @Transactional
    public String deleteJob(@RequestParam("id") Long id) {
        jobService.deleteJobBySeq(id);

        return "redirect:/c_pofo";
    }

}

