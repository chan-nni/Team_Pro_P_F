package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.Pro_P_F.domain.Series;
import spring.Pro_P_F.service.SeriesService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    // 시리즈 목록
    @GetMapping("/series")
    public String seriesList(Model model, HttpSession session, @ModelAttribute("message") String message) {
        String mId = (String) session.getAttribute("m_id");

        List<Series> seriesList = seriesService.findByMId(mId);
        model.addAttribute("seriesList", seriesList);

        model.addAttribute("message", message);

        return "my/series_add";
    }

    // 시리즈 추가 컨트롤러
    @PostMapping("/addSeries")
    public String addSeries(@RequestParam String seriesName, RedirectAttributes redirectAttributes) {
// 시리즈 이름이 중복되지 않으면 추가하고, 중복이면 오류 메시지 전송
        if (seriesService.isSeriesNameUnique(seriesName)) {
            seriesService.addSeries(seriesName);

            return "redirect:/series"; // 추가 성공 시 리다이렉션
        } else {
            redirectAttributes.addFlashAttribute("message", "이미 존재하는 시리즈 이름입니다.");
            return "redirect:/series";
        }
    }

    // 시리즈 수정 컨트롤러
    @PostMapping("/updateSeries")
    public String updateSeries(@RequestParam Long seriesId, @RequestParam String seriesName, RedirectAttributes redirectAttributes) {
// 시리즈 이름이 중복되지 않으면 수정하고, 중복이면 오류 메시지 전송
        if (seriesService.isSeriesNameUnique(seriesName)) {
            seriesService.updateSeries(seriesId, seriesName);

            return "redirect:/series"; // 수정 성공 시 리다이렉션
        } else {
            redirectAttributes.addFlashAttribute("message", "이미 존재하는 시리즈 이름입니다.");
            return "redirect:/series";
        }
    }


//    // 시리즈 추가
//    @PostMapping("/addSeries")
//    public String addSeries(@RequestParam("seriesName") String seriesName) {
//        seriesService.addSeries(seriesName);
//        return "redirect:/series";
//    }
//
//    // 시리즈 수정
//    @PostMapping("/updateSeries")
//    public String updateSeries(@RequestParam("seriesId") Long seriesId, @RequestParam("seriesName") String seriesName) {
//        seriesService.updateSeries(seriesId, seriesName);
//        return "redirect:/series";
//    }
}

