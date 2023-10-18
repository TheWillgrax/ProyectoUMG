package me.parzibyte.sistemaventasspringboot;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PDFReporteServicio {
    public void generateProductReport(List<Producto> productos, String filePath) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Definir el contenido del informe
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Informe de Productos");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 10);
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            int rows = productos.size();
            int cols = 4;
            float rowHeight = 20f;
            float tableHeight = rowHeight * (float) rows;
            float tableYPosition = yStart - tableHeight;

            // Dibujar la tabla
            float tableXPosition = margin;
            float tableWidthFraction = tableWidth / (float) cols;
            float cellMargin = 5f;

            // Dibujar encabezados
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            float nextX = tableXPosition;
            for (int j = 0; j < cols; j++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(nextX, yPosition);
                contentStream.showText(getTableHeader(j));
                contentStream.endText();
                nextX += tableWidthFraction;
            }

            // Dibujar filas
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            yPosition -= rowHeight;
            for (int i = 0; i < rows; i++) {
                nextX = tableXPosition;
                Producto producto = productos.get(i);

                contentStream.beginText();
                contentStream.newLineAtOffset(nextX, yPosition);
                contentStream.showText(producto.getNombre());
                contentStream.endText();
                nextX += tableWidthFraction;

                contentStream.beginText();
                contentStream.newLineAtOffset(nextX, yPosition);
                contentStream.showText(producto.getCodigo());
                contentStream.endText();
                nextX += tableWidthFraction;

                contentStream.beginText();
                contentStream.newLineAtOffset(nextX, yPosition);
                contentStream.showText(String.valueOf(producto.getPrecio()));
                contentStream.endText();
                nextX += tableWidthFraction;

                contentStream.beginText();
                contentStream.newLineAtOffset(nextX, yPosition);
                contentStream.showText(String.valueOf(producto.getExistencia()));
                contentStream.endText();
                yPosition -= rowHeight;
            }

            contentStream.close();

            // Guardar el documento en el archivo especificado
            document.save(filePath);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTableHeader(int column) {
        switch (column) {
            case 0:
                return "Nombre";
            case 1:
                return "Código";
            case 2:
                return "Precio";
            case 3:
                return "Existencia";
            default:
                return "";
        }
    }
}