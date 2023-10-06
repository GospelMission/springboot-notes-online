package practicespringboot.notesapplicationonline.system.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Integer id) {
        super("Could not find User with Id " + id);
    }
}
