package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String seriesList(Model model, HttpSession session) {
        String mId = (String) session.getAttribute("m_id");

        List<Series> seriesList = seriesService.findByMId(mId);
        model.addAttribute("seriesList", seriesList);
        return "my/series_add";
    }

    // 시리즈 추가
    @PostMapping("/addSeries")
    public String addSeries(@RequestParam("seriesName") String seriesName) {
        seriesService.addSeries(seriesName);
        return "redirect:/series";
    }

    // 시리즈 수정
    @PostMapping("/updateSeries")
    public String updateSeries(@RequestParam("seriesId") Long seriesId, @RequestParam("seriesName") String seriesName) {
        seriesService.updateSeries(seriesId, seriesName);
        return "redirect:/series";
    }
}

