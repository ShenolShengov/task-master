package bg.sofuni.mailsender.web.controller;

import bg.sofuni.mailsender.enity.MailHistory;
import bg.sofuni.mailsender.repository.MailHistoryRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static bg.sofuni.mailsender.dto.enums.EmailTemplate.CONTACT_US;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MailControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MailHistoryRepository mailHistoryRepository;

    @BeforeEach
    void setUp() {
        mailHistoryRepository.save(getTodayMailHistory());
        mailHistoryRepository.save(getTodayMailHistory());
        mailHistoryRepository.save(getMailHistoryBeforeDays(1));
        mailHistoryRepository.save(getMailHistoryBeforeDays(1));
        mailHistoryRepository.save(getMailHistoryBeforeDays(7));
        mailHistoryRepository.save(getMailHistoryBeforeDays(5));
        mailHistoryRepository.save(getMailHistoryBeforeDays(24));
        mailHistoryRepository.save(getMailHistoryBeforeDays(30));
        mailHistoryRepository.save(getMailHistoryBeforeDays(120));
    }

    @AfterEach
    void tearDown() {
        mailHistoryRepository.deleteAll();
    }


    @Test
    public void test_deleteHistory() throws Exception {
        mockMvc.perform(delete("/api/history"))
                .andExpect(status().isNoContent());
        assertEquals(0, mailHistoryRepository.count());
    }

    @Test
    public void test_deleteOldHistory() throws Exception {
        mockMvc.perform(delete("/api/history"))
                .andExpect(status().isNoContent());
        assertEquals(0, mailHistoryRepository.count());
    }

    @Test
    public void test_sendWithValidPayload() throws Exception {
        mailHistoryRepository.deleteAll();
        mockMvc.perform(post("/api/send")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getValidTestPayloadJson()))
                .andExpect(status().isOk());
        assertEquals(1, mailHistoryRepository.count());
    }

    @Test
    public void test_sendWithInValidPayload() throws Exception {
        mockMvc.perform(post("/api/send")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_sendWithInValidParams() throws Exception {
        mockMvc.perform(post("/api/send")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getTestPayloadWithInvalidParamsJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.reason").value("Invalid params"));
    }


    @Test
    public void test_mailHistoryOnBadFiltering() throws Exception {
        mockMvc.perform(get("/api/history")
                        .queryParam("sort", "wrongValue"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_MailHistoryForToday() throws Exception {
        MvcResult result = getHistory("today");
        String body = result.getResponse().getContentAsString();
        assertEquals(2, (int) JsonPath.read(body, "$.content.length()"));

        Optional<MailHistory> firstFound = getMailHistoryFromBody(body, 0);
        Optional<MailHistory> secondFound = getMailHistoryFromBody(body, 1);

        assertTrue(firstFound.isPresent());
        assertTrue(secondFound.isPresent());
        assertMailHistoryEquals(getTodayMailHistory(), firstFound.get());
        assertMailHistoryEquals(getTodayMailHistory(), secondFound.get());
    }

    @Test
    public void test_MailHistoryForYesterday() throws Exception {
        MvcResult result = getHistory("yesterday");
        String body = result.getResponse().getContentAsString();
        assertEquals(2, (int) JsonPath.read(body, "$.content.length()"));

        Optional<MailHistory> firstFound = getMailHistoryFromBody(body, 0);
        Optional<MailHistory> secondFound = getMailHistoryFromBody(body, 1);

        assertTrue(firstFound.isPresent());
        assertTrue(secondFound.isPresent());
        assertMailHistoryEquals(getMailHistoryBeforeDays(1), firstFound.get());
        assertMailHistoryEquals(getMailHistoryBeforeDays(1), secondFound.get());

    }

    @Test
    public void test_MailHistoryFor7Days() throws Exception {
        MvcResult result = getHistory("7-days");
        String body = result.getResponse().getContentAsString();
        assertEquals(6, (int) JsonPath.read(body, "$.content.length()"));

        Optional<MailHistory> firstFound = getMailHistoryFromBody(body, 0);
        Optional<MailHistory> secondFound = getMailHistoryFromBody(body, 5);

        assertTrue(firstFound.isPresent());
        assertTrue(secondFound.isPresent());
        assertMailHistoryEquals(getTodayMailHistory(), firstFound.get());
        assertMailHistoryEquals(getMailHistoryBeforeDays(7), secondFound.get());

    }

    @Test
    public void test_MailHistoryFor30Days() throws Exception {
        MvcResult result = getHistory("30-days");
        String body = result.getResponse().getContentAsString();
        assertEquals(8, (int) JsonPath.read(body, "$.content.length()"));

        Optional<MailHistory> firstFound = getMailHistoryFromBody(body, 0);
        Optional<MailHistory> secondFound = getMailHistoryFromBody(body, 7);

        assertTrue(firstFound.isPresent());
        assertTrue(secondFound.isPresent());
        assertMailHistoryEquals(getTodayMailHistory(), firstFound.get());
        assertMailHistoryEquals(getMailHistoryBeforeDays(30), secondFound.get());

    }

    @Test
    public void test_MailHistoryAll() throws Exception {
        MvcResult result = getHistory("all");
        String body = result.getResponse().getContentAsString();
        assertEquals(9, (int) JsonPath.read(body, "$.content.length()"));

        Optional<MailHistory> firstFound = getMailHistoryFromBody(body, 0);
        Optional<MailHistory> secondFound = getMailHistoryFromBody(body, 8);

        assertTrue(firstFound.isPresent());
        assertTrue(secondFound.isPresent());
        assertMailHistoryEquals(getTodayMailHistory(), firstFound.get());
        assertMailHistoryEquals(getMailHistoryBeforeDays(120), secondFound.get());

    }

    @Test
    public void test_hasHistory() throws Exception {
        String body = getHasHistory().getContentAsString();
        assertEquals("true", body);
        mailHistoryRepository.deleteAll();
        body = getHasHistory().getContentAsString();
        assertEquals("false", body);
    }

    private MockHttpServletResponse getHasHistory() throws Exception {
        return mockMvc.perform(get("/api/hasHistory"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    private Optional<MailHistory> getMailHistoryFromBody(String body, int index) {
        int foundId = JsonPath.read(body, "$.content[" + index + "].id");
        return mailHistoryRepository.findById((long) foundId);
    }

    private MvcResult getHistory(String filterByDate) throws Exception {
        return mockMvc
                .perform(get("/api/history")
                        .queryParam("filterByDate", filterByDate))
                .andExpect(status().isOk())
                .andReturn();
    }


    private void assertMailHistoryEquals(MailHistory expected, MailHistory actual) {
        assertEquals(expected.getFrom(), actual.getFrom());
        assertEquals(expected.getTo(), actual.getTo());
        assertEquals(expected.getTemplate(), actual.getTemplate());
        assertEquals(expected.getDate(), actual.getDate());
    }


    private MailHistory getMailHistoryBeforeDays(long amount) {
        return new MailHistory("fromTest@me.com", "fromTest@me.com",
                LocalDate.now().minusDays(amount).atStartOfDay().toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.DAYS),
                CONTACT_US);
    }

    private MailHistory getTodayMailHistory() {
        return getMailHistoryBeforeDays(0);
    }

    private String getValidTestPayloadJson() {
        return """
                {
                    "from" : "shenolShengov41@gmail.com",
                    "to" : "shenolShengov41@gmail.com",
                    "subject" : "Test email",
                    "template" : "CONTACT_US",
                    "params": {
                        "EMAIL": "test@gmail.com",
                        "MESSAGE": "Test message"
                    }
                }
                """;
    }

    private String getTestPayloadWithInvalidParamsJson() {
        return """
                {
                    "from" : "shenolShengov41@gmail.com",
                    "to" : "shenolShengov41@gmail.com",
                    "subject" : "Test email",
                    "template" : "CONTACT_US",
                    "params": {
                        "EMAIL": "test@gmail.com"
                    }
                }
                """;
    }
}
