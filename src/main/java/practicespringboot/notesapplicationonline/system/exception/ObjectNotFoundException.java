package practicespringboot.notesapplicationonline.system.exception;

public class ObjectNotFoundException extends RuntimeException {
        public ObjectNotFoundException(Class<?> objectType, String id) {
            super("Could not find " + objectType.getSimpleName() + " with Id " + id);
        }
}
