package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.AnswerDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.AnswerRepository;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.utils.AnswerTestUtils;
import bg.softuni.taskmaster.utils.QuestionTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static bg.softuni.taskmaster.utils.AnswerTestUtils.getTestAnswerDTO;
import static bg.softuni.taskmaster.utils.UserTestUtils.getTestUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerServiceImplTest {


    public static final long TEST_QUESTION_ID = 1L;
    public static final long ANSWER_TEST_ID = 1L;
    private AnswerServiceImpl answerServiceToTest;
    @Mock
    private AnswerRepository mockAnswerRepository;
    @Mock
    private UserHelperService mockUserHelperService;

    @Mock
    private QuestionRepository mockQuestionRepository;

    @Mock
    private ApplicationEventPublisher mockPublisher;

    @Captor
    public ArgumentCaptor<Answer> answerCaptor = ArgumentCaptor.forClass(Answer.class);

    @Captor
    public ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);

    private User testUser;

    private Question testQuestion;

    @BeforeEach
    void setUp() {
        this.answerServiceToTest = new AnswerServiceImpl(mockAnswerRepository, new ModelMapper(),
                mockUserHelperService, mockQuestionRepository, mockPublisher);
        testUser = getTestUser("testUser", "test@me.com", false);
        testQuestion = QuestionTestUtils.getTestQuestion(testUser, false);

    }

    @Test
    public void test_Answer() {
        doNothing().when(mockPublisher).publishEvent(any(ApplicationEvent.class));
        when(mockUserHelperService.getLoggedUser())
                .thenReturn(testUser);
        when(mockQuestionRepository.getReferenceById(1L))
                .thenReturn(testQuestion
                );

        AnswerDTO answerDTO = getTestAnswerDTO();
        answerServiceToTest.answer(answerDTO, TEST_QUESTION_ID);
        verify(mockAnswerRepository).save(answerCaptor.capture());
        Answer saveAnswer = answerCaptor.getValue();
        assertEquals(answerDTO.getCode(), saveAnswer.getCode());
        assertEquals(answerDTO.getDescription(), saveAnswer.getDescription());
        verify(mockPublisher).publishEvent(any(ApplicationEvent.class));
    }

    @Test
    public void test_IsActualUser() {
        when(mockAnswerRepository.findById(ANSWER_TEST_ID))
                .thenReturn(Optional.of(AnswerTestUtils.getTestAnswer(testUser, testQuestion, false)));
        when(mockUserHelperService.getUsername()).thenReturn(testUser.getUsername());
        assertTrue(answerServiceToTest.isActualUser(ANSWER_TEST_ID));

        when(mockUserHelperService.getUsername()).thenReturn("otherTestUser");
        assertFalse(answerServiceToTest.isActualUser(ANSWER_TEST_ID));

    }

    @Test
    public void test_Delete() {

        answerServiceToTest.delete(TEST_QUESTION_ID);
        verify(mockAnswerRepository).deleteById(longCaptor.capture());
        assertEquals(TEST_QUESTION_ID, longCaptor.getValue());
    }
}