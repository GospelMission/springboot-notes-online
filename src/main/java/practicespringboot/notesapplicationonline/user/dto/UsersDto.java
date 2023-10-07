package practicespringboot.notesapplicationonline.user.dto;

import jakarta.validation.constraints.NotEmpty;

public record UsersDto(
        Integer id,
        @NotEmpty(message = "email is required")
        String email,
        @NotEmpty(message = "firstName is required")
        String firstName,
        String middleName,
        String lastName,
        String role,
        boolean enabled,
        Integer numberOfNotes) {}
