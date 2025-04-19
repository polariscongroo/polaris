package tsp.polaris.recognition.other;

/**
 * Classe représentant une étoile dans l'espace
 *
 * @author Emma M.
 */

public class Star extends Point implements Comparable<Star> {
    private final double brightness; // Luminosité de l'étoile
    private final double size; // Taille de l'étoile

    /**
     * Constructeur de la classe Star
     *
     * @param x Coordonnée en x de l'étoile
     * @param y Coordonnée en y de l'étoile
     * @param brightness Luminosité de l'étoile
     * @param size Taille de l'étoile
     */
    public Star(double x, double y, double brightness, double size) {
        super(x, y);
        this.brightness = brightness;
        this.size = size;
    }

    /**
     * Affichage des coordonnées et de la luminosité de l'étoile
     *
     * @return Chaine de caractères contenant les coordonnées et la luminosité
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

    /**
     * Compare deux étoiles en fonction de leur luminosité
     * @param secondStar Etoile dont on veut comparer la luminosité
     * @return Un entier signé en fonction de si la luminosité de l'étoile est plus grande, plus petite ou identique que la 2e
     */
    public int compareTo(Star secondStar) {
        return Double.compare(secondStar.brightness, brightness);
    }

}
