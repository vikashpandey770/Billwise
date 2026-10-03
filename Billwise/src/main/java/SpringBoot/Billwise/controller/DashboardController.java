package SpringBoot.Billwise.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringBoot.Billwise.dto.DashboardSummary;
import SpringBoot.Billwise.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummary getDashboardSummary() {

        return dashboardService.getDashboardSummary();
    }
}