package practicespringboot.notesapplicationonline.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {
    Optional<List<Note>> findAllByOwner_Id(Integer id);
}
