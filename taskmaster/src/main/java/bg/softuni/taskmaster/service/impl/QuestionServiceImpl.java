package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.QuestionAskDTO;
import bg.softuni.taskmaster.model.dto.QuestionBaseInfoDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsInfoDTO;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.service.QuestionService;
import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserHelperService userHelperService;
    private final ModelMapper modelMapper;

    @Override
    public long ask(QuestionAskDTO questionAskDTO) {
        Question question = modelMapper.map(questionAskDTO, Question.class);
        question.setUser(userHelperService.getLoggedUser());
        return questionRepository.save(question).getId();
    }

    @Override
    @PreAuthorize("@questionServiceImpl.isActualUser(#id)")
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public QuestionDetailsInfoDTO getDetailsInfoDTO(Long id) {
        return questionRepository.findById(id)
                .map(e -> modelMapper.map(e, QuestionDetailsInfoDTO.class)
                        .setTags(mapToTags(e.getTags())))
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public Page<QuestionBaseInfoDTO> getQuestionsFrom(LocalDate questionCreatedTime, Pageable pageable) {
        if (questionCreatedTime == null) {
            return questionRepository.findAllByUserUsername(userHelperService.getUsername(), pageable)
                    .map(this::toBaseInfo);
        }
        return questionRepository.findAllByUserUsernameAndCreatedTimeDate(userHelperService.getUsername(),
                questionCreatedTime, pageable).map(this::toBaseInfo);
    }

    private LinkedHashSet<String> mapToTags(String tags) {
        return Arrays.stream(tags.split("\\s+")).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private QuestionBaseInfoDTO toBaseInfo(Question question) {
        return modelMapper.map(question, QuestionBaseInfoDTO.class).setTags(mapToTags(question.getTags()));
    }


    @Override
    public Page<QuestionBaseInfoDTO> getAll(String searchQuery, Pageable pageable) {
        if (searchQuery.isEmpty()) {
            return questionRepository.findAll(pageable).map(this::toBaseInfo);
        }
        return questionRepository.search(searchQuery, pageable).map(this::toBaseInfo);
    }

    @Override
    public boolean isActualUser(Long id) {
        return questionRepository.findById(id)
                .filter(e -> e.getUser().getUsername().equals(userHelperService.getUsername()))
                .isPresent();
    }
}
