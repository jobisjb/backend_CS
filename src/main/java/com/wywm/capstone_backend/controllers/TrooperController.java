package com.wywm.capstone_backend.controllers;
import com.lowagie.text.DocumentException;
import com.wywm.capstone_backend.services.PDFExporter;
import com.wywm.capstone_backend.services.Trooper;
import com.wywm.capstone_backend.services.TroopersList;
import org.apache.coyote.Request;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(path = "api/troopers")
public class TrooperController {

    private List<Trooper> getTrooper(){
        TroopersList troopersList = new TroopersList();
        List<Trooper> troopers;
        {
            try {
                troopers = troopersList.getAllTroopers();
            } catch (ParserConfigurationException | IOException | SAXException e) {
                throw new RuntimeException(e);
            }
        }
        return troopers;
    }


    private void exportToPDF(HttpServletResponse response, List<Trooper> troopers) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM_HH-mm");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Troop_Report" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        PDFExporter pdfExporter = new PDFExporter(troopers);
        pdfExporter.export(response);
    }
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("sort")
    //This method make a copy of the list then sort and returns the copy.
    public List<Trooper> sort(@RequestParam(value="order")String order) {
        List<Trooper> tempTrooperList = new LinkedList<>(getTrooper());
        if (order.equals("asc")) { //If this condition passes, the method will attempt to sort the list in ascending order.
        tempTrooperList.sort((p1, p2) -> p1.getNum() - p2.getNum());
        } else if (order.equals("desc")) { //If this condition passes, the method will attempt to sort the list in descending order.
           tempTrooperList.sort((p1, p2) -> p2.getNum() - p1.getNum());
        }else if (order.equals("descPer")) { //If this condition passes, the method will attempt to sort the list in descending order.
            tempTrooperList.sort((p1, p2) -> p2.getPercentage() - p1.getPercentage());
        }
        else if (order.equals("ascPer")) { //If this condition passes, the method will attempt to sort the list in descending order.
            tempTrooperList.sort((p1, p2) -> p1.getPercentage() - p2.getPercentage());
        }
        return tempTrooperList;
    }

    @GetMapping("Export")
    public void printToPDF(HttpServletResponse response, @RequestParam(value="order")String order) throws IOException {
        exportToPDF(response, sort(order));
    }
//    @GetMapping("ExportSortASC")
//    public void exportToPDFASC(HttpServletResponse response) throws IOException {
//        exportToPDF(response, sort("number", "asc"));
//    }
//    @GetMapping("ExportSortDESC")
//    public void exportToPDFDESC(HttpServletResponse response) throws IOException {
//        exportToPDF(response, sort("number", "desc"));
//    }
//
//    @GetMapping("ExportSortPercASC")
//    public void exportToPDFPercASC(HttpServletResponse response) throws IOException {
//        exportToPDF(response, sort("percentage", "asc"));
//    }
//
//    @GetMapping("ExportSortPercDESC")
//    public void ExportSortPercDESC(HttpServletResponse response) throws IOException {
//        exportToPDF(response, sort("percentage", "desc"));
//    }

}
