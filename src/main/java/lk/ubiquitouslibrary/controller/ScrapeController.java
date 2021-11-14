package lk.lendabook.controller;

import lk.lendabook.domain.BookFromScrape;
import lk.lendabook.security.AuthoritiesConstants;
import lk.lendabook.service.ScrapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scrape")
public class ScrapeResource {

    @Autowired
    ScrapeService scrapeService;

    @GetMapping("/start")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public void startScrape(){
        scrapeService.scrapeBooks();
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public String scrapeStatus(){
        return scrapeService.isScraperRunning()?"Running":"Not Running";
    }

    @GetMapping
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<BookFromScrape> getScraped(){
        return scrapeService.loadScraped();
    }

}
