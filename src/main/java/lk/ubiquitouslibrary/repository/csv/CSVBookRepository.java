package lk.ubiquitouslibrary.repository.csv;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lk.ubiquitouslibrary.entity.Book;
import lk.ubiquitouslibrary.entity.BookAbstract;
import lk.ubiquitouslibrary.entity.BookCSV;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Iterator;
import java.util.List;

@Repository
public class CSVBookRepository {
    String fileName = "./books.csv";

    public void writeBooks(List<? extends BookAbstract> books) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Writer writer = new FileWriter(fileName);
        HeaderColumnNameMappingStrategy<BookAbstract> strategy = new HeaderColumnNameMappingStrategyBuilder<BookAbstract>().build();
        strategy.setType(BookAbstract.class);
//        strategy.setColumnOrderOnWrite(new MyComparator());
        StatefulBeanToCsv<BookAbstract> beanToCsv = new StatefulBeanToCsvBuilder<BookAbstract>(writer)
                .withMappingStrategy(strategy)
                .build();
        beanToCsv.write((List<BookAbstract>) books);
        writer.close();
    }

    public List<BookCSV> readBooks() throws FileNotFoundException {
        return new CsvToBeanBuilder<BookCSV>(new FileReader(fileName))
                .withType(BookCSV.class).build().parse();
    }
}
