package practicespringboot.notesapplicationonline.note;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import practicespringboot.notesapplicationonline.note.utils.IdWorker;
import practicespringboot.notesapplicationonline.system.exception.ObjectNotFoundException;
import practicespringboot.notesapplicationonline.system.exception.UserNotFoundException;
import practicespringboot.notesapplicationonline.user.Users;
import practicespringboot.notesapplicationonline.user.UsersRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteService {
    private final NoteRepository noteRepository;
    private final UsersRepository usersRepository;
    private final IdWorker idWorker;

    public NoteService(NoteRepository noteRepository, UsersRepository usersRepository, IdWorker idWorker) {
        this.noteRepository = noteRepository;
        this.usersRepository = usersRepository;
        this.idWorker = idWorker;
    }

    public Note createNoteById(Note newNote, Integer userId) {
        Users owner = this.usersRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        newNote.setId(String.valueOf(idWorker.nextId()));
        newNote.setTitle(newNote.getTitle() != null ? newNote.getTitle() : "New Title");
        newNote.setDescription(newNote.getDescription() != null ? newNote.getDescription() : "New Description");
        newNote.setDate(new Date());
        newNote.setOwner(owner);

        return this.noteRepository.save(newNote);
    }

    public Note findById(String noteId) {
        return noteRepository
                .findById(noteId)
                .orElseThrow(() -> new ObjectNotFoundException(Note.class, noteId));
    }

    public List<Note> findAllById(Integer ownerId) {
        return noteRepository
                .findAllByOwner_Id(ownerId)
                .orElseThrow(() -> new ObjectNotFoundException(List.of(Note.class), ownerId));
    }

    public Note updateNoteById(String noteId, Note updateNote) {
        Note foundNote = noteRepository
                .findById(noteId)
                .orElseThrow(() -> new ObjectNotFoundException(Note.class, noteId));

        if(updateNote.getTitle() != null)
            foundNote.setTitle(updateNote.getTitle());

        if(updateNote.getDescription() != null)
            foundNote.setDescription(updateNote.getDescription());

        foundNote.setDate(new Date());

        return noteRepository.save(foundNote);
    }

    public void deleteNoteById(String noteId) {
        Note foundNote = noteRepository
                .findById(noteId)
                .orElseThrow(() -> new ObjectNotFoundException(Note.class, noteId));

        noteRepository.delete(foundNote);
    }

}
