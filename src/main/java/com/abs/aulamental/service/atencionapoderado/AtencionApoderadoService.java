package com.abs.aulamental.service.atencionapoderado;

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

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class AtencionApoderadoService {
    private final AtencionApoderadosRepository atencionApoderadosRepository;
    private final ApoderadoRepository apoderadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsignarRepository asignarRepository;

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
        return apoderadoRepository.findbyApoderadosOpcionalnombre(nombre, pageable).map(AtencionApoderadoMapper::toListAtenApoderado);
    }

    public Page<AtenApoderadoListDetailDto> listAtenAPoderadoDetails(int id, Date fecha, Pageable pageable) {
        return atencionApoderadosRepository.listAtencionApoderadoOptionalDate(id, fecha, pageable).map(AtencionApoderadoMapper::toListAtenApoderadoDetail);
    }

    public AtenApoderadoDto getAtencionApoderado(int id) {
        return AtencionApoderadoMapper.toDto(atencionApoderadosRepository.getAtencionApoderadosById(id));
    }


    private void validacionComprobarcreate(Apoderado apoderado, Usuario usuario){
        if (apoderado==null){
            throw new ValidarExcepciones("Apoderado no valido");
        }

        if (usuario==null){
            throw new ValidarExcepciones("Apoderado no valido");
        }
    }




}
