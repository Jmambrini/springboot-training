package pgconnect.controller;

import pgconnect.exception.ResourceNotFoundException;
import pgconnect.model.Answer;
import pgconnect.model.Book;
import pgconnect.repository.BookRepository;
import pgconnect.repository.QuestionRepository;
import pgconnect.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private  QuestionRepository questionRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book")
    public Page<Book> getBook(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

}
