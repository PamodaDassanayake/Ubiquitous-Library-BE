package lk.lendabook.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lk.lendabook.domain.Book;
import lk.lendabook.domain.BookFromCSV;
import lk.lendabook.repository.BookCSVRepository;
import lk.lendabook.repository.BookFromCSVRepository;
import lk.lendabook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookCSVRepository csvRepository;

    @Autowired
    BookFromCSVRepository bookFromCSVRepository;

    public void generateCSV(){
        List<Book> books = bookRepository.findAll();
        try {
            csvRepository.writeBooks(books);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

    public List<BookFromCSV> loadFromCSV(){
        List<BookFromCSV> books=null;
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

            bookFromCSVRepository.saveAll(books);
        }
        return books;
    }

    public List<Book> applyChangeFromCSV(List<Long> ids){
        List<Book> books = new ArrayList<>();
        ids.forEach(id -> {
            Book book = bookRepository.findById(id).get();
            BookFromCSV fromCSV = bookFromCSVRepository.findById(id).get();

            book.setTitle(fromCSV.getTitle());
            book.setAuthor(fromCSV.getAuthor());
            book.setPublisher(fromCSV.getPublisher());
            book.setEdition(fromCSV.getEdition());
            book.setPublishYear(fromCSV.getPublishYear());
            book.setNoOfCopies(fromCSV.getNoOfCopies());

            bookRepository.save(book);

            books.add(book);
        });
        return books;
    }
}
