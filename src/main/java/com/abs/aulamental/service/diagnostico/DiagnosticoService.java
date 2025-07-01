package com.abs.aulamental.service.diagnostico;

import com.abs.aulamental.dto.diagnostico.*;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.mapper.DiagnosticoMappers;
import com.abs.aulamental.model.Diagnostico;
import com.abs.aulamental.model.enums.Estado;
import com.abs.aulamental.repository.DiagnosticoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosticoService {
    private final DiagnosticoRepository diagnosticoRepository;

    public Page<DiagnosticoListDto> listDiagnostico(String nombre, Pageable pageable) {
        return diagnosticoRepository.findOpcionalbyNombre(nombre, pageable)
                .map(DiagnosticoMappers::toListDiagnotico);
    }

    public List<DiagnosticoListSelectDto> listDiagnosticoSelect(String nombre) {
        return  diagnosticoRepository.ListfindOpcionalbyNombre(nombre).stream().filter(diagnostico -> diagnostico.getEstado()==Estado.ACTIVO)
                .map(DiagnosticoMappers::toListDiagnosticoSelec).toList();
    }

    @Transactional
    public DiagnosticoDetailDto createDiagnostico(DiagnosticoCreateDto dto) {
        validarComprobacion(dto.nombre());
        return DiagnosticoMappers.toDetailDiagnostico(diagnosticoRepository.save(DiagnosticoMappers.toCreateDiagnostico(dto)));
    }

    public DiagnosticoDto getDiagnostico(int id) {
        return  DiagnosticoMappers.toDto(getEntityDiagnostico(id));
    }

    @Transactional
    public DiagnosticoDetailDto updateDiagnostico(DiagnosticoUpdateDto dto) {
        validarComprobacionUpdate(dto.nombre(), dto.id());
        var diagnostico = getEntityDiagnostico(dto.id());
        diagnostico.ActualizarDatos(dto);
        return  DiagnosticoMappers.toDetailDiagnostico(diagnostico);
    }

    @Transactional
    public DiagnosticoDetailDto changeStatus(int id) {
        var diagnostico = getEntityDiagnostico(id);
        diagnostico.ActualizarEstado(diagnostico.getEstado() == Estado.ACTIVO ? Estado.INACTIVO : Estado.ACTIVO);
        return  DiagnosticoMappers.toDetailDiagnostico(diagnostico);
    }

    private void validarComprobacion(String nombre){
        if (diagnosticoRepository.existsByNombre(nombre)){
            throw  new ValidarExcepciones("El nombre del diagnostico ya existe");
        }
    }

    private void validarComprobacionUpdate(String nombre, int id ){
        if (diagnosticoRepository.existsByNombreAndIdNot(nombre , id)){
            throw new ValidarExcepciones("El nombre del diagnóstico ya existe");
        }
    }

    private Diagnostico getEntityDiagnostico(int id){
        return  diagnosticoRepository.findById(id)
                .orElseThrow(() -> new ValidarExcepciones("Diagnostico no encontrado"));
    }

}
