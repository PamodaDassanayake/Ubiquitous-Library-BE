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
public class Book extends BookAbstract implements Serializable {

    private static final long serialVersionUID = 1L;

}
