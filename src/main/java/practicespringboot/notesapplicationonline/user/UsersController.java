package practicespringboot.notesapplicationonline.user;

import org.springframework.web.bind.annotation.RestController;
import practicespringboot.notesapplicationonline.note.NoteService;

@RestController
public class UsersController {

    private final NoteService noteService;

    public UsersController(NoteService noteService) {
        this.noteService = noteService;
    }
}
