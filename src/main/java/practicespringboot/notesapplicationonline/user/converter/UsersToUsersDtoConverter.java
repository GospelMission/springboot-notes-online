package practicespringboot.notesapplicationonline.user.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import practicespringboot.notesapplicationonline.user.Users;
import practicespringboot.notesapplicationonline.user.dto.UsersDto;
@Component
public class UsersToUsersDtoConverter implements Converter<Users, UsersDto> {
    @Override
    public UsersDto convert(Users source) {
        UsersDto usersDto = new UsersDto(
                source.getId(),
                source.getEmail(),
                source.getFirstName(),
                source.getMiddleName(),
                source.getLastName(),
                source.getRole(),
                source.isEnabled(),
                source.getNumberOfNotes()
        );
        return usersDto;
    }
}
