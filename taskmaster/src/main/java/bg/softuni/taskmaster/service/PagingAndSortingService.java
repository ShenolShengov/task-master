package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.BaseEntity;
import org.springframework.data.domain.Pageable;

public interface PagingAndSortingService<T extends BaseEntity> {


    Integer getPage();

    void setPage(Integer page);

    Integer getSize();

    void setSize(Integer size);

    String getSortBy();

    void setSortBy(String sortBy);

    Integer pageCount();

    boolean haseNextPage();

    boolean hasPrevPage();

    void nextPage();

    void prevPage();

    Pageable getPageable();

    void filterResult(Long elementCount);
    void setUp(Integer page, String sortBy, String sortDirection);
}
