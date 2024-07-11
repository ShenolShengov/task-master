package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.BaseEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface PagingAndSortingService<T extends BaseEntity> {

    Integer getPage();
    Integer getPageCount();
    String getSortBy();

    boolean hasNextPage();

    boolean hasPrevPage();

    String getSortDirection();

    Set<String> getSortingFields();

    Pageable getPageable();

    void setUp(Integer page, String sortBy, String sortDirection, String searchQuery);

    void setUp(Integer page, String sortBy, String sortDirection);

    Integer getTotalElements();

    void setTotalElements(Integer elementCount);

    String getSearchQuery();


}
