package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.AskQuestionDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsDTO;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' kk:mm");
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
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
    public QuestionDetailsDTO getDetailsDTO(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        QuestionDetailsDTO questionDetailsDTO = modelMapper.map(question, QuestionDetailsDTO.class);
        questionDetailsDTO.setCreatedTime(DATE_TIME_FORMATTER.format(question.getCreatedTime()));
        questionDetailsDTO.setTags(Arrays.stream(question.getTags().split("\\s+"))
                .collect(LinkedHashSet::new,
                        HashSet::add,
                        AbstractCollection::addAll));
        return questionDetailsDTO;
    }
}
