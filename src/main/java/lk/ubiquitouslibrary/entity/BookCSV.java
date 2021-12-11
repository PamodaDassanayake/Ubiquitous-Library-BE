package lk.ubiquitouslibrary.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }
}
