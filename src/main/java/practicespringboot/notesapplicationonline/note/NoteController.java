package practicespringboot.notesapplicationonline.note;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practicespringboot.notesapplicationonline.system.Result;

@RestController
@RequestMapping("${api.endpoint.base-url}/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    @GetMapping("/findNoteById/{noteId}")
    public Result findNoteById(@PathVariable String noteId) {
        return null;
    }

}
