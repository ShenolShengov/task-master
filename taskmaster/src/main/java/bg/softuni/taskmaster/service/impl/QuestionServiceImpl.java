package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.mappers.QuestionMapper;
import bg.softuni.taskmaster.model.dto.QuestionAnswerDTO;
import bg.softuni.taskmaster.model.dto.QuestionAskDTO;
import bg.softuni.taskmaster.model.dto.QuestionBaseInfoDTO;
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

        return questionRepository.findById(id).map(e -> QuestionMapper.INSTANCE.toDetailsInfo(e).setTags(mapToTags(e.getTags()))).orElseThrow(NullPointerException::new);
    }

    private static LinkedHashSet<String> mapToTags(String tags) {
        return Arrays.stream(tags.split("\\s+")).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public QuestionBaseInfoDTO getBaseInfoDTO(Question question) {
        return QuestionMapper.INSTANCE.toBaseInfo(question).setTags(mapToTags(question.getTags()));
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
