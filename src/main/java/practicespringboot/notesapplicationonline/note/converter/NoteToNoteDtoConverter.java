package practicespringboot.notesapplicationonline.note.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import practicespringboot.notesapplicationonline.note.Note;
import practicespringboot.notesapplicationonline.note.dto.NoteDto;
import practicespringboot.notesapplicationonline.user.converter.UsersToUsersDtoConverter;

@Component
public class NoteToNoteDtoConverter implements Converter<Note, NoteDto> {
    UsersToUsersDtoConverter usersToUsersDtoConverter;

    public NoteToNoteDtoConverter(UsersToUsersDtoConverter usersToUsersDtoConverter) {
        this.usersToUsersDtoConverter = usersToUsersDtoConverter;
    }

    @Override
    public NoteDto convert(Note source) {
        NoteDto noteDto = new NoteDto(
                source.getId(),
                source.getTitle(),
                source.getDescription(),
                source.getDate(),
                usersToUsersDtoConverter.convert(source.getOwner())
        );

        return noteDto;
    }
}
