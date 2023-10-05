package practicespringboot.notesapplicationonline.note;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import practicespringboot.notesapplicationonline.system.StatusCode;
import practicespringboot.notesapplicationonline.system.exception.ObjectNotFoundException;
import practicespringboot.notesapplicationonline.user.Users;
import practicespringboot.notesapplicationonline.user.UsersRepository;
import practicespringboot.notesapplicationonline.user.UsersService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {
    @Value("${api.endpoint.base-url}/notes")
    String baseUrl;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    NoteService noteService;

    List<Note> notes;
    @BeforeEach
    void setUp() {
        Users user1 = new Users();
        user1.setId(1);
        user1.setFirstName("Aaron Joseph");
        user1.setMiddleName("Nocon");
        user1.setLastName("Carillo");
        user1.setPassword("@Password1");
        user1.setEnabled(true);
        user1.setRole("admin");

        Note note1 = new Note();
        note1.setId("1");
        note1.setTitle("Title1");
        note1.setDescription("Description1");
        note1.setDate(new Date(1));
        note1.setOwner(user1);

        this.notes = new ArrayList<>();
        this.notes.add(note1);
        user1.setNotes(notes);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindNoteByNoteIdSuccess() throws Exception {
        //Given
        given(this.noteService.findById("1")).willReturn(this.notes.get(0));

        //When and Then
        this.mockMvc.perform(get(this.baseUrl + "/findNoteByNoteId/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("FindNoteByNoteId Success"))
                .andExpect(jsonPath("$.data.id").value("1"));
    }

    @Test
    void testFindNoteByNoteIdNotFound() throws Exception {
        //Given
        given(this.noteService.findById("-1")).willThrow(new ObjectNotFoundException(Note.class, "-1"));

        //When and Then
        this.mockMvc.perform(get(this.baseUrl + "/findNoteByNoteId/-1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find Note with Id -1"));
    }
}