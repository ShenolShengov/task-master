package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.AnswerDetailsDTO;
import bg.softuni.taskmaster.model.dto.AskQuestionDTO;
import bg.softuni.taskmaster.model.dto.QuestionAnswerDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.repository.AnswerRepository;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.QuestionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    @Override
    public long ask(AskQuestionDTO askQuestionDTO) {
        String authenticatedUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Question question = modelMapper.map(askQuestionDTO, Question.class);
        question.setCreatedTime(LocalDateTime.now());
        question.setUser(userRepository.findByUsername(authenticatedUserUsername).orElseThrow(
                RuntimeException::new));//todo add custom exception - ObjectNotFoundException
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
    public void answer(QuestionAnswerDTO questionAnswerDTO, Long id) {
        String authenticatedUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Answer answer = modelMapper.map(questionAnswerDTO, Answer.class);
        answer.setQuestion(questionRepository.findById(id).orElseThrow(RuntimeException::new));
        answer.setUser(userRepository.findByUsername(authenticatedUserUsername).orElseThrow(
                RuntimeException::new));//todo add custom exception - ObjectNotFoundException
        answerRepository.save(answer);
    }
}
