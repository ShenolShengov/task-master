package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.Task;

public interface TaskHelperService {

    Task getById(Long id);

    boolean isActualUser(Long id);
}
