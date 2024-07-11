package bg.softuni.taskmaster;

import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.resolver.CaptorParameterResolver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskMasterApplicationTests {

	@Test
	void contextLoads() {

	}

}
