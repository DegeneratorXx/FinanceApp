package com.example.FinanceApp.controller;

import com.example.FinanceApp.dto.report.MonthlyReportResponse;
import com.example.FinanceApp.dto.report.YearlyReportResponse;
import com.example.FinanceApp.service.ReportService;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService=reportService;
    }

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<MonthlyReportResponse> monthyReport(
            @PathVariable int year,
            @PathVariable int month,
            HttpSession session
    ){
        return new ResponseEntity<>(reportService.montlyReport(year,month,session), HttpStatus.OK);
    }


    @GetMapping("/yearly/{year}")
    public ResponseEntity<YearlyReportResponse> yearlyReport(
            @PathVariable int year,
            HttpSession session
    ){
        return new ResponseEntity<>(reportService.yearlyReport(year, session),HttpStatus.OK);
    }
}
