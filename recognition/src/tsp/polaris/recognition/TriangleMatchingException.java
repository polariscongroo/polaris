package tsp.polaris.recognition;
/**
 * Exception personnalisée lancée lorsqu'une erreur survient lors de la correspondance des triangles.
 * * @author Chadi A., Emma M.
 */
public class TriangleMatchingException extends Exception
{
    /**
     * Constructeur de l'exception.
     *
     * @param message Le message détaillant l'erreur qui a provoqué l'exception.
     */
    public TriangleMatchingException(String message)
    {
        super(message);
    }
}
