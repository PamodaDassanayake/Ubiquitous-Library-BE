package lk.ubiquitouslibrary.entity;

import lombok.Builder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Book.
 */
@Entity
@Table(name = "book_csv")
@DynamicUpdate
public class BookCSV extends BookAbstract implements Serializable {

    @Column(name = "difference")
    protected int difference;

    @Builder
    public BookCSV(Long id, String isbn, String title, Integer edition, String author, String publisher, Integer publishYear, Integer noOfCopies, @Size(max = 1000) String imageUrl, String description, int difference) {
        super(id, isbn, title, edition, author, publisher, publishYear, noOfCopies, imageUrl, description);
        this.difference = difference;
    }

    public BookCSV() {
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }
}
