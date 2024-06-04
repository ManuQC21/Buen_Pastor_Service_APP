package BUEN_PASTOR.Service;

import BUEN_PASTOR.Entity.empleado;
import BUEN_PASTOR.Entity.equipo;
import BUEN_PASTOR.Entity.ubicacion;
import BUEN_PASTOR.Repository.EquipoRepository;
import BUEN_PASTOR.utils.GenericResponse;
import BUEN_PASTOR.utils.Global;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.util.IOUtils;
import java.io.FileInputStream;



@Service
@Transactional
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    public GenericResponse<equipo> addEquipo(equipo equipo) {
        try {
            equipo.setCodigoBarra(generateRandomBarcode(12));
            BUEN_PASTOR.Entity.equipo savedEquipo = equipoRepository.save(equipo);
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Equipo registrado exitosamente.", savedEquipo);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al registrar el equipo: " + e.getMessage(), null);
        }
    }

    private String generateRandomBarcode(int length) {
        String characters = "0123456789";
        Random rng = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rng.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public GenericResponse<equipo> updateEquipo(equipo equipo) {
        if (equipo.getId() == 0) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "ID de equipo no proporcionado.", null);
        }

        Optional<BUEN_PASTOR.Entity.equipo> existingEquipo = equipoRepository.findById(equipo.getId());
        if (!existingEquipo.isPresent()) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Equipo no encontrado.", null);
        }

        try {
            BUEN_PASTOR.Entity.equipo updatedEquipo = existingEquipo.get();

            // Actualizando solo los campos permitidos
            updatedEquipo.setTipoEquipo(equipo.getTipoEquipo());
            updatedEquipo.setDescripcion(equipo.getDescripcion());
            updatedEquipo.setEstado(equipo.getEstado());
            updatedEquipo.setFechaCompra(equipo.getFechaCompra()); // Asumiendo que ya es LocalDate
            updatedEquipo.setMarca(equipo.getMarca());
            updatedEquipo.setModelo(equipo.getModelo());
            updatedEquipo.setNombreEquipo(equipo.getNombreEquipo());
            updatedEquipo.setNumeroOrden(equipo.getNumeroOrden());
            updatedEquipo.setSerie(equipo.getSerie());

            // Asignar responsable y ubicación si se proporcionan
            if (equipo.getResponsable() != null && equipo.getResponsable().getId() > 0) {
                updatedEquipo.setResponsable(new empleado(equipo.getResponsable().getId()));
            }
            if (equipo.getUbicacion() != null && equipo.getUbicacion().getId() > 0) {
                updatedEquipo.setUbicacion(new ubicacion(equipo.getUbicacion().getId()));
            }

            equipoRepository.save(updatedEquipo);
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Equipo actualizado exitosamente.", updatedEquipo);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al actualizar el equipo: " + e.getMessage(), null);
        }
    }

    public GenericResponse<Void> deleteEquipo(Integer id) {
        if (!equipoRepository.existsById(id)) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Equipo no encontrado.", null);
        }
        try {
            equipoRepository.deleteById(id);
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Equipo eliminado exitosamente.", null);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al eliminar el equipo: " + e.getMessage(), null);
        }
    }

    public GenericResponse<List<equipo>> findAllEquipos() {
        try {
            List<equipo> equipos = (List<equipo>) equipoRepository.findAll();
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Listado completo de equipos.", equipos);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al obtener el listado de equipos: " + e.getMessage(), null);
        }
    }

    public GenericResponse<equipo> scanAndCopyBarcodeData(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_WARNING, "La imagen cargada es nula o el formato no es compatible.", null);
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);
            String barcodeText = result.getText();

            Optional<equipo> informacion = equipoRepository.findByCodigoBarra(barcodeText);
            if (informacion.isPresent()) {
                return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Escaneo de Código de Barras correcto", informacion.get());
            } else {
                return new GenericResponse<>(Global.TIPO_CUIDADO, Global.RPTA_WARNING, "Código de barras no encontrado", null);
            }
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al procesar el archivo: " + e.getMessage(), null);
        }
    }

    public GenericResponse<byte[]> generateBarcodeImageForPatrimonialCode(String codigoPatrimonial) {
        Optional<equipo> informacion = equipoRepository.findByCodigoPatrimonial(codigoPatrimonial);
        if (informacion.isPresent()) {
            try {
                equipo info = informacion.get();
                String barcodeData = info.getCodigoBarra();  // Use only the barcode number
                return new GenericResponse<>("SUCCESS", Global.RPTA_OK, "Código de barras generado exitosamente", generateBarcodeImage(barcodeData));
            } catch (Exception e) {
                return new GenericResponse<>("ERROR", Global.RPTA_ERROR, "Error al generar el código de barras: " + e.getMessage(), null);
            }
        } else {
            return new GenericResponse<>("ERROR", Global.RPTA_ERROR, "No se encontró información para el código patrimonial proporcionado", null);
        }
    }
    private byte[] generateBarcodeImage(String data) throws Exception {
        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(data, BarcodeFormat.CODE_128, 300, 100);

        // Convert BitMatrix to BufferedImage
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Prepare to add text below barcode and margin at the top
        BufferedImage combinedImage = new BufferedImage(barcodeImage.getWidth(), barcodeImage.getHeight() + 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combinedImage.createGraphics();

        // Fill background with white
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, combinedImage.getWidth(), combinedImage.getHeight());

        // Draw barcode image with top margin
        g.drawImage(barcodeImage, 0, 20, null);
        g.setColor(java.awt.Color.BLACK);
        g.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 12));
        
        // Draw text below barcode
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(data);
        int textX = (barcodeImage.getWidth() - textWidth) / 2; // Center text horizontally
        int textY = barcodeImage.getHeight() + 40; // Position text below the barcode
        g.drawString(data, textX, textY);

        g.dispose(); // Clean up graphics object

        // Write combined image to output byte array
        try (ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(combinedImage, "PNG", pngOutputStream);
            return pngOutputStream.toByteArray();
        }
    }
    // Método para generar el Excel
    public byte[] generateExcelReport() throws Exception {
        List<equipo> equipos = (List<equipo>) equipoRepository.findAll();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Equipos");

            // Insertar el logo
            FileInputStream inputStream = new FileInputStream("src/main/resources/logo.png");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();
            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(0);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize();

            // Crear un estilo para el título
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);

            // Agregar título en la fila 3
            Row titleRow = sheet.createRow(2); // Fila 3
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("REPORTE TOTAL DE EQUIPOS");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 15)); // Fusionar celdas para el título

            // Estilo para encabezados
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Crear el encabezado en la fila 6
            String[] columns = {
                    "ID", "Tipo Equipo", "Código Barra", "Código Patrimonial", "Descripción", "Estado",
                    "Fecha Compra", "Marca", "Modelo", "Nombre Equipo", "Número Orden", "Serie",
                    "Responsable - Nombre", "Responsable - Cargo", "Ubicación - Ambiente", "Ubicación - Ubicación Física"
            };
            Row headerRow = sheet.createRow(5); // Fila 6
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerStyle);
            }

            // Llenar datos a partir de la fila 7
            int rowIdx = 6;
            for (BUEN_PASTOR.Entity.equipo equipo : equipos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(equipo.getId());
                row.createCell(1).setCellValue(equipo.getTipoEquipo());
                row.createCell(2).setCellValue(equipo.getCodigoBarra());
                row.createCell(3).setCellValue(equipo.getCodigoPatrimonial());
                row.createCell(4).setCellValue(equipo.getDescripcion());
                row.createCell(5).setCellValue(equipo.getEstado());
                row.createCell(6).setCellValue(equipo.getFechaCompra().toString());
                row.createCell(7).setCellValue(equipo.getMarca());
                row.createCell(8).setCellValue(equipo.getModelo());
                row.createCell(9).setCellValue(equipo.getNombreEquipo());
                row.createCell(10).setCellValue(equipo.getNumeroOrden());
                row.createCell(11).setCellValue(equipo.getSerie());
                row.createCell(12).setCellValue(equipo.getResponsable() != null ? equipo.getResponsable().getNombre() : "-");
                row.createCell(13).setCellValue(equipo.getResponsable() != null ? equipo.getResponsable().getCargo() : "-");
                row.createCell(14).setCellValue(equipo.getUbicacion() != null ? equipo.getUbicacion().getAmbiente() : "-");
                row.createCell(15).setCellValue(equipo.getUbicacion() != null ? equipo.getUbicacion().getUbicacionFisica() : "-");
            }


            // Ajuste automático de tamaño de columnas
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new Exception("Error al generar el reporte: " + e.getMessage());
        }
    }


    public GenericResponse<equipo> getEquipoById(int id) {
        try {
            equipo equipo = equipoRepository.findByIdWithDetails(id);
            if (equipo != null) {
                return new GenericResponse<>(Global.TIPO_DATA, Global.RPTA_OK, Global.OPERACION_CORRECTA, equipo);
            } else {
                return new GenericResponse<>(Global.TIPO_RESULT, Global.RPTA_WARNING, "El equipo no existe", null);
            }
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, Global.OPERACION_ERRONEA, null);
        }
    }

    public GenericResponse<List<equipo>> filtroPorNombre(String nombreEquipo) {
        try {
            List<equipo> equipos = equipoRepository.findByNombreEquipoContaining(nombreEquipo);
            return new GenericResponse<>(Global.TIPO_DATA, Global.RPTA_OK, Global.OPERACION_CORRECTA, equipos);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, Global.OPERACION_ERRONEA, null);
        }
    }

    public GenericResponse<List<equipo>> filtroCodigoPatrimonial(String codigoPatrimonial) {
        try {
            List<equipo> equipos = equipoRepository.findByCodigoPatrimonialContaining(codigoPatrimonial);
            return new GenericResponse<>(Global.TIPO_DATA, Global.RPTA_OK, Global.OPERACION_CORRECTA, equipos);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, Global.OPERACION_ERRONEA, null);
        }
    }

    public GenericResponse<List<equipo>> filtroFechaCompraBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            List<equipo> equipos = equipoRepository.findByFechaCompraBetween(fechaInicio, fechaFin);
            return new GenericResponse<>(Global.TIPO_DATA, Global.RPTA_OK, Global.OPERACION_CORRECTA, equipos);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, Global.OPERACION_ERRONEA, null);
        }
    }
}
