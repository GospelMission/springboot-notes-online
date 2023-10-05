package practicespringboot.notesapplicationonline.note;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import practicespringboot.notesapplicationonline.system.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note findById(String noteId) {
        return noteRepository
                .findById(noteId)
                .orElseThrow(() -> new ObjectNotFoundException(Note.class, noteId));
    }

    public List<Note> findAllByOwnerId(Integer ownerId) {
        return noteRepository
                .findAllByOwner_Id(ownerId)
                .orElseThrow(() -> new ObjectNotFoundException(List.of(Note.class), ownerId));
    }
}
