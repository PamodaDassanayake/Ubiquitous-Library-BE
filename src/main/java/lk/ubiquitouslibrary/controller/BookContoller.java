package lk.ubiquitouslibrary.controller;

import io.swagger.annotations.ApiParam;
import lk.ubiquitouslibrary.entity.Book;
import lk.ubiquitouslibrary.entity.BookBuy;
import lk.ubiquitouslibrary.repository.BookBuyRepository;
import lk.ubiquitouslibrary.repository.BookRepository;
import lk.ubiquitouslibrary.repository.GoogleBooksRepository;
import lk.ubiquitouslibrary.security.AuthoritiesConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Book}.
 */
@RestController
@RequestMapping("/api/books")
@Transactional
public class BookContoller {

    private final Logger log = LoggerFactory.getLogger(BookContoller.class);

    private static final String ENTITY_NAME = "book";

    private final BookRepository bookRepository;

    private final GoogleBooksRepository googleBooksRepository;

    private final BookBuyRepository bookBuyRepository;

    public BookContoller(BookRepository bookRepository, GoogleBooksRepository googleBooksRepository, BookBuyRepository bookBuyRepository) {
        this.bookRepository = bookRepository;
        this.googleBooksRepository = googleBooksRepository;
        this.bookBuyRepository = bookBuyRepository;
    }

    /**
     * {@code POST  /books} : Create a new book.
     *
     * @param book the book to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new book, or with status {@code 400 (Bad Request)} if the book has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) throws URISyntaxException {
        log.debug("REST request to save Book : {}", book);
        if (book.getId() != null) {
            throw HttpClientErrorException.BadRequest.create("A new book cannot already have an ID",HttpStatus.BAD_REQUEST,"idexists",null,null, StandardCharsets.UTF_8);
        }
        Book result = bookRepository.save(book);
        return ResponseEntity
            .created(new URI("/api/books/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /books/:id} : Updates an existing book.
     *
     * @param id the id of the book to save.
     * @param book the book to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated book,
     * or with status {@code 400 (Bad Request)} if the book is not valid,
     * or with status {@code 500 (Internal Server Error)} if the book couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id", required = false) final Long id, @RequestBody Book book)
        throws URISyntaxException {
        log.debug("REST request to update Book : {}, {}", id, book);
        if (book.getId() == null) {
            throw HttpClientErrorException.BadRequest.create("Invalid Id",HttpStatus.BAD_REQUEST,"idnull",null,null, StandardCharsets.UTF_8);
        }
        if (!Objects.equals(id, book.getId())) {
            throw HttpClientErrorException.BadRequest.create("Invalid Id",HttpStatus.BAD_REQUEST,"idinvalidid",null,null, StandardCharsets.UTF_8);
        }

        if (!bookRepository.existsById(id)) {
            throw HttpClientErrorException.NotFound.create("Entity not found", HttpStatus.NOT_FOUND, "idnotfound", null, null, StandardCharsets.UTF_8);
        }

        Book result = bookRepository.save(book);
        return ResponseEntity
            .ok()
            .body(result);
    }


    /**
     * {@code GET  /books} : get all the books.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of books in body.
     */
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        log.debug("REST request to get all Books");
        return bookRepository.findAll();
    }

    /**
     * {@code GET  /books} : Search all the books.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of books in body.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> getAllBooks(
            @ApiParam("Title of the book") @RequestParam(required = false, defaultValue = "") String title,
            @ApiParam("Author of the book") @RequestParam(required = false, defaultValue = "") String author
    ) {
        log.debug("REST request to search all Books");

        if (!(title == null || title.isBlank()) && (author == null || author.isBlank())){
            //search by title
            return ResponseEntity.ok(bookRepository.findAllByTitleContaining(title));
        }else if ((title == null || title.isBlank()) && !(author == null || author.isBlank())){
            //search by author
            return ResponseEntity.ok(bookRepository.findAllByAuthorContaining(author));
        }else if (!(title == null || title.isBlank()) && !(author == null || author.isBlank())){
            //serch by both
            return ResponseEntity.ok(bookRepository.findAllByTitleContainingOrAuthorContaining(title, author));
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * {@code GET  /books/:id} : get the "id" book.
     *
     * @param id the id of the book to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the book, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        log.debug("REST request to get Book : {}", id);
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /books/:id} : delete the "id" book.
     *
     * @param id the id of the book to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.debug("REST request to delete Book : {}", id);
        bookRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    /**
     * Search book from Google Books Repository
     * */
    @GetMapping("/google")
    public List<BookBuy> serachGoogle(@RequestParam String q){
        return googleBooksRepository.searchBook(q);
    }

    /**
     * Request a unavailable book
     * */
    @PutMapping("/request")
    public BookBuy request(@RequestBody BookBuy bookBuy){
        return bookBuyRepository.save(bookBuy);
    }

    /**
     * Get all requested books
     * */
    @GetMapping ("/requested")
    public List<BookBuy> requested(){
        return bookBuyRepository.findAll();
    }

    /**
     * Add a requested book to the library. And delete it from request list
     * */
    @PostMapping("/requested/purchased")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Book> boughtFromListBook(@RequestBody Book book) throws URISyntaxException {
        bookBuyRepository.deleteById(book.getId());
        book.setId(null);
        return createBook(book);
    }

}
