package bg.softuni.taskmaster.service;

import org.springframework.data.domain.Pageable;

public interface PagingAndSortingService {

    String getSortProperties();

    String getSortDirection();

    String getSearchQuery();

    void saveOptions(Integer page, String sortProperties, String sortDirection, String searchQuery);

    void saveOptions(Integer page, String sortProperties, String sortDirection);


    Pageable pageable();
}
