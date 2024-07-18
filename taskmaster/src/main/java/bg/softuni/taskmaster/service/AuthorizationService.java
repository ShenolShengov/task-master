package bg.softuni.taskmaster.service;

public interface AuthorizationService {

    void makeAdmin(Long id);

    void removeAdmin(Long id);
}
