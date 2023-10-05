package practicespringboot.notesapplicationonline.note.dto;

import practicespringboot.notesapplicationonline.user.dto.UsersDto;

import java.util.Date;

public record NoteDto(
        String id,
        String title,
        String description,
        Date date,
        UsersDto owner
) {
}
