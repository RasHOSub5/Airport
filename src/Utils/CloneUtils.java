package Utils;

public class CloneUtils {
    @SuppressWarnings("unchecked")
    public static <T> T clone(T object) {
        if (object instanceof Cloneable) {
            try {
                return (T) object.getClass().getMethod("clone").invoke(object);
            } catch (Exception e) {
                throw new RuntimeException("Error al clonar", e);
            }
        }
        throw new IllegalArgumentException("Objeto no clonable");
    }
}
