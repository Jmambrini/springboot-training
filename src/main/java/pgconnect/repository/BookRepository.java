package pgconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pgconnect.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>{

}
