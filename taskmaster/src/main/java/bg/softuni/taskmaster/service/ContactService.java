package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.ContactUsDTO;

public interface ContactService {

    void sendMail(ContactUsDTO contactUsDTO);
}
