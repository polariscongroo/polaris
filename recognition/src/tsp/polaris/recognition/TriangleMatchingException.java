package tsp.polaris.recognition;
/**
 * Exception personnalisee lancee lorsqu'une erreur survient lors de la correspondance des triangles.
 * * @author Chadi A., Emma M.
 */
public class TriangleMatchingException extends Exception
{
    /**
     * Constructeur de l'exception.
     *
     * @param message Le message detaillant l'erreur qui a provoque l'exception.
     */
    public TriangleMatchingException(String message)
    {
        super(message);
    }
}
