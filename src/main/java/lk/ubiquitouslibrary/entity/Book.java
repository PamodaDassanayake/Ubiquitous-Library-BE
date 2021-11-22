package lk.ubiquitouslibrary.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
public class Book extends BookAbstract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public Book(Long id, String isbn, String title, Integer edition, String author, String publisher, Integer publishYear, Integer noOfCopies, @Size(max = 1000) String imageUrl, String description) {
        super(id, isbn, title, edition, author, publisher, publishYear, noOfCopies, imageUrl, description);
    }

    public Book() {
    }
}
