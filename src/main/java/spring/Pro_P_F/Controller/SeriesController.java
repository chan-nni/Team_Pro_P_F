package spring.Pro_P_F.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.Pro_P_F.domain.Series;
import spring.Pro_P_F.service.SeriesService;

import java.util.List;

@Controller
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/series")
    public String seriesList(Model model) {
        List<Series> seriesList = seriesService.getAllSeries();
        model.addAttribute("seriesList", seriesList);
        return "my/series_add";
    }

    @PostMapping("/addSeries")
    public String addSeries(@RequestParam("seriesName") String seriesName) {
        seriesService.addSeries(seriesName);
        return "redirect:/series";
    }

    @PostMapping("/updateSeries")
    public String updateSeries(@RequestParam("seriesId") Long seriesId, @RequestParam("seriesName") String seriesName) {
        seriesService.updateSeries(seriesId, seriesName);
        return "redirect:/series";
    }

    // 다른 메서드 (시리즈 삭제, 시리즈 조회 등)도 추가 가능
}

