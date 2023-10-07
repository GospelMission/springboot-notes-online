package practicespringboot.notesapplicationonline.note;

import org.springframework.web.bind.annotation.*;
import practicespringboot.notesapplicationonline.note.converter.NoteDtoToNoteConverter;
import practicespringboot.notesapplicationonline.note.converter.NoteToNoteDtoConverter;
import practicespringboot.notesapplicationonline.note.dto.NoteDto;
import practicespringboot.notesapplicationonline.system.Result;
import practicespringboot.notesapplicationonline.system.StatusCode;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/notes")
public class NoteController {
    private final NoteService noteService;
    private final NoteToNoteDtoConverter noteToNoteDtoConverter;
    private final NoteDtoToNoteConverter noteDtoToNoteConverter;

    public NoteController(NoteService noteService, NoteToNoteDtoConverter noteToNoteDtoConverter, NoteDtoToNoteConverter noteDtoToNoteConverter) {
        this.noteService = noteService;
        this.noteToNoteDtoConverter = noteToNoteDtoConverter;
        this.noteDtoToNoteConverter = noteDtoToNoteConverter;
    }
    @PostMapping("/createNoteByUserId/{userId}")
    public Result createNoteByUserId(@RequestBody NoteDto noteDto, @PathVariable Integer userId) {
        Note newNote = this.noteDtoToNoteConverter.convert(noteDto);
        Note savedNote = this.noteService.createNoteById(newNote, userId);
        NoteDto savedNoteDto = this.noteToNoteDtoConverter.convert(savedNote);
        return new Result(true, StatusCode.SUCCESS, "Create Note By User Id Success", savedNoteDto);
    }
    @GetMapping("/findNoteByNoteId/{noteId}")
    public Result findNoteByNoteId(@PathVariable String noteId) {
        Note foundNote= this.noteService.findById(noteId);
        NoteDto noteDto = this.noteToNoteDtoConverter.convert(foundNote);
        return new Result(true, StatusCode.SUCCESS, "FindNoteByNoteId Success", noteDto);
    }
    @GetMapping("/findAllNotesByOwnerId/{ownerId}")
    public Result findAllNotesByOwnerId(@PathVariable Integer ownerId) {
        List<Note> returnedNotes = this.noteService.findAllById(ownerId);
        List<NoteDto> notesDto = returnedNotes
                .stream()
                .map(this.noteToNoteDtoConverter::convert)
                .toList();

        return new Result(true, StatusCode.SUCCESS, "Find All Notes By Owner Id Success", notesDto);
    }
    @PutMapping("/updateNoteByNoteId/{noteId}")
    public Result updateNoteByNoteId(@RequestBody NoteDto noteDto, @PathVariable String noteId) {
        Note note = noteDtoToNoteConverter.convert(noteDto);
        Note updatedNote = noteService.updateNoteById(noteId, note);
        NoteDto updatedNoteDto = noteToNoteDtoConverter.convert(updatedNote);
        return new Result(true, StatusCode.SUCCESS, "Update Note By User Id Success", updatedNoteDto);
    }
    @DeleteMapping("/deleteNoteByNoteId/{noteId}")
    public Result deleteNoteByNoteId(@PathVariable String noteId) {
        noteService.deleteNoteById(noteId);
        return new Result(true, StatusCode.SUCCESS, "Delete Note By Note Id Success");
    }

}
