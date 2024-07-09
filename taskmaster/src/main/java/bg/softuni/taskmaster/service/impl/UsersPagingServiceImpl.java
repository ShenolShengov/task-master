package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UsersPagingService;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
//@SessionScope
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
public class UsersPagingServiceImpl implements UsersPagingService {

    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private static final Integer DEFAULT_PAGE = 0;
    private static final int MAX_PAGE_SIZE = 10;
    private Integer page;
    private Integer size;

    private final JpaRepository<User, Long> userRepository;

    public UsersPagingServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        setSize(DEFAULT_PAGE_SIZE);
        setPage(DEFAULT_PAGE);
    }

    @Override
    public void setPage(Integer page) {
        if (page == null || page > pageCount()) {
            return;
        }
        this.page = page;
    }

    @Override
    public void setSize(Integer size) {
        if (size == null || size > MAX_PAGE_SIZE) {
            return;
        }
        this.size = size;
    }

    @Override
    public Integer pageCount() {
        return (int) Math.ceil((double) userRepository.count() / size);
    }

    @Override
    public boolean haseNextPage() {
        return userRepository.count() - (long) (page + 1) * size > 0;
    }

    @Override
    public boolean hasPrevPage() {
        return this.getPage() > 0;
    }

    @Override
    public Integer nextPage() {
        if (haseNextPage()) {
            page++;
        }
        return this.getPage();
    }

    @Override
    public Integer prevPage() {
        if (hasPrevPage()) {
            page--;
        }
        return getPage();
    }

}
