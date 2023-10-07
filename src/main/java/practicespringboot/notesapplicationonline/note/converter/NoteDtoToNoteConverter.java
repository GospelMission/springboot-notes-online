package practicespringboot.notesapplicationonline.note.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import practicespringboot.notesapplicationonline.note.Note;
import practicespringboot.notesapplicationonline.note.dto.NoteDto;
@Component
public class NoteDtoToNoteConverter implements Converter<NoteDto, Note> {
    @Override
    public Note convert(NoteDto source) {
        Note note = new Note();
            note.setTitle(source.title());
            note.setDescription(source.description());

        return note;
    }
}
