package tw.team1.trail.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import tw.team1.trail.service.CSVService;

@Controller
public class ReadCSV {

    @Autowired
    public CSVService csvService;


    @GetMapping("/readCsv")
    public String readCsv(){
        csvService.uploadCSVFile();
        return "讀取csv";
    }
}
