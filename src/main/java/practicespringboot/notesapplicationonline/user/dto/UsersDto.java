package practicespringboot.notesapplicationonline.user.dto;

public record UsersDto(
        Integer id,
        String firstName,
        String middleName,
        String lastName,
        String role,
        boolean enabled,
        Integer numberOfNotes) {}
