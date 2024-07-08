package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.AnswerDetailsDTO;
import bg.softuni.taskmaster.model.dto.QuestionAnswerDTO;
import bg.softuni.taskmaster.model.dto.QuestionAskDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.repository.AnswerRepository;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.service.QuestionService;
import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserHelperService userHelperService;
    private final ModelMapper modelMapper;

    @Override
    @SneakyThrows
    public long ask(QuestionAskDTO questionAskDTO) {
        Question question = modelMapper.map(questionAskDTO, Question.class);
        question.setCreatedTime(LocalDateTime.now());
        question.setUser(userHelperService.getUser());
        return questionRepository.save(question).getId();
    }

    @Override
    @Transactional
    public QuestionDetailsDTO getDetailsDTO(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        QuestionDetailsDTO questionDetailsDTO = modelMapper.map(question, QuestionDetailsDTO.class);

        questionDetailsDTO.setAnswers(
                questionDetailsDTO.getAnswers()
                        .stream().
                        sorted(Comparator.comparing(AnswerDetailsDTO::getCreatedTime))
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );

        questionDetailsDTO.setTags(
                Arrays.stream(question.getTags().split("\\s+"))
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
        ;
        return questionDetailsDTO;
    }

    @Override
    @SneakyThrows
    public void answer(QuestionAnswerDTO questionAnswerDTO, Long id) {
        Answer answer = modelMapper.map(questionAnswerDTO, Answer.class);
        answer.setQuestion(questionRepository.findById(id).orElseThrow(RuntimeException::new));
        answer.setUser(userHelperService.getUser());
        answerRepository.save(answer);
    }
}
