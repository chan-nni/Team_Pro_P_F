package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String getScraps(@RequestParam(value = "page", defaultValue = "0") int page,
                            HttpSession session, Model model,
                            RedirectAttributes redirectAttributes,
                            @ModelAttribute("error") String error) {
        model.addAttribute("error", error);

        String mId = (String) session.getAttribute("m_id");
        Member member = memberService.findOne(mId);

        if (member != null) {
            try {
                int totalPages = scrapService.getTotalPages(member, 3);

                if (page < 0 || page >= totalPages) {
                    throw new IllegalArgumentException();
                }

                Pageable pageable = PageRequest.of(page, 9);
                Page<Scrap> scraps = scrapService.getScrapsByMember(member, pageable);

                model.addAttribute("scraps", scraps.getContent());
                model.addAttribute("currentPage", scraps.getNumber());
                model.addAttribute("totalPages", totalPages);
            } catch (IllegalArgumentException e) {
                // 잘못된 페이지 번호에 대한 예외 처리
                redirectAttributes.addFlashAttribute("message", "더 이상 페이지가 존재하지 않습니다.");
                return "redirect:/scrap?page=0";
            }
        }

        return "my/scrap";
    }


    // 스크랩 하기
    @GetMapping("/scrapJob")
    public String scrapJob(@RequestParam("id") Long jobId, HttpSession session, RedirectAttributes redirectAttributes) {
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

                redirectAttributes.addFlashAttribute("message", "스크랩 되었습니다.");

            } else{
                redirectAttributes.addFlashAttribute("message", "이미 스크랩 된 공고 입니다");
            }
        }
        return "redirect:/job_de?id=" + jobId;
    }

    // 스크랩 취소
    @PostMapping("/scrapCancel")
    public String scrapCancel(@RequestParam("seq") Long scrapSeq, RedirectAttributes redirectAttributes) {
        // 스크랩 취소 로직 구현
        try {
            Scrap scrap = scrapService.findBySeq(scrapSeq);

            if (scrap != null) {
                scrapService.delete(scrap);
                redirectAttributes.addFlashAttribute("message", "스크랩이 취소되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("message", "해당 스크랩을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            // 적절한 예외 처리 로직 추가
            redirectAttributes.addFlashAttribute("message", "스크랩 취소 중 오류가 발생했습니다.");
        }

        return "redirect:/scrap";
    }


}