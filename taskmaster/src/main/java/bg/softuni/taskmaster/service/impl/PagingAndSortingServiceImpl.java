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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


//@SessionScope
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
public class PagingAndSortingServiceImpl<T extends BaseEntity> implements PagingAndSortingService<T> {

    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private static final int MAX_PAGE_SIZE = 10;
    private static final Integer DEFAULT_PAGE = 0;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_SORT_DIRECTION = "ASC";
    private final Class<T> clazz;
    private final JpaRepository<T, Long> repository;
    private final Set<String> sortingFields;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortDirection;
    private String searchQuery;
    private Integer elementsCount;

    public PagingAndSortingServiceImpl(JpaRepository<T, Long> repository, Class<T> clazz) {
        this.repository = repository;
        this.clazz = clazz;
        this.sortingFields = getSortingFields();
        this.elementsCount = (int) repository.count();
        this.searchQuery = "";
        setSize(DEFAULT_PAGE_SIZE);
        setPage(DEFAULT_PAGE);
        setSortBy(DEFAULT_SORT_BY);
        setSortDirection(DEFAULT_SORT_DIRECTION);
    }


    @Override
    public void setPage(Integer page) {
        if (page == null || page > getPageCount()) {
            return;
        }
        this.page = page;
    }

    private double getPageCount() {
        return Math.ceil((double) elementsCount / size) - 1;
    }

    @Override
    public void setSize(Integer size) {
        if (size == null || size > MAX_PAGE_SIZE) {
            return;
        }
        this.size = size;
    }

    @Override
    public void setSortBy(String sortBy) {
        if ((sortBy == null || !sortingFields.contains(sortBy)) && !"id".equals(sortBy)) {
            return;
        }
        this.sortBy = sortBy;
    }

    @Override
    public boolean hasNextPage() {
        return page < getPageCount();
    }

    @Override
    public boolean hasPrevPage() {
        return page > 0;
    }

    public Set<String> getSortingFields() {

        return Arrays.stream(clazz.getDeclaredFields()).filter(e -> e.isAnnotationPresent(SortParam.class)).map(Field::getName).collect(Collectors.toCollection(LinkedHashSet::new));
    }


    @Override
    public void setSortDirection(String sortDirection) {
        if (!"ASC".equals(sortDirection) && !"DESC".equals(sortDirection)) {
            return;
        }
        this.sortDirection = sortDirection;
    }


    @Override
    public Pageable getPageable() {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return PageRequest.of(getPage(), getSize(), direction, sortBy);
    }


    @Override
    public void setUp(Integer page, String sortBy, String sortDirection) {
        setPage(page);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
    }

    @Override
    public void setElementCount(Integer elementsCount) {
        this.elementsCount = elementsCount;
    }

    @Override
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    public List<?> applyPageable(List<?> founded) {
        Pageable pageable = getPageable();
        int end = (int) pageable.getOffset() + pageable.getPageSize() * (pageable.getPageNumber() + 1);
        return founded.subList((int) pageable.getOffset(), Math.min(end, founded.size()));
    }

    @Override
    public void setDefaultElementCounts() {
        this.elementsCount = (int) repository.count();
    }


}
