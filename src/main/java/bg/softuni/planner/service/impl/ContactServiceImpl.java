package bg.softuni.planner.service.impl;

import bg.softuni.planner.model.dto.ContactUsDTO;
import bg.softuni.planner.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ContactServiceImpl implements ContactService {


    private final RestClient restClient;

    public ContactServiceImpl(@Qualifier("mail-rest-client") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void sendMail(ContactUsDTO contactUsDTO) {
        restClient.post()
                .uri("/send")
                .body(contactUsDTO)
                .retrieve();
    }
}
