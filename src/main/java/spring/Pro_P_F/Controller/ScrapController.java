package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.domain.Job;
import spring.Pro_P_F.domain.Member;
import spring.Pro_P_F.domain.Scrap;
import spring.Pro_P_F.service.JobService;
import spring.Pro_P_F.service.MemberService;
import spring.Pro_P_F.service.ScrapService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ScrapController {
    @Autowired
    private ScrapService scrapService;

    @Autowired
    private JobService jobService;

    @Autowired
    private MemberService memberService;

    // 스크랩 페이지 이동
    @GetMapping("/scrap")
    public String Scrap(HttpSession session, Model model) {
        String mId = (String) session.getAttribute("m_id");
        Member member = memberService.findOne(mId);

        if (member != null) {
            List<Scrap> scraps = scrapService.findByMember(member);
            model.addAttribute("scraps", scraps);
        }

        return "my/scrap";
    }

    // 스크랩 하기
    @GetMapping("/scrapJob")
    public String scrapJob(@RequestParam("id") Long jobId, HttpSession session, Model model) {
        // user Id
        String mId = (String) session.getAttribute("m_id");
        Member member = memberService.findOne(mId);

        // job Id
        List<Job> jobs = jobService.findBySeq(jobId);

        if (member != null && jobs.size() > 0) {
            Job job = jobs.get(0);

            // 스크랩 중복 확인
            boolean isAlreadyScrapped = scrapService.existsByMemberAndJob(member, job);

            if (!isAlreadyScrapped) {
                Scrap scrap = new Scrap();
                scrap.setMember(member);
                scrap.setJob(job);

                scrapService.save(scrap);

                return "redirect:/scrap";
            }
        }
        return "redirect:/noScrapJob?id=" + jobId;
    }

    @GetMapping("noScrapJob")
    public String noScrap(@RequestParam("id") Long jobId, Model model, HttpSession session){
        // user Id
        String mId = (String) session.getAttribute("m_id");
        Member member = memberService.findOne(mId);

        List<Job> jobs = jobService.findBySeq(jobId);
        model.addAttribute("jobs", jobs);
        Job job = jobs.get(0);

        boolean isAlreadyScrapped = scrapService.existsByMemberAndJob(member, job);

        if(!isAlreadyScrapped){
            model.addAttribute("message", "스크랩 되었습니다.");
        } else {
            model.addAttribute("message", "이미 스크랩 된 공고 입니다.");
        }

        return "company/employ_detail";
    }
}