package lk.ubiquitouslibrary.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_video")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(value = {"video"}, allowSetters = true)
public class CommentVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Video video;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JsonIgnoreProperties(value = {"email","activated","langKey","resetDate","nic","dob"}, allowSetters = true)
    private User user;

    @Column(name = "description")
    private String comment;

    @Column(name = "date_time")
    private LocalDateTime dateTime;
}
