package BUEN_PASTOR.Service;

import BUEN_PASTOR.Entity.Administrative;
import BUEN_PASTOR.Entity.DTO.TeacherPaymentDTO;
import BUEN_PASTOR.Entity.Teacher;
import BUEN_PASTOR.Entity.TeacherPayment;
import BUEN_PASTOR.Repository.PagoRepository;
import BUEN_PASTOR.utils.GenericResponse;
import BUEN_PASTOR.utils.Global;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagosService {

    @Autowired
    private PagoRepository pagoRepository;

    // Punto 1, 2, 3, 4: Agregar, editar, eliminar y listar todos los pagos
    public GenericResponse<TeacherPayment> agregarPago(TeacherPayment pago) {
        try {
            TeacherPayment savedPago = pagoRepository.save(pago);
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Pago agregado correctamente", savedPago);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al agregar el pago", null);
        }
    }

    public GenericResponse<TeacherPayment> editarPago(TeacherPayment pago) {
        if (!pagoRepository.existsById(pago.getId())) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Pago no encontrado", null);
        }
        try {
            TeacherPayment existingPago = pagoRepository.findById(pago.getId()).orElseThrow(() -> new Exception("Pago no encontrado"));

            existingPago.setAmount(pago.getAmount());
            existingPago.setPaymentDate(pago.getPaymentDate());
            existingPago.setPaymentStatus(pago.getPaymentStatus());
            existingPago.setPaymentReference(pago.getPaymentReference());
            existingPago.setWorkDays(pago.getWorkDays());
            existingPago.setEducationLevel(pago.getEducationLevel());
            existingPago.setModularCode(pago.getModularCode());
            existingPago.setTeacher(new Teacher(pago.getTeacher().getId())); // Set only the ID
            existingPago.setAdministrative(new Administrative(pago.getAdministrative().getId())); // Set only the ID

            TeacherPayment updatedPago = pagoRepository.save(existingPago);
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Pago actualizado con éxito", updatedPago);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al actualizar el pago", null);
        }
    }


    public GenericResponse<Void> eliminarPago(Integer id) {
        if (!pagoRepository.existsById(id)) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Pago no encontrado", null);
        }
        try {
            pagoRepository.deleteById(id);
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Pago eliminado correctamente", null);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al eliminar el pago", null);
        }
    }

    @Transactional(readOnly = true)
    public GenericResponse<List<TeacherPaymentDTO>> listarTodosLosPagos() {
        try {
            List<TeacherPayment> pagos = pagoRepository.findAll();
            List<TeacherPaymentDTO> pagosDTO = pagos.stream().map(pago -> {
                TeacherPaymentDTO dto = new TeacherPaymentDTO();
                dto.setId(pago.getId());
                dto.setTeacherId(pago.getTeacher().getId());
                dto.setTeacherName(pago.getTeacher().getFullName());
                dto.setAdministrativeId(pago.getAdministrative().getId());
                dto.setAmount(pago.getAmount());
                dto.setPaymentDate(pago.getPaymentDate());
                dto.setPaymentStatus(pago.getPaymentStatus());
                dto.setPaymentReference(pago.getPaymentReference());
                dto.setWorkDays(pago.getWorkDays());
                dto.setEducationLevel(pago.getEducationLevel());
                dto.setModularCode(pago.getModularCode());
                return dto;
            }).collect(Collectors.toList());
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Lista de todos los pagos", pagosDTO);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al obtener la lista de pagos", null);
        }
    }

    // Punto 7: Generar baucher de pago en PDF para un docente específico
    public byte[] generarBaucherPdf(TeacherPayment pago) throws Exception {
        // Lógica para generar el archivo PDF del baucher de pago
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        document.add(new Paragraph("Baucher de Pago"));
        document.add(new Paragraph("Docente: " + pago.getTeacher().getFullName()));
        document.add(new Paragraph("Monto: " + pago.getAmount().toString()));
        document.add(new Paragraph("Fecha de Pago: " + pago.getPaymentDate().toString()));
        document.close();
        return outputStream.toByteArray();
    }

    // Punto 8: Generar reporte de todos los pagos en formato Excel
    public byte[] generateExcelReport() throws Exception {
        List<TeacherPayment> pagos = pagoRepository.findAll();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Pagos");

            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Docente", "Monto", "Fecha de Pago", "Estado"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle(workbook));
            }

            int rowNum = 1;
            for (TeacherPayment pago : pagos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(pago.getId());
                row.createCell(1).setCellValue(pago.getTeacher().getFullName());
                row.createCell(2).setCellValue(pago.getAmount().doubleValue());
                row.createCell(3).setCellValue(pago.getPaymentDate().toString());
                row.createCell(4).setCellValue(pago.getPaymentStatus());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private CellStyle headerStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    public GenericResponse<TeacherPayment> obtenerPagoPorId(Integer id) {
        try {
            TeacherPayment pago = pagoRepository.findById(id).orElse(null);
            if (pago == null) {
                return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Pago no encontrado", null);
            }
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Pago encontrado", pago);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al obtener el pago", null);
        }
    }
}
