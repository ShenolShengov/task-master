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
import static bg.softuni.taskmaster.utils.UserTestUtils.getMockUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerServiceImplTest {


    public static final long TEST_QUESTION_ID = 1L;
    public static final long ANSWER_TEST_ID = 1L;
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private UserHelperService userHelperService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private ApplicationEventPublisher publisher;
    private AnswerServiceImpl answerService;

    @Captor
    public ArgumentCaptor<Answer> answerCaptor = ArgumentCaptor.forClass(Answer.class);

    @Captor
    public ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);

    private User mockUser;

    private Question mockQuestion;

    @BeforeEach
    void setUp() {
        this.answerService = new AnswerServiceImpl(answerRepository, new ModelMapper(),
                userHelperService, questionRepository, publisher);
        mockUser = getMockUser("mockUser", "mock@me.com", false);
        mockQuestion = QuestionTestUtils.getTestQuestion(mockUser, false);

    }

    @Test
    public void test_Answer() {
        doNothing().when(publisher).publishEvent(any(ApplicationEvent.class));
        when(userHelperService.getLoggedUser())
                .thenReturn(mockUser);
        when(questionRepository.getReferenceById(1L))
                .thenReturn(mockQuestion
                );

        AnswerDTO answerDTO = getTestAnswerDTO();
        answerService.answer(answerDTO, TEST_QUESTION_ID);
        verify(answerRepository).save(answerCaptor.capture());
        Answer saveAnswer = answerCaptor.getValue();
        assertEquals(answerDTO.getCode(), saveAnswer.getCode());
        assertEquals(answerDTO.getDescription(), saveAnswer.getDescription());
        verify(publisher).publishEvent(any(ApplicationEvent.class));
    }

    @Test
    public void test_IsActualUser() {
        when(answerRepository.findById(ANSWER_TEST_ID))
                .thenReturn(Optional.of(AnswerTestUtils.getTestAnswer(mockUser, mockQuestion, false)));
        when(userHelperService.getUsername()).thenReturn(mockUser.getUsername());
        assertTrue(answerService.isActualUser(ANSWER_TEST_ID));

        when(userHelperService.getUsername()).thenReturn("otherMockUser");
        assertFalse(answerService.isActualUser(ANSWER_TEST_ID));

    }

    @Test
    public void test_Delete() {

        answerService.delete(TEST_QUESTION_ID);
        verify(answerRepository).deleteById(longCaptor.capture());
        assertEquals(TEST_QUESTION_ID, longCaptor.getValue());
    }
}