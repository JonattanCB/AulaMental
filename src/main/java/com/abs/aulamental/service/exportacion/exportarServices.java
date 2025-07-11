package com.abs.aulamental.service.exportacion;

import com.abs.aulamental.dto.exportar.*;
import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.mapper.AlumnoMapper;
import com.abs.aulamental.mapper.AtencionAlumnoMapper;
import com.abs.aulamental.mapper.PersonaMapper;
import com.abs.aulamental.model.AtencionAlumno;
import com.abs.aulamental.model.itemSucesos;
import com.abs.aulamental.repository.*;
import com.abs.aulamental.service.apoderado.ApoderadoService;
import com.abs.aulamental.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class exportarServices {

    private final AlumnoRepository alumnoRepository;
    private final ItemSucesoRepository itemSucesoRepository;
    private final ApoderadoRepository apoderadoRepository;
    private final AtencionAlumnoRepository atencionAlumnoRepository;
    private  final AtencionApoderadosRepository atencionApoderadosRepository;
    private final ApoderadoService apoderadoService;

    public byte[] exportarSuceso(int idSuceso, int idAlumno) {
        try {
            var alumno = alumnoRepository.searchAlumnosById(idAlumno);

            var item = itemSucesoRepository.findBySucesoIdAndAlumnoId(idSuceso, idAlumno);
            List<SucesosExportar> listdto = new ArrayList<>();

            SucesosExportar dto = new SucesosExportar(String.valueOf(item.getSucesos().getId()), item.getSucesos().getFecha().toString() ,PersonaMapper.toConcatNombre(alumno.getPersona()),
                    String.valueOf(DateUtil.calculateAge(LocalDate.parse(alumno.getPersona().getFnacimiento()))), AlumnoMapper.toConcatNivelAlumno(alumno),
                    item.getMotivo(),item.getSucesos().getNombre(), item.getSucesos().getDetalles(),item.getSucesos().getArgurmentosalumno(),item.getSucesos().getAccionesrealizadas());

            listdto.add(dto);
            // Cargar el reporte Jasper desde el classpath
            InputStream jasperStream = new ClassPathResource("Reportes/Suceso/SUCESO.jasper").getInputStream();

            // Parámetros (sin logos en este caso, según el JRXML)
            Map<String, Object> params = new HashMap<>();

            // Data source
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listdto);

            // Cargar y llenar el reporte
            JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);
            JasperPrint print = JasperFillManager.fillReport(report, params, ds);

            // Generar el PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, baos);
            return baos.toByteArray();
        }catch (Exception e){
            throw new ValidarExcepciones("Error al generar pdf: "+e);
        }
    }


    public byte[] exportarSucesos(int idAlumno) {
       try {
           var alumno = alumnoRepository.searchAlumnosById(idAlumno);
           List<SucesosExportar> suceso = itemSucesoRepository.findByAlumnoId(idAlumno).stream().map(itemSucesos -> {
               return new SucesosExportar(String.valueOf(itemSucesos.getSucesos().getId()), itemSucesos.getSucesos().getFecha().toString() ,PersonaMapper.toConcatNombre(alumno.getPersona()),
                       String.valueOf(DateUtil.calculateAge(LocalDate.parse(alumno.getPersona().getFnacimiento()))), AlumnoMapper.toConcatNivelAlumno(alumno),
                       itemSucesos.getMotivo(),itemSucesos.getSucesos().getNombre(), itemSucesos.getSucesos().getDetalles(),itemSucesos.getSucesos().getArgurmentosalumno(),itemSucesos.getSucesos().getAccionesrealizadas());
           }).toList();
           // Cargar el reporte Jasper desde el classpath
           InputStream jasperStream = new ClassPathResource("Reportes/Suceso/SUCESO.jasper").getInputStream();

           // Parámetros (sin logos en este caso, según el JRXML)
           Map<String, Object> params = new HashMap<>();

           // Data source
           JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(suceso);

           // Cargar y llenar el reporte
           JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);
           JasperPrint print = JasperFillManager.fillReport(report, params, ds);

           // Generar el PDF
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           JasperExportManager.exportReportToPdfStream(print, baos);
           return baos.toByteArray();

       }catch (Exception e){
           throw new ValidarExcepciones("Error al generar pdf: "+e);
       }
    }

    public byte[] exportarAtencionAlumno(int idAtencion, int idalumno) {
        try {
            var alumno = alumnoRepository.searchAlumnosById(idalumno);
            List<ApoderadoAtencionAlumnoDto> listApoderado = apoderadoRepository.findByAlumnoId(idalumno).stream().map(
                    apoderado -> new ApoderadoAtencionAlumnoDto( apoderado.getParentesco().name(), PersonaMapper.toConcatNombre(apoderado.getPersona()),
                            apoderado.getOcupacion(),apoderado.getConvive().name())
            ).toList();

            var atencion = atencionAlumnoRepository.searchAtencionAlumnoById(idAtencion);
            AtencionAlumnoExportDto model = new AtencionAlumnoExportDto(
                    String.valueOf(idAtencion),atencion.getFecha().toString(),PersonaMapper.toConcatNombre(alumno.getPersona()),String.valueOf(alumno.getGrado()), alumno.getNivel().name(),
                    alumno.getPersona().getFnacimiento(), alumno.getPersona().getLnacimiento(), String.valueOf(DateUtil.calculateAge(LocalDate.parse(alumno.getPersona().getFnacimiento()))),
                    apoderadoService.contact1ApoderadoAlumno(idalumno), alumno.getPersona().getDireccion(), atencion.getMotivo(), atencion.getResumen(),atencion.getConclusion(), atencion.getRecomendacion(),
                    atencion.getTecnicas(),atencion.getDiagnostico().getNombre(), atencion.getComentario(), listApoderado
            );

            InputStream logoIzq = new ClassPathResource("img/Colegio.png").getInputStream();
            InputStream logoDer = new ClassPathResource("img/Psicologia.png").getInputStream();
            InputStream reporteStream = new ClassPathResource(
                    "Reportes/Atencion_Psicologia/FICHA_ATENCION_PSICOLOGICA.jasper"
            ).getInputStream();

            Map<String,Object> params = new HashMap<>();
            params.put("Logo_Izq", logoIzq);
            params.put("Logo_Der", logoDer);
            params.put("numero", model.numero());
            params.put("fecha", model.fecha());
            params.put("nombre", model.nombre());
            params.put("grado", model.grado());
            params.put("nivel", model.nivel());
            params.put("fechaNacimiento", model.fechaNacimiento());
            params.put("lugarNacimiento", model.lugarNacimiento());
            params.put("edad", model.edad());
            params.put("telefono", model.telefono());
            params.put("direccion", model.direccion());
            params.put("motivo_consulta", model.motivo_consulta());
            params.put("resumen_entrevista", model.resumen_entrevista());
            params.put("conclusiones", model.conclusiones());
            params.put("recomendaciones", model.recomendaciones());
            params.put("tecnicas", model.tecnicas());
            params.put("diagnostico_presuntivo", model.diagnostico_presuntivo());
            params.put("comentario_adicional", model.comentario_adicional());

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(model.apoderados());
            JasperReport report = (JasperReport) JRLoader.loadObject(reporteStream);
            JasperPrint print = JasperFillManager.fillReport(report, params, ds);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, baos);
            return baos.toByteArray();
        }catch (Exception e){
            throw new  ValidarExcepciones("Error al generar el pdf: "+e);
        }
    }

    public byte[] exportarAtencionAlumnoAsistencia(int mes, int anio) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            List<AtencionAlumnoAsistenciaDto> dto = atencionAlumnoRepository.findByMesAndAnio(mes,anio).stream().map(
                    atencionAlumno -> {
                        String nombre =  PersonaMapper.toConcatNombre(atencionAlumno.getAlumno().getPersona());
                        return new AtencionAlumnoAsistenciaDto( nombre , AlumnoMapper.toConcatNivelAlumno(atencionAlumno.getAlumno()),
                                atencionAlumno.getFecha().toString(), sdf.format(atencionAlumno.getFregistro()), "Aprobado");
                    }).toList();

            // logos
            InputStream logoIzq = new ClassPathResource("img/Colegio.png").getInputStream();
            InputStream logoDer = new ClassPathResource("img/Psicologia.png").getInputStream();
            // reporte
            InputStream jasperStream = new ClassPathResource(
                    "Reportes/Atencion_Estudiante/RG-ATENCION-ESTUDIANTE.jasper"
            ).getInputStream();

            Map<String,Object> params = new HashMap<>();
            params.put("Logo_Izq", logoIzq);
            params.put("Logo_Der", logoDer);
            params.put("Mes", DateUtil.obtenerNombreMes(mes));
            params.put("Ano", String.valueOf(anio));

            JRBeanCollectionDataSource ds =
                    new JRBeanCollectionDataSource(dto);
            JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);
            JasperPrint print = JasperFillManager.fillReport(report, params, ds);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, baos);
            return baos.toByteArray();
        }catch (Exception e){
            throw new  ValidarExcepciones("Error al generar el pdf: "+e);
        }
    }


    public byte[] exportarAtencionApoderadoAsistencia(int mes, int anio) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            List<AtencionApoderadoAsistenciaDto> dto = atencionApoderadosRepository.findByMesYAnio(mes,anio).stream().map(
                    atencionAlumno -> {
                        String nombre =  PersonaMapper.toConcatNombre(atencionAlumno.getApoderado().getPersona());
                        return new AtencionApoderadoAsistenciaDto( nombre , AlumnoMapper.toConcatNivelAlumno(atencionAlumno.getApoderado().getAlumno()),
                                atencionAlumno.getFecha().toString(), sdf.format(atencionAlumno.getFregistro()), "Aprobado");
                    }).toList();

            // logos
            InputStream logoIzq = new ClassPathResource("img/Colegio.png").getInputStream();
            InputStream logoDer = new ClassPathResource("img/Psicologia.png").getInputStream();
            // reporte jasper
            InputStream jasperStream = new ClassPathResource(
                    "Reportes/Asistencia_Padres/RG-ASISTENCIA-PADRES.jasper"
            ).getInputStream();

            Map<String,Object> params = new HashMap<>();
            params.put("Logo_Izq", logoIzq);
            params.put("Logo_Der", logoDer);
            params.put("Mes", DateUtil.obtenerNombreMes(mes));
            params.put("Ano", anio);

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(dto);
            JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);
            JasperPrint print = JasperFillManager.fillReport(report, params, ds);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, baos);
            return baos.toByteArray();

        }catch (Exception e){
            throw new ValidarExcepciones("error al generar el pdf: "+e);
        }
    }

    public byte[] exportarAtencionAlumnoSeguimineto(int id) {
        try{
            List<AtencionAlumnosSeguiminetoEsportar> dto = atencionAlumnoRepository.findAtencionesAlumnosCerradasByAlumnoId(id).stream()
                    .map(atencionAlumno -> {
                        return new AtencionAlumnosSeguiminetoEsportar(atencionAlumno.getFecha().toString(), String.valueOf(atencionAlumno.getId()), atencionAlumno.getMotivo(),
                                atencionAlumno.getResumen(),atencionAlumno.getConclusion(),atencionAlumno.getRecomendacion(),atencionAlumno.getTecnicas(), atencionAlumno.getDiagnostico().getNombre(),
                                atencionAlumno.getComentario());
                    }).toList();
            // Carga de logos
            InputStream logoIzq = new ClassPathResource("img/Colegio.png").getInputStream();
            InputStream logoDer = new ClassPathResource("img/Psicologia.png").getInputStream();
            //Reporte Jasper
            InputStream jasperStream = new ClassPathResource(
                    "Reportes/Seguimiento/Seguimiento_2.jasper"
            ).getInputStream();

            // Parámetros
            Map<String,Object> params = new HashMap<>();
            params.put("Logo_Izq", logoIzq);
            params.put("Logo_Der", logoDer);

            // Data source
            JRBeanCollectionDataSource ds =
                    new JRBeanCollectionDataSource(dto);

            JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);
            JasperPrint print = JasperFillManager.fillReport(report, params, ds);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, baos);
            return baos.toByteArray();

        } catch (Exception e) {
            throw new ValidarExcepciones("error al generar el pdf "+e);
        }
    }

    public byte[] exportarAtencionApoderado(int idatencion, int idapoderado) {
        try{
            var apoderado = apoderadoRepository.searchApoderadoById(idapoderado);
            var aap = atencionApoderadosRepository.searchAtencionApoderadosById(idatencion);

            List<AtencionApoderadoExportar> listdto = new ArrayList<>();

            AtencionApoderadoExportar dto = new AtencionApoderadoExportar(String.valueOf(idatencion),aap.getFecha().toString(),
                    PersonaMapper.toConcatNombre(apoderado.getPersona()),  String.valueOf(DateUtil.calculateAge(LocalDate.parse(apoderado.getAlumno().getPersona().getFnacimiento()))),
                    AlumnoMapper.toConcatNivelAlumno(apoderado.getAlumno()),PersonaMapper.toConcatNombre(apoderado.getAlumno().getPersona()),apoderado.getPersona().getDireccion(),
                    apoderado.getPersona().getTelefono1(), aap.getMotivo(),aap.getResumen(),aap.getConclusiones(), aap.getRecomendaciones(), aap.getIntervencion(), aap.getComentario());

            listdto.add(dto);

            InputStream logoIzq = new ClassPathResource("img/Colegio.png").getInputStream();
            InputStream logoDer = new ClassPathResource("img/Psicologia.png").getInputStream();
            InputStream reporteStream = new ClassPathResource(
                    "Reportes/Atencion_Padres/FC-ATENCION-PADRES.jasper"
            ).getInputStream();

            Map<String, Object> params = new HashMap<>();
            params.put("Logo_Izq", logoIzq);
            params.put("Logo_Der", logoDer);

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listdto);
            JasperReport report = (JasperReport) JRLoader.loadObject(reporteStream);
            JasperPrint print = JasperFillManager.fillReport(report, params, ds);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, baos);
            return baos.toByteArray();

            
        }catch (Exception e){
            throw new ValidarExcepciones("Error al generar el pdf "+e);
        }
    }
}
