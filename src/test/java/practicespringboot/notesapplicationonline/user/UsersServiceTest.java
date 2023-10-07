package practicespringboot.notesapplicationonline.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import practicespringboot.notesapplicationonline.system.exception.UserAlreadyExistException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {
    @Mock
    UsersRepository usersRepository;
    @InjectMocks
    UsersService usersService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSaveSuccess() {
        Users sampleUser = new Users();
        sampleUser.setId(1);
        sampleUser.setEmail("sampleUser@email.com");
        sampleUser.setFirstName("sampleFirstName");
        sampleUser.setPassword("@Password1");
        sampleUser.setRole(UsersRole.USER);
        sampleUser.setEnabled(true);

        //Given
        given(usersRepository.save(sampleUser)).willReturn(sampleUser);

        //When
        Users savedUser = usersService.save(sampleUser);

        //Then
        assertThat(savedUser.getEmail()).isEqualTo("sampleUser@email.com");
        verify(usersRepository, times(1)).save(sampleUser);
    }

    @Test
    void testSaveAlreadyExist() {
        Users sampleUser = new Users();
        sampleUser.setEmail("existingEmail@email.com");

        //Given
        given(usersRepository.findByEmail(Mockito.any(String.class))).willThrow(new UserAlreadyExistException("existingEmail@email.com"));

        //When
        Throwable thrown = catchThrowable(() -> {
           usersService.save(sampleUser);
        });

        assertThat(thrown)
                .isInstanceOf(UserAlreadyExistException.class)
                .hasMessage("User already exist with email existingEmail@email.com");
        verify(usersRepository, times(1)).findByEmail("existingEmail@email.com");
    }
}