package me.parzibyte.sistemaventasspringboot;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReporteControlador {

    @Autowired
    private ProductosRepository productosRepository;

    @GetMapping("/products")
    public ModelAndView generateProductReport(HttpServletResponse response) throws IOException {
        Iterable<Producto> productosIterable = productosRepository.findAll();

        List<Producto> productos = new ArrayList<>();
        productosIterable.forEach(productos::add);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=product_report.pdf");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Encabezado con detalles de la empresa
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 780);
        contentStream.showText("                   Empresa: Ice Cream               Dirección: 2da Avenida 1-47 Zona 1");
        contentStream.endText();

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 760);
        contentStream.showText("                  Teléfono: 41338903                Correo: Omar.mp2002@gmail.com");
        contentStream.endText();


        // Título centrado y en negro
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.setNonStrokingColor(Color.BLACK);
        float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth("Reporte de Productos en Quetzales (GTQ)") / 1000 * 16;
        float titleX = (PDRectangle.A4.getWidth() - titleWidth) / 2;
        float titleY = 810;
        contentStream.beginText();
        contentStream.newLineAtOffset(titleX, titleY);
        contentStream.showText("Reporte de Productos en Quetzales (GTQ)");
        contentStream.endText();

        // Línea horizontal bajo el título
        contentStream.setStrokingColor(Color.BLACK);
        contentStream.moveTo(50, 740);
        contentStream.lineTo(550, 740);
        contentStream.stroke();

        int y = 720; // Posición inicial del contenido

        for (Producto producto : productos) {
            y -= 20;

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText("Nombre:");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(150, y);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText(producto.getNombre());
            contentStream.endText();

            y -= 15;

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText("Código:");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(150, y);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText(producto.getCodigo());
            contentStream.endText();

            y -= 15;

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText("Cantidad:");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(150, y);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText(String.valueOf(producto.getExistencia()));
            contentStream.endText();

            y -= 15;

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText("Precio:");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(150, y);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText("Q " + String.format("%.2f", producto.getPrecio()));
            contentStream.endText();

            // Dibujar una línea horizontal como separador
            contentStream.setStrokingColor(Color.GRAY); // Puedes ajustar el color según tus preferencias
            contentStream.moveTo(50, y - 5); // Posición inicial de la línea
            contentStream.lineTo(550, y - 5); // Posición final de la línea
            contentStream.stroke();
        }

        // Obtener la fecha y hora actual
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHora = sdf.format(new Date());

        // Crear una nueva sección para el pie de página
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.setNonStrokingColor(Color.GRAY); // Puedes ajustar el color según tus preferencias
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 30); // Ajusta la posición según tus necesidades
        contentStream.showText("Fecha y Hora de Generación: " + fechaHora);
        contentStream.endText();

        contentStream.close();
        document.save(response.getOutputStream());
        document.close();

        return null;
    }
}
