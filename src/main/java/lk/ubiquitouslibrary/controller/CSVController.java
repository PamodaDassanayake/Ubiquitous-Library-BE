package lk.ubiquitouslibrary.controller;

import lk.ubiquitouslibrary.entity.Book;
import lk.ubiquitouslibrary.entity.BookCSV;
import lk.ubiquitouslibrary.security.AuthoritiesConstants;
import lk.ubiquitouslibrary.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/csv")
public class CSVController {

    @Autowired
    CsvService csvService;

    @GetMapping("/generate")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public void generateCSV(){
        csvService.generateCSV();
    }

    @GetMapping("/load")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<BookCSV> loadCSV(){
        return csvService.getLoadedUpdates();
    }

    @PutMapping("/save")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<Book> saveChangesFromCSV(@RequestBody List<Long> ids){
        return csvService.applyChangeFromCSV(ids);
    }
}
