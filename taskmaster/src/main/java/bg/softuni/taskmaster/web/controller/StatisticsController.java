package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/statistics")
    public String statisticsView(Model model) {
        model.addAttribute("statisticsData", statisticsService.getStatistics());
        return "statistics";
    }
}
