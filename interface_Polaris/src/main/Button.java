package main;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * Classe personnalisée étendant {@link JButton} pour créer un bouton avec un effet de flou en arrière-plan.
 * 
 * @author RAVEN, Chadi A., ChatGPT
 */
public final class Button extends JButton {
    private Component blurComponent;
    private BufferedImage blurredBackground;

    /**
     * Constructeur de la classe {@code Button}.
     * Configure le bouton pour qu'il n'ait pas de fond, ne peigne pas de bordure et soit transparent.
     */
    public Button() {
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
    }

    /**
     * Définit le composant à flouter en arrière-plan.
     * @param blur Le composant à flouter.
     */
    public void setBlur(Component blur) {
        this.blurComponent = blur;
        SwingUtilities.invokeLater(() -> {
            createImage();
            repaint();
        });
    }

    /**
     * Capture l’arrière-plan et applique un effet de flou.
     */
    private void createImage() {
        if (blurComponent != null && blurComponent.getParent() != null) {
            Component parent = blurComponent.getParent();
            BufferedImage background = new BufferedImage(parent.getWidth(), parent.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = background.createGraphics();
            parent.paint(g2);
            g2.dispose();

            blurredBackground = blurImage(background);
        }
    }

    /**
     * Applique un effet de flou sur une image donnée.
    */
    private BufferedImage blurImage(BufferedImage image) {
        float[] blurMatrix = {
            1f/16f, 2f/16f, 1f/16f,
            2f/16f, 4f/16f, 2f/16f,
            1f/16f, 2f/16f, 1f/16f
        };
        Kernel kernel = new Kernel(3, 3, blurMatrix);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }


    @Override
    protected void paintComponent(Graphics g) {
        if (blurredBackground != null) {
            g.drawImage(blurredBackground, 0, 0, getWidth(), getHeight(), getX(), getY(), getX() + getWidth(), getY() + getHeight(), null);
        }
        super.paintComponent(g);
    }
}
