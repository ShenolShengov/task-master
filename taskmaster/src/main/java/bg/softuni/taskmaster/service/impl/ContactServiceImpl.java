package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.ContactUsDTO;
import bg.softuni.taskmaster.service.ContactService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ContactServiceImpl implements ContactService {


    private final RestClient restClient;

    public ContactServiceImpl(@Qualifier("mail-rest-client") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void contactUs(ContactUsDTO contactUsDTO) {
        restClient.post()
                .uri("/send")
                .body(contactUsDTO)
                .retrieve();
    }
}
