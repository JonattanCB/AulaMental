package com.abs.aulamental.service.atencionapoderado;

import com.abs.aulamental.dto.Apoderado.ApoderadoDetailsSelectDto;
import com.abs.aulamental.dto.asignar.AsignarCreateDto;
import com.abs.aulamental.dto.atencionapoderado.*;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.mapper.AsignarMappper;
import com.abs.aulamental.mapper.AtencionApoderadoMapper;
import com.abs.aulamental.model.Apoderado;
import com.abs.aulamental.model.Usuario;
import com.abs.aulamental.model.enums.EstadoDocumento;
import com.abs.aulamental.model.enums.Tipodocumentacion;
import com.abs.aulamental.repository.ApoderadoRepository;
import com.abs.aulamental.repository.AsignarRepository;
import com.abs.aulamental.repository.AtencionApoderadosRepository;
import com.abs.aulamental.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtencionApoderadoService {
    private final AtencionApoderadosRepository atencionApoderadosRepository;
    private final ApoderadoRepository apoderadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsignarRepository asignarRepository;

    @Transactional
    public AtenApoderadoDetailDto createAtenApoderadoPsicologia(AtenApoderadoCreateDto dto) {
        var apoderado = apoderadoRepository.searchApoderadoById(dto.idApoderado());
        var psicologo =  usuarioRepository.searchUsuarioById(dto.idpsicologo());
        validacionComprobarcreate(apoderado,psicologo);

        var atencionApoderado = atencionApoderadosRepository.save(AtencionApoderadoMapper.tocreateAtenApoderado(dto,apoderado));

        asignarRepository.save(AsignarMappper.toCreateAsignar(new AsignarCreateDto(
                dto.idpsicologo(), dto.idpsicologo(), Tipodocumentacion.ATENCIONAPODERADO,atencionApoderado.getId(), EstadoDocumento.CERRADO,""
        ), psicologo, psicologo));

        return AtencionApoderadoMapper.toDetailAtenApoderado(atencionApoderado);
    }

    public Page<AtenApoderadoListDto> listAtenApoderado(String nombre, Pageable pageable) {
        return apoderadoRepository.findbyApoderadosOpcionalnombre(nombre, pageable).map(apoderado -> {
            long cant = getCantidadAtenciones(apoderado.getId());
            String fecha = getUltimaFechaAtencion(apoderado.getId());
            return AtencionApoderadoMapper.toListAtenApoderado(apoderado,cant,fecha);
        });
    }

    public Page<AtenApoderadoListDetailDto> listAtenAPoderadoDetails(int id, LocalDate fecha, Pageable pageable) {
        var apoderado = apoderadoRepository.searchApoderadoById(id);
        return atencionApoderadosRepository.listAtencionApoderadoOptionalDate(id, apoderado.getPersona().getId(), fecha, pageable).map(AtencionApoderadoMapper::toListAtenApoderadoDetail);
    }

    @Transactional
    public AtenApoderadoDto getAtencionApoderado(int id) {
        return AtencionApoderadoMapper.toDto(atencionApoderadosRepository.getAtencionApoderadosById(id));
    }

    public List<ApoderadoDetailsSelectDto> getApoderadoSelect(String nombre){
        return apoderadoRepository.listarApoderadoOptionNombre(nombre).stream().map(apoderado -> {
            long cantidad = getCantidadAtenciones(apoderado.getId());
            String fecha = getUltimaFechaAtencion(apoderado.getId());
            return AtencionApoderadoMapper.toListSelectApoderad(apoderado,cantidad,fecha);
        }).toList();
    }

    private void validacionComprobarcreate(Apoderado apoderado, Usuario usuario){
        if (apoderado==null){
            throw new ValidarExcepciones("Apoderado no valido");
        }

        if (usuario==null){
            throw new ValidarExcepciones("Apoderado no valido");
        }
    }

    public long getCantidadAtenciones(int idApoderado) {
        return atencionApoderadosRepository.contarAtencionesPorApoderadoCerradas(idApoderado);
    }

    public String getUltimaFechaAtencion(int idApoderado) {
        Date ultimaFecha = atencionApoderadosRepository.obtenerUltimaFechaPorApoderadoCerrada(idApoderado);
        return (ultimaFecha != null) ? ultimaFecha.toString() : "Sin registros";
    }




}
