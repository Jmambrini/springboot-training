package pgconnect.controller;

import pgconnect.exception.ResourceNotFoundException;
import pgconnect.model.Answer;
import pgconnect.repository.QuestionRepository;
import pgconnect.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private  QuestionRepository questionRepository;

    @GetMapping("/book/questions/{questionId}/answers")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {

        return answerRepository.findByQuestionId(questionId);
    }

    @PostMapping("/book/questions/{questionId}/answers")
    public Answer addAnswer(@PathVariable Long questionId, @Valid @RequestBody Answer answer) {

        return questionRepository.findById(questionId)
                .map(question -> {

                    answer.setQuestion(question);
                    return answerRepository.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException("Question with id " + questionId + " not found."));
    }

    @PutMapping("/book/questions/{questionId}/answers/{answerId}")
    public Answer updateAnswer(@PathVariable Long questionId,
                               @PathVariable Long answerId,
                               @Valid @RequestBody Answer answerRequest) {

        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question with id " + questionId + " not found.");
        }

        return answerRepository.findById(answerId)
                .map(answer -> {

                    answer.setText((answerRequest.getText()));
                    return answerRepository.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException("Answer with id " + answerId + " not found."));
    }

    @DeleteMapping("/book/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId,
                                          @PathVariable Long answerId) {

        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question with id " + questionId + " not found.");
        }

        return answerRepository.findById(answerId)
                .map(answer -> {

                    answerRepository.delete(answer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Answer with id " + answerId + " not found."));
    }
}
