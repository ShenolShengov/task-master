package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.anottation.SortParam;
import bg.softuni.taskmaster.model.entity.BaseEntity;
import bg.softuni.taskmaster.service.PagingAndSortingService;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


//@SessionScope
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
public class PagingAndSortingServiceImpl<T extends BaseEntity> implements PagingAndSortingService<T> {

    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private static final Integer DEFAULT_PAGE = 0;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_SORT_DIRECTION = "ASC";
    private static final String DEFAULT_SEARCH_QUERY = "";
    private final Class<T> clazz;
    private final JpaRepository<T, Long> repository;
    private final Set<String> sortingFields;
    private Integer page;
    private final Integer size;
    private String sortBy;
    private String sortDirection;
    private String searchQuery;
    private Integer totalElements;

    public PagingAndSortingServiceImpl(JpaRepository<T, Long> repository, Class<T> clazz) {
        this.repository = repository;
        this.clazz = clazz;
        this.sortingFields = getSortingFields();
        this.totalElements = (int) repository.count();
        this.searchQuery = DEFAULT_SEARCH_QUERY;
        this.size = DEFAULT_PAGE_SIZE;
        setPage(DEFAULT_PAGE);
        setSortBy(DEFAULT_SORT_BY);
        setSortDirection(DEFAULT_SORT_DIRECTION);
    }

    @Override
    public boolean hasNextPage() {
        return page < getPageCount();
    }

    @Override
    public boolean hasPrevPage() {
        return page > 0;
    }

    @Override
    public Set<String> getSortingFields() {

        return Arrays.stream(clazz.getDeclaredFields()).filter(e -> e.isAnnotationPresent(SortParam.class)).map(Field::getName).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Pageable getPageable() {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return PageRequest.of(getPage(), getSize(), direction, sortBy);
    }

    @Override
    public void setUp(Integer page, String sortBy, String sortDirection, String searchQuery) {
        setPage(page);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setDefaultTotalElements();
        setSearchQuery(searchQuery);
    }


    @Override
    public void setUp(Integer page, String sortBy, String sortDirection) {
        setUp(page, sortBy, sortDirection, "");
    }

    @Override
    public void setTotalElements(Integer elementsCount) {
        this.totalElements = elementsCount;
    }

    private void setPage(Integer page) {
        if (page == null || page > getPageCount()) {
            return;
        }
        this.page = page;
    }


    private void setSortBy(String sortBy) {
        if ((sortBy == null || !sortingFields.contains(sortBy)) && !"id".equals(sortBy)) {
            return;
        }
        this.sortBy = sortBy;
    }

    private void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    private void setSortDirection(String sortDirection) {
        if (!"ASC".equals(sortDirection) && !"DESC".equals(sortDirection)) {
            return;
        }
        this.sortDirection = sortDirection;
    }

    @Override
    public Integer getPageCount() {
        return (int) Math.ceil((double) totalElements / size) - 1;
    }

    private void setDefaultTotalElements() {
        this.totalElements = (int) repository.count();
    }

}
