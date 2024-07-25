package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.web.PagedModel;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PageResponseDTO<T> implements Serializable {

    private List<T> content;
    private PagedModel.PageMetadata page;

}
