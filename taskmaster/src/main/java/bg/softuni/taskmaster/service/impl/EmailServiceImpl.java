package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.MailHistoryInfoDTO;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.model.enums.EmailParam;
import bg.softuni.taskmaster.model.enums.EmailTemplate;
import bg.softuni.taskmaster.service.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Type;
import java.util.EnumMap;

@Service
public class EmailServiceImpl implements EmailService {


    private final RestClient restClient;

    public EmailServiceImpl(@Qualifier("mail-rest-client") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void send(Payload payload) {
        restClient.post().uri("/send").body(payload).retrieve();
    }

    @Override
    public Payload createPayload(String from, String to, String subject, EmailTemplate template, EnumMap<EmailParam, String> params) {
        return new Payload(from, to, subject, template, params);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagedModel<MailHistoryInfoDTO> history(Pageable pageable) {
        return restClient.get().uri(u -> u.path("/history")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("sort", pageable.getSort().toString().replace(": ", ","))
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

}

//class CustomPageImpl<T> extends PageImpl<T> {
//    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
//    public CustomPageImpl(@JsonProperty("content") List<T> content, @JsonProperty("number") int number,
//                          @JsonProperty("size") int size, @JsonProperty("totalElements") Long totalElements,
//                          @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last,
//                          @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort,
//                          @JsonProperty("numberOfElements") int numberOfElements) {
//        super(content, PageRequest.of(number, 1), 10);
//    }
//
//    public CustomPageImpl(List<T> content, Pageable pageable, long total) {
//        super(content, pageable, total);
//    }
//
//    public CustomPageImpl(List<T> content) {
//        super(content);
//    }
//
//    public CustomPageImpl() {
//        super(new ArrayList<>());
//    }
//}
