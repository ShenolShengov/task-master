package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.entity.BaseEntity;
import bg.softuni.taskmaster.service.PagingAndSortingService;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


//@SessionScope
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
public class PagingAndSortingServiceImpl<T extends BaseEntity> implements PagingAndSortingService<T> {

    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private static final Integer DEFAULT_PAGE = 0;
    private static final String DEFAULT_SORT_BY = "id";
    private static final int MAX_PAGE_SIZE = 10;
    private final Class<T> clazz;
    private final JpaRepository<T, Long> repository;
    private final Set<String> sortingFields;
    private Integer page;
    private Integer size;
    private String sortBy;
    private Long elementsCount;

    public PagingAndSortingServiceImpl(JpaRepository<T, Long> repository, Class<T> clazz) {
        this.repository = repository;
        this.clazz = clazz;
        this.sortingFields = getSortingFields(clazz);
        this.elementsCount = repository.count();
        setSize(DEFAULT_PAGE_SIZE);
        setPage(DEFAULT_PAGE);
        setSortBy(DEFAULT_SORT_BY);
    }

    private Set<String> getSortingFields(Class<?> type) {
        Set<String> sortingFields = Arrays.stream(type.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toSet());
        sortingFields.add("id");
        return sortingFields;
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
    public String getSortBy() {
        return sortBy;
    }

    @Override
    public void setSortBy(String sortBy) {

        if (sortBy == null || !sortingFields.contains(sortBy)) {
            return;
        }
        this.sortBy = sortBy;
    }

    @Override
    public Integer pageCount() {
        return (int) Math.ceil((double) elementsCount / size);
    }

    @Override
    public boolean haseNextPage() {
        return pageCount() - (long) (page + 1) * size > 0;
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

    @Override
    public Pageable getPageable() {
        return PageRequest.of(getPage(), getSize(), Sort.Direction.ASC, getSortBy());
    }

    @Override
    public void filterResult(Long elementsCount) {
        this.elementsCount = elementsCount;
        setPage(0);
    }

    @Override
    public void setUp(Integer page, String sortBy) {
        setPage(page);
        setSortBy(sortBy);
    }

}
