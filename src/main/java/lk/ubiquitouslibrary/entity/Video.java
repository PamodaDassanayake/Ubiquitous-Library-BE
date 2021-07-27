package lk.ubiquitouslibrary.entity;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Video.
 */
@Entity
@Table(name = "video")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "director")
    private String director;

    @Column(name = "studio")
    private String studio;

    @Column(name = "publish_year")
    private Integer publishYear;

    @Size(max = 1000)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 1000)
    @Column(name = "video_url", length = 256)
    private String videoUrl;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Video{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", director='" + getDirector() + "'" +
            ", studio='" + getStudio() + "'" +
            ", publishYear=" + getPublishYear() +
            "}";
    }
}
