package practicespringboot.notesapplicationonline.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import practicespringboot.notesapplicationonline.system.StatusCode;
import practicespringboot.notesapplicationonline.system.exception.UserAlreadyExistException;
import practicespringboot.notesapplicationonline.user.dto.UsersDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {
    @Value("${api.endpoint.base-url}/users")
    String baseUrl;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UsersService usersService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreateUserSuccess() throws Exception {
        Users sampleUser = new Users();
        sampleUser.setEmail("sampleUser@email.com");
        sampleUser.setFirstName("Aaron Joseph");
        sampleUser.setPassword("@Password1");

        String json = objectMapper.writeValueAsString(sampleUser);

        //Given
        given(usersService.save(Mockito.any(Users.class))).willReturn(sampleUser);

        //When and Then
        this.mockMvc.perform(post(baseUrl + "/register").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Create User Success"))
                .andExpect(jsonPath("$.data.email").value("sampleUser@email.com"));
    }

    @Test
    void testCreateUserAlreadyExist() throws Exception {
        Users sampleUser = new Users();
        sampleUser.setEmail("existingEmail@email.com");
        sampleUser.setFirstName("Aaron Joseph");
        sampleUser.setPassword("@Password1");

        String json = objectMapper.writeValueAsString(sampleUser);

        //Given
        given(usersService.save(Mockito.any(Users.class))).willThrow(new UserAlreadyExistException("existingEmail@email.com"));

        //When and Then
        this.mockMvc.perform(post(baseUrl + "/register").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.FORBIDDEN))
                .andExpect(jsonPath("$.message").value("User already exist with email existingEmail@email.com"));
    }
}