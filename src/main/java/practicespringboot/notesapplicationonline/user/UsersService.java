package practicespringboot.notesapplicationonline.user;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import practicespringboot.notesapplicationonline.system.exception.UserAlreadyExistException;

@Service
@Transactional
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users save(Users newUser) {
        return (Users) this.usersRepository.findByEmail(newUser.getEmail())
                    .map(existingUser -> {
                        throw new UserAlreadyExistException(newUser.getEmail());
                    })
                    .orElseGet(() -> {
                        newUser.setRole(UsersRole.USER);
                        newUser.setEnabled(true);
                        return usersRepository.save(newUser);
                    });
    }   
}
