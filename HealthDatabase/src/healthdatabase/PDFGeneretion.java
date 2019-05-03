
package healthdatabase;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;


public class PDFGeneretion {
    public void pdfGeneration(String fileName, ObservableList<Records> data){
        
        Document document = new Document();
        
        try{
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
        
        float[] columnWidths = {6, 3, 6, 6, 3, 3};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("Adatb�zis �rt�kek"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(6);
            table.addCell(cell);
            
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("D�tum");
            table.addCell("V�rcukor");
            table.addCell("�tkez�s");
            table.addCell("V�rnyom�s");
            table.addCell("Pulzus");
            table.addCell("Tests�ly");
            table.setHeaderRows(1);
            
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            for (int i = 1; i <= data.size(); i++) {
                Records actualRecord = data.get(i - 1);
                
                table.addCell(actualRecord.getDate());
                table.addCell(actualRecord.getGlucose());
                table.addCell(actualRecord.getMealType());
                table.addCell(actualRecord.getBloodPress());
                table.addCell(actualRecord.getPulse());
                table.addCell(actualRecord.getWeight());
            }
            
            document.add(table);
           
 
            //Al��r�s
            Chunk signature = new Chunk("\n\n Gener�lva a Health Database alkalmaz�s seg�ts�g�vel.");
            Paragraph base = new Paragraph(signature);
            document.add(base);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
    
}
