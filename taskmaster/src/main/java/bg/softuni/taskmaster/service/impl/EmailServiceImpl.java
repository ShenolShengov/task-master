package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.MailHistoryInfoDTO;
import bg.softuni.taskmaster.model.dto.PageResponseDTO;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.model.enums.EmailParam;
import bg.softuni.taskmaster.model.enums.EmailTemplate;
import bg.softuni.taskmaster.service.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
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
    public Page<MailHistoryInfoDTO> history(Instant filterByDate, Pageable pageable) {
        PageResponseDTO<MailHistoryInfoDTO> pageResponseDTO = restClient.get().uri(u -> u.path("/history")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("sort", pageable.getSort().toString().replace(": ", ","))
                        .queryParam("filterByDate", filterByDate)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return new PageImpl<>(pageResponseDTO.getContent(), pageable, pageResponseDTO.getPage().totalElements());
    }

}