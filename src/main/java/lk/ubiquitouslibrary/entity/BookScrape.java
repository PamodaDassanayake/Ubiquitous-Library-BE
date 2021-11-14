package lk.ubiquitouslibrary.entity;

import lombok.Builder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
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
}
