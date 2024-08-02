package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.ContactUsDTO;
import bg.softuni.taskmaster.service.ContactService;
import bg.softuni.taskmaster.utils.UserTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ContactControllerIT {

    public static final String CONTACT_US_TITLE = "[Contact us]";
    public static final String CONTACT_US_EMAIL = "contactUs@me.com";
    public static final String CONTACT_US_MESSAGE = "contact us test message";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestUtils userTestUtils;

    @MockBean
    private ContactService mockContactService;

    @Captor
    private ArgumentCaptor<ContactUsDTO> contactUsDTOCaptor = ArgumentCaptor.forClass(ContactUsDTO.class);

    @BeforeEach
    void setUp() {
        userTestUtils.getOrSaveTestUserFromDB("testUser", "test@gmail.com");
    }

    @AfterEach
    void tearDown() {
        userTestUtils.clearDB();
    }

    @Test
    public void test_ContactsView() throws Exception {
        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(view().name("contacts"))
                .andExpect(model().attributeExists("contactData"));
    }

    @Test
    public void test_ContactUs_With_NotLoggedUser() throws Exception {
        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(new LinkedMultiValueMap<>()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("testUser")
    public void test_ContactUs_With_NotValid_ContactUsDTO() throws Exception {
        mockMvc.perform(post("/contacts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(new LinkedMultiValueMap<>()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/contacts"))
                .andExpect(flash().attributeCount(3))
                .andExpect(flash().attributeExists("invalidData", "contactData",
                        "org.springframework.validation.BindingResult.contactData"));
    }

    @Test
    @WithMockUser("testUser")
    public void test_ContactUs_With_Valid_ContactUsDTO() throws Exception {

        doNothing().when(mockContactService).contactUs(any(ContactUsDTO.class));
        mockMvc.perform(post("/contacts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(getTestAddContactUsDTOFormFields()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attributeExists("mailSent"));
        verify(mockContactService).contactUs(contactUsDTOCaptor.capture());
        ContactUsDTO actual = contactUsDTOCaptor.getValue();
        assertEquals(CONTACT_US_TITLE, actual.getTitle());
        assertEquals(CONTACT_US_EMAIL, actual.getEmail());
        assertEquals(CONTACT_US_MESSAGE, actual.getMessage());
    }

    private MultiValueMap<String, String> getTestAddContactUsDTOFormFields() {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("title", CONTACT_US_TITLE);
        map.add("email", CONTACT_US_EMAIL);
        map.add("message", CONTACT_US_MESSAGE);
        return map;
    }
}