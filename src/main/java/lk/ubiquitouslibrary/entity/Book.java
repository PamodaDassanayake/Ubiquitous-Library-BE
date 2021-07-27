package lk.ubiquitouslibrary.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Data
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "edition")
    private Integer edition;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publish_year")
    private Integer publishYear;

    @Column(name = "no_of_copies")
    private Integer noOfCopies;

    @Size(max = 1000)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", isbn='" + getIsbn() + "'" +
            ", title='" + getTitle() + "'" +
            ", edition=" + getEdition() +
            ", author='" + getAuthor() + "'" +
            ", publisher='" + getPublisher() + "'" +
            ", publishYear=" + getPublishYear() +
            ", noOfCopies=" + getNoOfCopies() +
            "}";
    }
}
