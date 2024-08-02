package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.MailHistoryInfoDTO;
import bg.softuni.taskmaster.model.dto.PageResponseDTO;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.model.enums.EmailTemplate;
import bg.softuni.taskmaster.utils.EmailUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.List;
import java.util.function.Function;

import static bg.softuni.taskmaster.model.enums.EmailParam.EMAIL;
import static bg.softuni.taskmaster.model.enums.EmailParam.MESSAGE;
import static bg.softuni.taskmaster.model.enums.EmailTemplate.CONTACT_US;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {

    public static final String TEST_FROM = "test@me.com";
    public static final String TEST_TO = "to@me.com";
    public static final String TEST_SUBJECT = "subject Test";
    public static final EmailTemplate TEST_EMAIL_TEMPLATE = CONTACT_US;
    public static final String TEST_EMAIL = "test email";
    public static final String TEST_MESSAGE = "test message";
    public static final int TEST_PAGE_NUMBER = 0;
    public static final int TEST_PAGE_SIZE = 2;
    public static final int TEST_TOTAL_ELEMENTS = 2;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestClient mockRestClient;

    private MailServiceImpl mailServiceToTest;

    @BeforeEach
    void setUp() {
        this.mailServiceToTest = new MailServiceImpl(mockRestClient);
    }


    @Test
    public void test_Send() {
        Payload testPayload = getTestPayload();
        mailServiceToTest.send(testPayload);
        verify(mockRestClient.post().uri("/send").body(testPayload)).retrieve();
    }


    @Test
    @SuppressWarnings("unchecked")
    public void test_History() {
        when(mockRestClient.get().uri(any(Function.class)).retrieve().body(PageResponseDTO.class))
                .thenReturn(getTestPageHistory());
        Page<MailHistoryInfoDTO> history = mailServiceToTest.history("testFilterBy", getTestPageable());
        assertEquals(2, history.getContent().size());
        assertEquals(TEST_TOTAL_ELEMENTS, history.getTotalElements());
    }

    @Test
    public void test_CreatePayload() {
        Payload actualPayload = mailServiceToTest.createPayload(TEST_FROM, TEST_TO, TEST_SUBJECT, TEST_EMAIL_TEMPLATE,
                EmailUtils.toParams(EMAIL, TEST_EMAIL, MESSAGE, TEST_MESSAGE));
        assertEquals(getTestPayload(), actualPayload);
    }

    @Test
    public void test_HasHistory() {
        when(mockRestClient.get().uri("/hasHistory").retrieve().body(Boolean.class))
                .thenReturn(true);
        assertTrue(mailServiceToTest.hasHistory());
    }

    @Test
    public void test_DeleteHistory() {
        mailServiceToTest.deleteHistory();
        verify(mockRestClient.delete().uri("/history")).retrieve();

    }

    private PageResponseDTO<MailHistoryInfoDTO> getTestPageHistory() {
        List<MailHistoryInfoDTO> history = List.of(getTestMailHistoryInfoDTO(), getTestMailHistoryInfoDTO());
        return new PageResponseDTO<>(history, new PagedModel.PageMetadata(0, 0, 2, 1));
    }

    private static PageRequest getTestPageable() {
        return PageRequest.of(TEST_PAGE_NUMBER, TEST_PAGE_SIZE);
    }

    private MailHistoryInfoDTO getTestMailHistoryInfoDTO() {
        return new MailHistoryInfoDTO(1L, TEST_EMAIL_TEMPLATE.name(), Instant.now(), TEST_FROM, TEST_TO);
    }

    private Payload getTestPayload() {
        return new Payload(TEST_FROM, TEST_TO, TEST_SUBJECT, TEST_EMAIL_TEMPLATE,
                EmailUtils.toParams(EMAIL, TEST_EMAIL, MESSAGE, TEST_MESSAGE));
    }
}
