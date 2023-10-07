package practicespringboot.notesapplicationonline.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import practicespringboot.notesapplicationonline.note.NoteService;
import practicespringboot.notesapplicationonline.system.Result;

@RestController
@CrossOrigin
@RequestMapping("${api.endpoint.base-url}/users")
public class UsersController {

    private final NoteService noteService;

    public UsersController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/register")
    public Result createUser(@Valid @RequestBody Users newUser) {
        return null;
    }
}
