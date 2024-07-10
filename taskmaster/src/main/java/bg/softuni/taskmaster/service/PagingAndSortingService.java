package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import bg.softuni.taskmaster.model.entity.BaseEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface PagingAndSortingService<T extends BaseEntity> {


    Integer getPage();

    void setPage(Integer page);

    Integer getSize();

    void setSize(Integer size);

    String getSortBy();

    void setSortBy(String sortBy);

    boolean hasNextPage();
    boolean hasPrevPage();

    String getSortDirection();

    void setSortDirection(String sortDirection);

    Set<String> getSortingFields();


    Pageable getPageable();

    void setUp(Integer page, String sortBy, String sortDirection);

    void setElementCount(Integer elementCount);

    String getSearchQuery();
    void setSearchQuery(String searchQuery);

    List<?> applyPageable(List<?> founded);

    void setDefaultElementCounts();
}
