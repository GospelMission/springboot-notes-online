package practicespringboot.notesapplicationonline.note;

import org.springframework.web.bind.annotation.*;
import practicespringboot.notesapplicationonline.note.converter.NoteToNoteDtoConverter;
import practicespringboot.notesapplicationonline.note.dto.NoteDto;
import practicespringboot.notesapplicationonline.system.Result;
import practicespringboot.notesapplicationonline.system.StatusCode;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/notes")
public class NoteController {
    private final NoteService noteService;
    private final NoteToNoteDtoConverter noteToNoteDtoConverter;

    public NoteController(NoteService noteService, NoteToNoteDtoConverter noteToNoteDtoConverter) {
        this.noteService = noteService;
        this.noteToNoteDtoConverter = noteToNoteDtoConverter;
    }
    @GetMapping("/findNoteByNoteId/{noteId}")
    public Result findNoteByNoteId(@PathVariable String noteId) {
        Note foundNote= this.noteService.findById(noteId);
        NoteDto noteDto = noteToNoteDtoConverter.convert(foundNote);
        return new Result(true, StatusCode.SUCCESS, "FindNoteByNoteId Success", noteDto);
    }

    @GetMapping("/findAllNotesByOwnerId/{ownerId}")
    public Result findAllNotesByOwnerId(@PathVariable Integer ownerId) {
        List<Note> returnedNotes = noteService.findAllById(ownerId);
        List<NoteDto> notesDto = returnedNotes
                .stream()
                .map(noteToNoteDtoConverter::convert)
                .toList();

        return new Result(true, StatusCode.SUCCESS, "Find All Notes By Owner Id Success", notesDto);
    }
    @PostMapping("createNoteByUserId/{userId}")
    public Result createNoteByUserId(Integer userId) {
        return null;
    }

}
