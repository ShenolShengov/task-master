package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.CloudinaryService;
import bg.softuni.taskmaster.testutils.PictureTestDataUtils;
import bg.softuni.taskmaster.testutils.UserTestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class UserModificationControllerIT {

    public static final String TEST_USERNAME = "testUser";
    public static final String TEST_EMAIL = "test@gmail.com";
    public static final String TEST_FULL_NAME = "Test user";
    public static final int TEST_AGE = 20;
    private static final String TEST_NEW_EMAIL = "test_test@gmail.com";
    private static final String TEST_NEW_USERNAME = "testEditedUser";
    public static final String TEST_NEW_FULL_NAME = "User test";
    public static final int TEST_NEW_AGE = 25;
    public static final String NEW_PROFILE_PICTURE_PUBLIC_ID = "updatePicture";
    public static final String NEW_PROFILE_PICTURE_URL = "https://someUrl.com";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestDataUtils userTestDataUtils;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private CloudinaryService mokcCloudinaryService;

    @BeforeEach
    void setUp() {
        userTestDataUtils.saveTestUser(TEST_USERNAME, TEST_EMAIL,
                TEST_FULL_NAME, TEST_AGE, false);
    }

    @AfterEach
    void tearDown() {
        userTestDataUtils.clearDB();
    }

    @Test
    @WithMockUser(TEST_USERNAME)
    public void test_ProfileView() throws Exception {
        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists("profileData"))
                .andExpect(model().attribute("profileData",
                        hasProperty("username", equalTo(TEST_USERNAME))))
                .andExpect(model().attribute("profileData",
                        hasProperty("email", equalTo(TEST_EMAIL))))
                .andExpect(model().attribute("profileData",
                        hasProperty("fullName", equalTo(TEST_FULL_NAME))))
                .andExpect(model().attribute("profileData",
                        hasProperty("age", is(TEST_AGE))));

    }

    @Test
    @WithMockUser(TEST_USERNAME)
    public void test_Edit() throws Exception {
        when(mokcCloudinaryService
                .uploadPicture(ArgumentMatchers.any(MultipartFile.class), ArgumentMatchers.anyString()))
                .thenReturn(NEW_PROFILE_PICTURE_PUBLIC_ID);
        when(mokcCloudinaryService
                .getUrl(ArgumentMatchers.anyString()))
                .thenReturn(NEW_PROFILE_PICTURE_URL);

        mockMvc.perform(multipart(HttpMethod.PUT, "/users/edit")
                        .file(PictureTestDataUtils.getMultipartPicture())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(getValidUserEditFormFields()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
        Optional<User> editedUserOptional = userRepository.findByUsername(TEST_NEW_USERNAME);
        assertTrue(editedUserOptional.isPresent());
        User editedUser = editedUserOptional.get();
        assertEquals(TEST_NEW_EMAIL, editedUser.getEmail());
        assertEquals(TEST_NEW_FULL_NAME, editedUser.getFullName());
        assertEquals(TEST_NEW_AGE, editedUser.getAge());
        assertEquals(NEW_PROFILE_PICTURE_PUBLIC_ID, editedUser.getProfilePicture().getPublicId());
        assertEquals(NEW_PROFILE_PICTURE_URL, editedUser.getProfilePicture().getUrl());
    }

    @Test
    @WithMockUser(TEST_USERNAME)
    public void test_Edit_With_NotValid_Data() throws Exception {
        mockMvc.perform(multipart(HttpMethod.PUT, "/users/edit")
                        .file(PictureTestDataUtils.getMultipartPicture())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(getNotValidUserEditFormFields()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/users/profile"))
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attributeExists("profileData",
                        "org.springframework.validation.BindingResult.profileData"));
        Optional<User> editedUserOptional = userRepository.findByUsername(TEST_USERNAME);
        assertTrue(editedUserOptional.isPresent());
    }

    private MultiValueMap<String, String> getNotValidUserEditFormFields() {
        MultiValueMap<String, String> map = getValidUserEditFormFields();
        map.add("age", "-2");
        map.add("username", "S");
        return map;
    }

    private MultiValueMap<String, String> getValidUserEditFormFields() {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", TEST_NEW_USERNAME);
        map.add("email", TEST_NEW_EMAIL);
        map.add("fullName", TEST_NEW_FULL_NAME);
        map.add("age", String.valueOf(TEST_NEW_AGE));
        return map;
    }
}