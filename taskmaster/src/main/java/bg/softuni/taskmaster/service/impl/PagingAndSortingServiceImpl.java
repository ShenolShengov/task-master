package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.service.PagingAndSortingService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;


@SessionScope
@Getter
@Setter
@Service
public class PagingAndSortingServiceImpl implements PagingAndSortingService {

    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private static final Integer DEFAULT_PAGE = 0;
    private static final String DEFAULT_SORT_PROPERTIES = "id";
    private static final String DEFAULT_SEARCH_QUERY = "";
    private Integer page;
    private Integer size;
    private String sortProperties;
    private String sortDirection;
    private String searchQuery;

    public PagingAndSortingServiceImpl() {
        setSearchQuery(searchQuery);
        setSize(DEFAULT_PAGE_SIZE);
        setPage(DEFAULT_PAGE);
        setSortProperties(DEFAULT_SORT_PROPERTIES);
        setSortDirection(Sort.DEFAULT_DIRECTION.name());
    }

    @Override
    public void saveOptions(Integer page, String sortProperties, String sortDirection, String searchQuery) {
        setPage(page);
        setSortProperties(sortProperties);
        setSortDirection(sortDirection);
        setSearchQuery(searchQuery);
    }

    @Override
    public void saveOptions(Integer page, String sortProperties, String sortDirection) {
        saveOptions(page, sortProperties, sortDirection, "");
    }

    @Override
    public Pageable pageable() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortProperties));
    }
}
