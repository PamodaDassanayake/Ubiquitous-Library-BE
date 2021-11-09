package lk.lendabook.domain;

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
public class BookFromCSV extends AbstractBook implements Serializable {

    @Column(name = "difference")
    private int difference;

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }
}
