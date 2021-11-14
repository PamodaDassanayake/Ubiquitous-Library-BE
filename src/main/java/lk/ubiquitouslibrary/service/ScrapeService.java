package lk.lendabook.service;

import lk.lendabook.domain.BookFromScrape;
import lk.lendabook.helpers.Scraper1;
import lk.lendabook.repository.BookFromScrapeRepository;
import lk.lendabook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapeService {
    @Autowired
    BookFromScrapeRepository repository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    Scraper1 scraper1;

    public void scrapeBooks(){
        if (!scraper1.isRunning()) {
            repository.deleteAllInBatch();
            Thread thread = new Thread(() -> {
                List<BookFromScrape> books = scraper1.scrape();
                repository.saveAll(books);
            });
            thread.start();
        }
    }

    public List<BookFromScrape> loadScraped(){
        return repository.findAll();
    }

    public boolean isScraperRunning(){
        return scraper1.isRunning();
    }

//    public Book addToLibrary(Long idFromScrape){
//        BookFromScrape bookFromScrape = repository.findById(idFromScrape).get();
//
//        Book book = new Book()
//                .author(bookFromScrape.getAuthor())
//                .title(bookFromScrape.getTitle())
//                .edition(bookFromScrape.getEdition())
//                .
//    }
}
