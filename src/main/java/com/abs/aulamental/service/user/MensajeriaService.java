package com.abs.aulamental.service.user;

import com.abs.aulamental.dto.cita.CitaCreateDto;
import com.abs.aulamental.dto.user.MensajeriaCitaDto;
import com.abs.aulamental.external.whatsapp.WhasapClient;
import com.abs.aulamental.external.whatsapp.dto.MensajeriaDto;
import com.abs.aulamental.service.alumno.AlumnoService;
import com.abs.aulamental.service.apoderado.ApoderadoService;
import com.abs.aulamental.service.cita.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MensajeriaService {
    private final AlumnoService alumnoService;
    private final ApoderadoService apoderadoService;
    private final WhasapClient whasapClient;
    private final CitaService citaService;

    @Transactional
    public String enviarMensaje(MensajeriaCitaDto dto){
        String nombreApoderado = alumnoService.getAlumnoById(dto.idAlumno()).apoderadoDtos().get(0).nombre();

        String phone = "51"+apoderadoService.contact1ApoderadoAlumno(dto.idAlumno());

        String mensaje = String.format("""
            *CITACIÓN*
            
            Sr(a). Apoderado de Familia: *%s*
            
            Le saludo cordialmente, mediante la presente el *Dpto. de Psicología* de la *I.E.P. “Alberto Benjamín Simpson”*, la cito a Usted para tratar asuntos relacionados a su menor hijo(a) el día *%s* a horas: *%s*, por favor traer la citación.
            
            Atentamente  
            *%s*  
            *PSICÓLOGO*
            """, nombreApoderado,dto.fecha(), dto.hora(), dto.psicologo());

        citaService.crearCita(new CitaCreateDto(dto.fecha(), dto.hora(), dto.idAlumno(), dto.idpsicologo(), dto.motivo()));

        var mensajeriaDto = new MensajeriaDto(phone, mensaje, null,false);
        boolean envio = whasapClient.mensajes(mensajeriaDto);
        if (envio) {
            return  "Mensaje enviado correctamente";
        }else {
            return "Mensaje no enviado";
        }
    }




}
