package practicespringboot.notesapplicationonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import practicespringboot.notesapplicationonline.note.utils.IdWorker;

@SpringBootApplication
public class NotesApplicationOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesApplicationOnlineApplication.class, args);
	}

	@Bean
	public IdWorker idWorker() {
		return new IdWorker(1,1);
	}

}
