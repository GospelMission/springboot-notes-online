package practicespringboot.notesapplicationonline.note;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practicespringboot.notesapplicationonline.note.converter.NoteToNoteDtoConverter;
import practicespringboot.notesapplicationonline.note.dto.NoteDto;
import practicespringboot.notesapplicationonline.system.Result;
import practicespringboot.notesapplicationonline.system.StatusCode;

@RestController
@RequestMapping("${api.endpoint.base-url}/notes")
public class NoteController {
    private final NoteService noteService;
    private final NoteToNoteDtoConverter noteToNoteDtoConverter;

    public NoteController(NoteService noteService, NoteToNoteDtoConverter noteToNoteDtoConverter) {
        this.noteService = noteService;
        this.noteToNoteDtoConverter = noteToNoteDtoConverter;
    }
    @GetMapping("/findNoteById/{noteId}")
    public Result findNoteById(@PathVariable String noteId) {
        Note foundNote= this.noteService.findById(noteId);
        NoteDto noteDto = noteToNoteDtoConverter.convert(foundNote);
        return new Result(true, StatusCode.SUCCESS, "FindNoteById Success", noteDto);
    }

}
