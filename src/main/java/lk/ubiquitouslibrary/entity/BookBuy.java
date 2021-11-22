package lk.ubiquitouslibrary.entity;

import lombok.Builder;

import javax.validation.constraints.Size;

public class BookBuy extends BookAbstract{

    @Builder
    public BookBuy(Long id, String isbn, String title, Integer edition, String author, String publisher, Integer publishYear, Integer noOfCopies, @Size(max = 1000) String imageUrl, String description) {
        super(id, isbn, title, edition, author, publisher, publishYear, noOfCopies, imageUrl, description);
    }

    public BookBuy() {
    }
}
