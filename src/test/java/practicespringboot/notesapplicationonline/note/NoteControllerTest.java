package practicespringboot.notesapplicationonline.note;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;

import practicespringboot.notesapplicationonline.note.converter.NoteDtoToNoteConverter;
import practicespringboot.notesapplicationonline.note.converter.NoteToNoteDtoConverter;
import practicespringboot.notesapplicationonline.note.dto.NoteDto;
import practicespringboot.notesapplicationonline.system.StatusCode;
import practicespringboot.notesapplicationonline.system.exception.ObjectNotFoundException;
import practicespringboot.notesapplicationonline.system.exception.UserNotFoundException;
import practicespringboot.notesapplicationonline.user.Users;
import practicespringboot.notesapplicationonline.user.UsersRepository;
import practicespringboot.notesapplicationonline.user.UsersService;
import practicespringboot.notesapplicationonline.user.dto.UsersDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {
    @Value("${api.endpoint.base-url}/notes")
    String baseUrl;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
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

    @Test
    void testFindAllNotesByOwnerIdSuccess() throws Exception {

        //Given
        given(this.noteService.findAllById(1)).willReturn(notes);

        //When and Then
        this.mockMvc.perform(get(this.baseUrl + "/findAllNotesByOwnerId/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Notes By Owner Id Success"))
                .andExpect(jsonPath("$.data[0].owner.id").value(1));

    }
    @Test
    void testFindAllNotesByOwnerIdNotFound() throws Exception {
        //Given
        given(this.noteService.findAllById(-1)).willThrow(new ObjectNotFoundException(List.of(Note.class), -1));

        //When and Then
        this.mockMvc.perform(get(baseUrl + "/findAllNotesByOwnerId/-1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find List of Note with Id -1"));
    }

    @Test
    void testCreateNoteByUserIdSuccess() throws Exception {
        NoteDto noteDto = new NoteDto(
                null,
                "Title1",
                "Description1",
                null,
                null
        );
        String json = this.objectMapper.writeValueAsString(noteDto);

        Note savedNote = notes.get(0);

        //Given
        given(this.noteService.createNoteById(Mockito.any(Note.class), Mockito.any(Integer.class))).willReturn(savedNote);

        //When and Then
        this.mockMvc.perform(post(baseUrl + "/createNoteByUserId/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Create Note By User Id Success"))
                .andExpect(jsonPath("$.data.title").value(savedNote.getTitle()));

    }

    @Test
    void testCreateNoteByUserIdNotFound() throws Exception {
        NoteDto noteDto = new NoteDto(
                null,
                "Title1",
                "Description1",
                null,
                null
        );

        String json = this.objectMapper.writeValueAsString(noteDto);

        //Given
        given(this.noteService.createNoteById(Mockito.any(Note.class), Mockito.any(Integer.class))).willThrow(new UserNotFoundException(1));

        //When and Then
        this.mockMvc.perform(post(baseUrl + "/createNoteByUserId/1")
                        .contentType(MediaType.APPLICATION_JSON).content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find User with Id 1"));
    }

    @Test
    void testUpdateNoteByNoteIdSuccess() throws Exception {
        NoteDto noteDto = new NoteDto(null, "New Title", "New Description", null,null);
        String json = objectMapper.writeValueAsString(noteDto);

        Users sampleUser = new Users();
        sampleUser.setId(1);
        sampleUser.setFirstName("Aaron Joseph");
        sampleUser.setMiddleName("Nocon");
        sampleUser.setLastName("Carillo");
        sampleUser.setPassword("@Password1");
        sampleUser.setEnabled(true);
        sampleUser.setRole("admin");

        Note updatedNote = new Note();
        updatedNote.setId("1");
        updatedNote.setTitle("New Title");
        updatedNote.setDescription("New Description");
        updatedNote.setDate(new Date(1));
        updatedNote.setOwner(sampleUser);

        //Given
        given(this.noteService.updateNoteById(Mockito.any(String.class), Mockito.any(Note.class))).willReturn(updatedNote);

        //When and Then
        this.mockMvc.perform(put(baseUrl + "/updateNoteByNoteId/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Note By User Id Success"))
                .andExpect(jsonPath("$.data.title").value("New Title"));

    }

    @Test
    void testUpdateNoteByNoteIdNotFound() throws Exception {
        NoteDto noteDto = new NoteDto(null, "New Title", "New Description", null,null);
        String json = objectMapper.writeValueAsString(noteDto);

        //Given
        given(this.noteService.updateNoteById(Mockito.any(String.class), Mockito.any(Note.class))).willThrow(new ObjectNotFoundException(Note.class, -1));

        //When and Then
        this.mockMvc.perform(put(baseUrl + "/updateNoteByNoteId/-1")
                        .contentType(MediaType.APPLICATION_JSON).content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find Note with Id -1"));
    }

    @Test
    void testDeleteNoteByNoteIdSuccess() throws Exception {
        //Given
        doNothing().when(noteService).deleteNoteById("1");

        //When and Then
        this.mockMvc.perform(delete(baseUrl + "/deleteNoteByNoteId/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Note By Note Id Success"));
    }

    @Test
    void testDeleteNoteByNoteIdNotFound() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException(Note.class, "-1")).when(noteService).deleteNoteById("-1");

        //When and Then
        this.mockMvc.perform(delete(baseUrl + "/deleteNoteByNoteId/-1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find Note with Id -1"));
    }
}