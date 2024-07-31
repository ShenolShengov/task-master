package bg.softuni.taskmaster;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskMasterApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    public void applicationContextTest() {
        TaskMasterApplication.main(new String[]{});
    }
}
