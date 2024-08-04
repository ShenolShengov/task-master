package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.testutils.UserTestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
    private UserTestDataUtils userTestDataUtils;


    @BeforeEach
    void setUp() {
        userTestDataUtils.saveTestUser("testUser", "test@gmail.com");
    }

    @AfterEach
    void tearDown() {
        userTestDataUtils.clearDB();
    }

    @Test
    public void test_ContactsView() throws Exception {
        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(view().name("contacts"))
                .andExpect(model().attributeExists("contactData"));
    }

    @Test
    @WithMockUser("testUser")
    public void test_ContactUs() throws Exception {
        mockMvc.perform(post("/contacts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(getTestAddContactUsDTOFormFields()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attributeExists("mailSent"));

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
    public void test_ContactUs_With_NotLoggedUser() throws Exception {
        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(new LinkedMultiValueMap<>()))
                .andExpect(status().isForbidden());
    }

    private MultiValueMap<String, String> getTestAddContactUsDTOFormFields() {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("title", CONTACT_US_TITLE);
        map.add("email", CONTACT_US_EMAIL);
        map.add("message", CONTACT_US_MESSAGE);
        return map;
    }
}