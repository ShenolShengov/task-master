package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.Question;

public interface QuestionHelperService {

    Question getById(Long id);
}
