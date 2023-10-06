package practicespringboot.notesapplicationonline.note;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import practicespringboot.notesapplicationonline.note.utils.IdWorker;
import practicespringboot.notesapplicationonline.system.exception.ObjectNotFoundException;
import practicespringboot.notesapplicationonline.system.exception.UserNotFoundException;
import practicespringboot.notesapplicationonline.user.Users;
import practicespringboot.notesapplicationonline.user.UsersRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    NoteRepository noteRepository;
    @Mock
    UsersRepository usersRepository;
    @InjectMocks
    NoteService noteService;
    @Mock
    IdWorker idWorker;

    List<Note> notes;
    Users user1;

    @BeforeEach
    void setUp() {
        user1 = new Users();
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
    void testFindByIdSuccess() {
        Note note1 = notes.get(0);
        //Given
        given(this.noteRepository.findById("1")).willReturn(Optional.of(note1));

        //When
        Note returnedNote = this.noteService.findById("1");

        //Then
        assertThat(returnedNote.getId()).isEqualTo(note1.getId());
        assertThat(returnedNote.getTitle()).isEqualTo(note1.getTitle());
        assertThat(returnedNote.getDescription()).isEqualTo(note1.getDescription());
        assertThat(returnedNote.getDate()).isEqualTo(note1.getDate());
        assertThat(returnedNote.getOwner()).isEqualTo(note1.getOwner());
        verify(this.noteRepository, times(1)).findById("1");
    }

    @Test
    void testFindByIdNotFound() {
        //Given
        given(this.noteRepository.findById("-1")).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(() -> {
            this.noteService.findById("-1");
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find Note with Id -1");
        verify(this.noteRepository, times(1)).findById("-1");

    }

    @Test
    void testFindAllNotesByOwnerIdSuccess() {
        //Given
        given(this.noteRepository.findAllByOwner_Id(1)).willReturn(Optional.of(notes));

        //When
        List<Note> returnedNotes = this.noteService.findAllById(1);

        //Then
        assertThat(returnedNotes.size()).isEqualTo(this.notes.size());
    }

    @Test
    void testFindAllNotesByOwnerIdNotFound() {
        //Given
        given(this.noteRepository.findAllByOwner_Id(-1)).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(() -> {
            this.noteService.findAllById(-1);
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find List of Note with Id -1");
        verify(this.noteRepository, times(1)).findAllByOwner_Id(-1);

    }

    @Test
    void testCreateNoteByUserIdSuccess() {
        Note note2 = new Note();
        note2.setTitle("Title2");
        note2.setDescription("Description2");

        //Given
        given(idWorker.nextId()).willReturn(123456L);
        given(this.usersRepository.findById(1)).willReturn(Optional.of(user1));
        given(this.noteRepository.save(note2)).willReturn(note2);

        //When
        Note savedNote = this.noteService.createNoteById(note2,1);

        //Then
        assertThat(savedNote.getTitle()).isEqualTo(note2.getTitle());
        assertThat(savedNote.getDescription()).isEqualTo(note2.getDescription());
        assertThat(savedNote.getOwner()).isEqualTo(user1);
        verify(this.usersRepository, times(1)).findById(1);
        verify(this.noteRepository, times(1)).save(note2);
    }

    @Test
    void testCreateNoteByUserIdNotFound() {
        //Given
        given(this.usersRepository.findById(-1)).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(() -> {
           noteService.createNoteById(Mockito.any(Note.class), -1);
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Could not find User with Id -1");
        verify(this.usersRepository, times(1)).findById(-1);
    }
}