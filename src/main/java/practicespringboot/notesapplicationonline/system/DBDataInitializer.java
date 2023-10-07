package practicespringboot.notesapplicationonline.system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import practicespringboot.notesapplicationonline.user.Users;
import practicespringboot.notesapplicationonline.user.UsersRepository;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final UsersRepository usersRepository;

    public DBDataInitializer(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Users sampleUser = new Users();
        sampleUser.setId(1);
        sampleUser.setEmail("sampleUser@email.com");
        sampleUser.setFirstName("sampleFirstName");
        sampleUser.setMiddleName("sampleMiddleName");
        sampleUser.setLastName("sampleLastName");
        sampleUser.setPassword("@Password1");
        sampleUser.setEnabled(true);
        sampleUser.setRole("admin");

        usersRepository.save(sampleUser);
    }
}
