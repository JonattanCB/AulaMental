package com.abs.aulamental.service.suceso;

import com.abs.aulamental.dto.suceso.*;
import com.abs.aulamental.mapper.AlumnoMapper;
import com.abs.aulamental.mapper.PersonaMapper;
import com.abs.aulamental.mapper.SucesoMapper;
import com.abs.aulamental.model.Alumno;
import com.abs.aulamental.model.enums.NivelGravedad;
import com.abs.aulamental.model.itemSucesos;
import com.abs.aulamental.repository.*;
import com.abs.aulamental.service.apoderado.ApoderadoService;
import com.abs.aulamental.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SucesoService {
    private final SucesoRepository sucesoRepository;
    private final AlumnoRepository alumnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ItemSucesoRepository itemSucesoRepository;
    private final ApoderadoService apoderadoService;


    public SucesoDetailDto createSuceso(SucesoCreateDto dto){
        var usuario = usuarioRepository.searchUsuarioById(dto.idUsuario());
        var suceso = sucesoRepository.save(SucesoMapper.toCreateSuceso(dto, usuario));
        dto.alumnoIncidentes().forEach(sa -> {
            Alumno alumno = alumnoRepository.searchAlumnosById(sa.idAlumno());
            itemSucesoRepository.save(new itemSucesos(0, suceso, alumno, sa.motivo(), dto.nivelGravedad(), DateUtil.nowTimestamp(),DateUtil.nowTimestamp()));
        });
        return SucesoMapper.toDetailSuceso(suceso,dto.alumnoIncidentes(),dto.nivelGravedad());
    }

    public Page<ItemSucesoDto> listItemSuceso(int id, String nombre, Pageable pageable) {
        Page<itemSucesos> sucesos = itemSucesoRepository.findByIdUsuarioandOptionalFecha(id,nombre,pageable);
        return sucesos.map(SucesoMapper::toItemSucesoDto);
    }

    public Page<SucesoAlumnoListDto> listAlumnosSuceso(String nombre, Pageable pageable){
        return alumnoRepository.getAlumnosOptionNombre(nombre, pageable)
                .map(alumno -> {
                   String contact1 = apoderadoService.contact1ApoderadoAlumno(alumno.getId());
                   String contact2 = apoderadoService.contact2ApoderadoAlumno(alumno.getId());
                   return AlumnoMapper.tolistSucesoAlumno(alumno,contact1,contact2);
                });
    }

    public SucesoDto getSucesos(int id){
       return SucesoMapper.toSucesoDto(sucesoRepository.searchSucesosById(id));
    }

    public SucesosAlumnoDetailsDto toDetailsSucesoAlumno(int id){
        long cantBaja = itemSucesoRepository.countByAlumnoIdAndNivelGravedad(id, NivelGravedad.BAJA);
        long cantMedia = itemSucesoRepository.countByAlumnoIdAndNivelGravedad(id, NivelGravedad.MEDIA);
        long cantAlta = itemSucesoRepository.countByAlumnoIdAndNivelGravedad(id, NivelGravedad.ALTA);
        String alias = PersonaMapper.obtenerIniciales(alumnoRepository.searchAlumnosById(id).getPersona());
        return new SucesosAlumnoDetailsDto(alias,cantBaja, cantMedia, cantAlta);
    }

}
