package bg.softuni.planner.service;

import bg.softuni.planner.model.dto.ContactUsDTO;

public interface ContactService {

    void sendMail(ContactUsDTO contactUsDTO);
}
