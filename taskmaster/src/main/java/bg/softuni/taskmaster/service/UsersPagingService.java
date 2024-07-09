package bg.softuni.taskmaster.service;

public interface UsersPagingService {


    Integer  getPage();

    void setPage(Integer  page);

    Integer  getSize();

    void setSize(Integer  size);

    Integer  pageCount();

    boolean haseNextPage();

    boolean hasPrevPage();

    Integer  nextPage();

    Integer  prevPage();


}
