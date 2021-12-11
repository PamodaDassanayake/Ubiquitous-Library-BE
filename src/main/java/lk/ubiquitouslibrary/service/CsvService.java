package lk.ubiquitouslibrary.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lk.ubiquitouslibrary.entity.Book;
import lk.ubiquitouslibrary.entity.BookCSV;
import lk.ubiquitouslibrary.repository.BookCSVRepository;
import lk.ubiquitouslibrary.repository.BookRepository;
import lk.ubiquitouslibrary.repository.csv.CSVBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CSVBookRepository csvRepository;

    @Autowired
    BookCSVRepository bookCSVRepository;

    @PostConstruct
    public void generateInitialCSV(){
        List<Book> books = bookRepository.findAll();
        try {
            csvRepository.writeBooks(books);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

    public void generateCSV(){
        List<BookCSV> books = bookCSVRepository.findAll();

        try {
            csvRepository.writeBooks(books);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 0 ? * * *")
    public void loadFromCSV(){
        List<BookCSV> books=null;
        try {
            books=csvRepository.readBooks();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (books != null){
            books.forEach(bookFromCSV -> {
                if (bookFromCSV.getId()>0) {
                    Book book = bookRepository.findById(bookFromCSV.getId()).get();
                    bookFromCSV.setDifference(bookFromCSV.getNoOfCopies() - book.getNoOfCopies());
                }
            });

            bookCSVRepository.saveAll(books);
        }
    }

    public List<BookCSV> getLoadedUpdates(){
        return bookCSVRepository.findAll();
    }

    public List<Book> applyChangeFromCSV(List<Long> ids){
        List<Book> books = new ArrayList<>();
        ids.forEach(id -> {
            Book book = bookRepository.findById(id).get();
            BookCSV fromCSV = bookCSVRepository.findById(id).get();

            book.setTitle(fromCSV.getTitle());
            book.setAuthor(fromCSV.getAuthor());
            book.setPublisher(fromCSV.getPublisher());
            book.setEdition(fromCSV.getEdition());
            book.setPublishYear(fromCSV.getPublishYear());
            book.setNoOfCopies(fromCSV.getNoOfCopies());

            fromCSV.setDifference(0);
            bookRepository.save(book);
            bookCSVRepository.save(fromCSV);

            books.add(book);
        });
        generateCSV();
        return books;
    }
}
