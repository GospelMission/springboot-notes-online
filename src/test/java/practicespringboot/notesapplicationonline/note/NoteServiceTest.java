package practicespringboot.notesapplicationonline.note;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import practicespringboot.notesapplicationonline.system.exception.ObjectNotFoundException;
import practicespringboot.notesapplicationonline.user.Users;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    NoteRepository noteRepository;
    @InjectMocks
    NoteService noteService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //Given
        Users user1 = new Users();
        user1.setId(1);
        user1.setFirstName("Aaron Joseph");
        user1.setMiddleName("Nocon");
        user1.setLastName("Carillo");
        user1.setEnabled(true);
        user1.setRole("admin");

        Note note1 = new Note();
        note1.setId("1");
        note1.setTitle("Title1");
        note1.setDescription("Description1");
        note1.setDate(new Date(1));
        note1.setOwner(user1);

        given(noteRepository.findById("1")).willReturn(Optional.of(note1));

        //When
        Note returnedNote = noteService.findById("1");

        //Then
        assertThat(returnedNote.getId()).isEqualTo(note1.getId());
        assertThat(returnedNote.getTitle()).isEqualTo(note1.getTitle());
        assertThat(returnedNote.getDescription()).isEqualTo(note1.getDescription());
        assertThat(returnedNote.getDate()).isEqualTo(note1.getDate());
        assertThat(returnedNote.getOwner()).isEqualTo(note1.getOwner());
        verify(noteRepository, times(1)).findById("1");
    }

    @Test
    void testFindByIdNotFound() {
        //Given
        given(noteRepository.findById("-1")).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(() -> {
            noteService.findById("-1");
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find Note with Id -1");
        verify(noteRepository, times(1)).findById("-1");

    }
}