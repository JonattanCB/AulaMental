package com.abs.aulamental.controller.user;

import com.abs.aulamental.dto.user.MensajeriaCitaDto;
import com.abs.aulamental.service.user.MensajeriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mensajeria")
@RequiredArgsConstructor
public class MensajeController {
    private  final MensajeriaService mensajeriaService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(@RequestBody MensajeriaCitaDto request) {
        return ResponseEntity.ok(mensajeriaService.enviarMensaje(request));
    }

}
