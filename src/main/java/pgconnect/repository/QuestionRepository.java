package pgconnect.repository;

import pgconnect.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE title = :title")
    Page<Question> findByTitleOi(String title, Pageable pageable);

    @Query("SELECT q from Question q WHERE createdAt >= :startDateTime AND createdAt <= :endDateTime AND NOT title = :notTitle")
    Page<Question> findByNotTitleAndTime(String notTitle, Date startDateTime, Date endDateTime, Pageable pageable);

    Page<Question> findByTitleAndDescription(String title, String description, Pageable pageable);

    Page<Question> findById(Long id, Pageable pageable);
}
