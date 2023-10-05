package practicespringboot.notesapplicationonline.system.exception;

import practicespringboot.notesapplicationonline.note.Note;

import java.util.List;
import java.util.Optional;

public class ObjectNotFoundException extends RuntimeException {
        public ObjectNotFoundException(Class<?> objectType, String id) {
            super("Could not find " + objectType.getSimpleName() + " with Id " + id);
        }

        public ObjectNotFoundException(Class<?> objectType, Integer id) {
            super("Could not find " + objectType.getSimpleName() + " with Id " + id);
        }

        public ObjectNotFoundException(List<Class<?>> objectType, Integer id) {
            super("Could not find List of " + objectType.get(0).getSimpleName() +" with Id " + id);
        }

}
