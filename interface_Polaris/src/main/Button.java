package main;

import javax.swing.JButton;
/**
 * Classe personnalisée étendant {@link JButton} pour créer un bouton sans fond, sans bordure et transparent.
 * Utilisée pour personnaliser l'apparence d'un bouton dans une interface graphique.
 * @author RAVEN, Chadi A.
 */
public class Button extends JButton {
    /**
     * Constructeur de la classe {@code Button}.
     * Configure le bouton pour qu'il n'ait pas de fond, ne peigne pas de bordure et soit transparent.
     */
    public Button() {
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
    }
}