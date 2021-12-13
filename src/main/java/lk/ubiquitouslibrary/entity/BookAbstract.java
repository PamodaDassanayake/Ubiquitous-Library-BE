package lk.ubiquitouslibrary.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class BookAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "isbn")
    protected String isbn;

    @Column(name = "title")
    protected String title;

    @Column(name = "edition")
    protected Integer edition;

    @Column(name = "author", length = 1000)
    protected String author;

    @Column(name = "publisher")
    protected String publisher;

    @Column(name = "publish_year")
    protected Integer publishYear;

    @Column(name = "no_of_copies")
    protected Integer noOfCopies;

    @Size(max = 1000)
    @Column(name = "image_url", length = 1000)
    protected String imageUrl;

    @Column(name = "description", length = 1000)
    protected String description;

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
