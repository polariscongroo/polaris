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
     */
    public Star(double x, double y, double brightness) {
        super(x, y);
        this.brightness = brightness;
    }

    /**
     * Affichage des coordonnées et de la luminosité de l'étoile
     *
     * @return Chaine de caractères contenant les coordonnées et la luminosité
     */
    public String toString() {
        return "(" + point[0] + ", " + point[1] + ") : " + brightness;
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
     * Compare deux étoiles en fonction de leur luminosité
     * @param secondStar Etoile dont on veut comparer la luminosité
     * @return Un entier signé en fonction de si la luminosité de l'étoile est plus grande, plus petite ou identique que la 2e
     */
    public int compareTo(Star secondStar) {
        if(brightness == secondStar.brightness){
            return 0;
        } else if(brightness > secondStar.brightness){
            return 1;
        } else{
            return -1;
        }
    }
}
