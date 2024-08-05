package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.QuestionAskDTO;
import bg.softuni.taskmaster.model.dto.QuestionBaseInfoDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsInfoDTO;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.service.QuestionHelperService;
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
    private final QuestionHelperService questionHelperService;
    private final UserHelperService userHelperService;
    private final ModelMapper modelMapper;

    @Override
    public long ask(QuestionAskDTO questionAskDTO) {
        Question question = modelMapper.map(questionAskDTO, Question.class);
        question.setUser(userHelperService.getLoggedUser());
        return questionRepository.save(question).getId();
    }

    @Override
    @PreAuthorize("@questionServiceImpl.isActualUser(#id) || hasRole('ADMIN')")
    public void delete(Long id) {
        questionRepository.delete(questionHelperService.getById(id));
    }

    @Override
    @Transactional
    public QuestionDetailsInfoDTO getDetailsInfo(Long id) {
        Question question = questionHelperService.getById(id);
        QuestionDetailsInfoDTO questionDetailsInfoDTO =
                modelMapper.map(question, QuestionDetailsInfoDTO.class);
        questionDetailsInfoDTO.setTags(mapToTags(question.getTags()));
        return questionDetailsInfoDTO;
    }

    @Override
    public Page<QuestionBaseInfoDTO> getFrom(LocalDate date, Pageable pageable) {
        if (date == null) {
            return questionRepository.findAllByUserUsername(userHelperService.getUsername(), pageable)
                    .map(this::toBaseInfo);
        }
        return questionRepository.findAllByUserUsernameAndCreatedTimeDate(userHelperService.getUsername(),
                date, pageable).map(this::toBaseInfo);
    }

    private LinkedHashSet<String> mapToTags(String tags) {
        return Arrays.stream(tags.split("\\s+")).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private QuestionBaseInfoDTO toBaseInfo(Question question) {
        return modelMapper.map(question, QuestionBaseInfoDTO.class).setTags(mapToTags(question.getTags()));
    }


    @Override
    public Page<QuestionBaseInfoDTO> getAll(String searchQuery, Pageable pageable) {
        return questionRepository.search(searchQuery, pageable).map(this::toBaseInfo);
    }

    @Override
    public boolean isActualUser(Long id) {

        return questionHelperService.getById(id).getUser().getUsername().equals(userHelperService.getUsername());
    }

}
