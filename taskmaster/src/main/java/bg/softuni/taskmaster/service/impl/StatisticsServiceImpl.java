package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.StatisticsDTO;
import bg.softuni.taskmaster.repository.AnswerRepository;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.repository.TaskRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public StatisticsDTO getStatistics() {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setUsersCount(userRepository.count());
        statisticsDTO.setAdminUsersCount(userRepository.countAdminUsers());
        statisticsDTO.setRegularUsersCount(userRepository.countRegularUser());
        statisticsDTO.setTasksCount(taskRepository.count());
        statisticsDTO.setQuestionsCount(questionRepository.count());
        statisticsDTO.setAnswersCount(answerRepository.count());
        return statisticsDTO;
    }

}
