package tsp.polaris.recognition.other;

/**
 * Classe représentant une étoile dans l'espace
 *
 * @author Emma M.
 */

public class Star extends Point{
    private final double brightness; // Luminosité de l'étoile

    /**
     * Constructeur de la classe Star
     *
     * @param x Coordonnée en x de l'étoile
     * @param y Coordonnée en y de l'étoile
     * @param brightness Luminosité de l'étoile
     * @param size Taille de l'étoile
     */
    public Star(double x, double y, double brightness, double size) {
        super(x, y, size);
        this.brightness = brightness;
    }

    /**
     * Affichage des coordonnées et de la luminosité de l'étoile
     *
     * @return Chaine de caractères contenant les coordonnées, la luminosité et la taille
     */
    @Override
    public String toString() {
        return super.toString() + ": " + brightness + ", " + size;
    }

    /**
     * Getter de la luminosité de l'étoile
     *
     * @return La luminosité de l'étoile
     */
    public double getBrightness() {
        return brightness;
    }

    /**
     * Getter de la taille de l'étoile
     *
     * @return La taille de l'étoile
     */
    public double getSize() { 
        return size;
    }

}
