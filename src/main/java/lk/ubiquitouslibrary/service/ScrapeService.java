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
    Scraper scraper;

    public void scrapeBooks() {
        if (!scraper.isRunning()) {
            repository.deleteAllInBatch();
            Thread thread = new Thread(() -> {
                List<BookScrape> books = scraper.scrape();
                books.forEach(bookScrape -> {
                    try {
                        repository.save(bookScrape);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            thread.start();
        }
    }

    public List<BookScrape> loadScraped() {
        return repository.findAll();
    }

    public boolean isScraperRunning() {
        return scraper.isRunning();
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
