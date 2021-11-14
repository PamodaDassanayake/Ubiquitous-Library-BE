package lk.ubiquitouslibrary.service;

import lk.ubiquitouslibrary.entity.BookScrape;
import lk.ubiquitouslibrary.repository.BookRepository;
import lk.ubiquitouslibrary.repository.BookScrapeRepository;
import lk.ubiquitouslibrary.util.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapeService {
    @Autowired
    BookScrapeRepository repository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    Scraper scraper1;

    public void scrapeBooks(){
        if (!scraper1.isRunning()) {
            repository.deleteAllInBatch();
            Thread thread = new Thread(() -> {
                List<BookScrape> books = scraper1.scrape();
                repository.saveAll(books);
            });
            thread.start();
        }
    }

    public List<BookScrape> loadScraped(){
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
