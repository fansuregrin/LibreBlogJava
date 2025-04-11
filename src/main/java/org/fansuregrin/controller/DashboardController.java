package org.fansuregrin.controller;

import org.fansuregrin.dto.StatsInfo;
import org.fansuregrin.dto.ApiResponse;
import org.fansuregrin.service.impl.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin/dashboard/stats")
    ApiResponse statsInfo() {
        StatsInfo info =  dashboardService.stats();
        return ApiResponse.success(info);
    }

}
