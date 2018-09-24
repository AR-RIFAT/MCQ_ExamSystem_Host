package sample;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class pdfgenerator {

    public static void pdf() throws Exception {


        DatabaseHelper.CheckUpdateResult(Helper.courseCode,Helper.subjectName);
        String dest=System.getProperty("user.home")+"/Desktop/"+Helper.courseCode+"_"+Helper.subjectName+".pdf";
        Document document=new Document();
        PdfWriter.getInstance(document,new FileOutputStream(dest));
        document.open();
        Paragraph paragraph=new Paragraph("EXAM RESULT\n\n");
        Paragraph para2=new Paragraph(Helper.courseCode+"     "+Helper.subjectName+"\n\n\n");
        para2.setAlignment(Element.ALIGN_CENTER);


        paragraph.setAlignment(Element.ALIGN_CENTER);


        document.add(paragraph);
        document.add(para2);
        document.add(createTable());
        document.close();

    }

    public static PdfPTable createTable() throws Exception {

        PdfPTable table=new PdfPTable(5);
        table.addCell("Reg No");
        table.addCell("Correct");
        table.addCell("Incorrect");
        table.addCell("Untouched");
        table.addCell("Score");

        List<StdInstance> data=DatabaseHelper.ReadtAllData(Helper.courseCode,Helper.subjectName);


        for(int i=0;i<data.size();i++)
        {
            table.addCell(data.get(i).getRegId());
            table.addCell(Integer.toString(data.get(i).getCorrect()));
            table.addCell(Integer.toString(data.get(i).getIncorrect()));
            table.addCell(Integer.toString(data.get(i).getUntouched()));
            table.addCell(Integer.toString(data.get(i).getTotal()));

        }

        return table;


    }
}
