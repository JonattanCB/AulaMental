package com.abs.aulamental.controller.dashboard;

import com.abs.aulamental.dto.dashboard.*;
import com.abs.aulamental.service.user.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardControllers {

    private final DashboardService dashboardService;

    @GetMapping("/psicologo/{id}")
    public ResponseEntity<DashboardPsicologoDto> getDashboardPsicologo(@PathVariable int id) {
        return ResponseEntity.ok(dashboardService.getDasboardPsicologo(id));
    }

    @GetMapping("/practicante/{id}")
    public  ResponseEntity<DashboardPracticanteDto> getDashboardPracticate(@PathVariable int id) {
        return ResponseEntity.ok(dashboardService.getDasboardPracticante(id));
    }

    @GetMapping("/Directora/{id}")
    public ResponseEntity<DashboardDirectoraDto> getDashboardDirectora(@PathVariable int id) {
        return ResponseEntity.ok(dashboardService.getDasboardDirectora(id));
    }

    @GetMapping("/psicologobienestar/{id}")
    public ResponseEntity<DashboardPsicologoBienestarDto> getDasboaradPsicologoBienestar(@PathVariable int id) {
        return ResponseEntity.ok(dashboardService.getdasboardPsicologoBienestar(id));
    }

    @GetMapping("/bienestar/{id}")
    public ResponseEntity<DashboardBienestarDto> getbienestar(@PathVariable int id) {
        return ResponseEntity.ok(dashboardService.getDasboardBienestar(id));
    }

}
