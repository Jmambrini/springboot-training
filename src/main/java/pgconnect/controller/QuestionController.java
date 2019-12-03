package pgconnect.controller;

import pgconnect.exception.ResourceNotFoundException;
import pgconnect.model.Question;
import pgconnect.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.json.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/book/questions")
    public Page<Question> getQuestions (Pageable pageable, @RequestParam int cas, @Valid @RequestBody String teste) throws ParseException {


        switch (cas){
            case 1:
                JSONObject obj = new JSONObject(teste);

                String teste1 = obj.getString("title");
                String teste2 = obj.getString("description");

                return questionRepository.findByTitleAndDescription(teste1, teste2, pageable);
            case 2:
                Long id = Long.parseLong(teste);

                return questionRepository.findById(id, pageable);
            case 3:
                return questionRepository.findByTitleOi(teste, pageable);
            case 5:
                JSONObject obj2 = new JSONObject(teste);

                String notTitle = obj2.getString("notTitle");
                JSONObject arr = obj2.getJSONObject("date");

                String startDate = "", startTime = "", endDate = "", endTime = "";

//                for (int i = 0; i < arr.length(); i++) {
                    startDate = arr.getString("startDate");
                    startTime = arr.getString("startTime");
                    endDate = arr.getString("endDate");
                    endTime = arr.getString("endTime");
//                }

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Date startDateTime = formato.parse((startDate + " " + startTime));
//                LocalDateTime startDateTime = LocalDateTime.parse((startDate + " " + startTime), formatter);
//                        ;
                Date endDateTime = formato.parse((endDate + " " + endTime));
//                LocalDateTime endDateTime = LocalDateTime.parse((endDate + " " + endTime), formatter);
//                        ;

                return questionRepository.findByNotTitleAndTime(notTitle, startDateTime, endDateTime, pageable);
            default:
                questionRepository.findAll(pageable);
        }

//        return questionRepository.findById(1000L, pageable);

        return questionRepository.findAll(pageable);
    }

    @PostMapping("/book/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {

        return questionRepository.save(question);
    }

    @PutMapping("/book/questions/{questionId}")
    public Question updateQuestion(@PathVariable Long questionId, @Valid @RequestBody Question questionRequest) {

        return questionRepository.findById(questionId)
                .map(question -> {

                    question.setTitle(questionRequest.getTitle());
                    question.setDescription(questionRequest.getDescription());
                    return questionRepository.save(question);
                }).orElseThrow(() -> new ResourceNotFoundException("Question with id " + questionId + " not found."));
    }

    @DeleteMapping("/book/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {

        return questionRepository.findById(questionId)
                .map(question -> {

                    questionRepository.delete(question);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Question with id " + questionId + " not found."));
    }
}
