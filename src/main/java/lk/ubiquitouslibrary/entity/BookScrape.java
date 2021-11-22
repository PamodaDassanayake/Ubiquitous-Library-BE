package lk.ubiquitouslibrary.entity;

import lombok.Builder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Book.
 */
@Entity
@Table(name = "book_scrape")
@DynamicUpdate
public class BookScrape extends BookAbstract implements Serializable {

    public BookScrape() {
    }

    @Builder
    public BookScrape(Long id, String isbn, String title, Integer edition, String author, String publisher, Integer publishYear, Integer noOfCopies, @Size(max = 1000) String imageUrl, String description) {
        super(id, isbn, title, edition, author, publisher, publishYear, noOfCopies, imageUrl, description);
    }
}
