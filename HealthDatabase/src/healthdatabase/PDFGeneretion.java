
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
            PdfPCell cell = new PdfPCell(new Phrase("Adatbázis értékek"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(6);
            table.addCell(cell);
            
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Dátum");
            table.addCell("Vércukor");
            table.addCell("Étkezés");
            table.addCell("Vérnyomás");
            table.addCell("Pulzus");
            table.addCell("Testsúly");
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
           
 
            //Aláírás
            Chunk signature = new Chunk("\n\n Generálva a Health Database alkalmazás segítségével.");
            Paragraph base = new Paragraph(signature);
            document.add(base);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
    
}
