package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.AnswerDetailsDTO;
import bg.softuni.taskmaster.model.dto.QuestionAnswerDTO;
import bg.softuni.taskmaster.model.dto.QuestionAskDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsInfoDTO;
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
    public QuestionDetailsInfoDTO getDetailsInfoDTO(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        QuestionDetailsInfoDTO questionDetailsInfoDTO = modelMapper.map(question, QuestionDetailsInfoDTO.class);

        questionDetailsInfoDTO.setAnswers(
                questionDetailsInfoDTO.getAnswers()
                        .stream().
                        sorted(Comparator.comparing(AnswerDetailsDTO::getCreatedTime))
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );

        questionDetailsInfoDTO.setTags(mapToTags(question.getTags()));
        return questionDetailsInfoDTO;
    }

    private static LinkedHashSet<String> mapToTags(String tags) {
        return Arrays.stream(tags.split("\\s+"))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public QuestionDetailsInfoDTO getBaseInfoDTO(Long id) {
        return null;
    }

    @Override
    @SneakyThrows
    public void answer(QuestionAnswerDTO questionAnswerDTO, Long id) {
        Answer answer = modelMapper.map(questionAnswerDTO, Answer.class);
        answer.setQuestion(questionRepository.findById(id).orElseThrow(RuntimeException::new));
        answer.setUser(userHelperService.getUser());
        answerRepository.save(answer);
        questionRepository.save(answer.getQuestion());
    }
}
