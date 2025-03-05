package interfacegraphique;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;


/**
 * Cette classe represente un fond d'ecran personnalise pour une interface graphique.
 * Elle permet d'afficher une image en fond d'ecran et d'ajouter un effet de flou sur un composant specifie.
 * Elle etend la classe {@link JComponent} pour être utilisee comme un composant visuel dans une interface graphique.
 * @author RAVEN, Chadi A.
 */
//On cree le fond de notre interface
public class Background extends JComponent{
    
    private Icon image;
    private BufferedImage bufferedImage;
    private Component blur;
    /**
     * Constructeur de la classe {@code Background}.
     * Charge une image de fond par defaut.
     */
    //Selectionne l'image de fond
    public Background() {
        image = new ImageIcon(getClass().getResource("polarisbg.jpg"));
    }
    /**
     * Cree l'image de fond redimensionnee pour s'ajuster à la taille du composant.
     * L'image est redessinee chaque fois que le composant est redimensionne.
     */
    //Place l'image selectionnee au centre de l'interface
    private void createImage() {
        if (image != null) {
            int width = getWidth();
            int height = getHeight();
            if (width > 0 && height > 0) {
                bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = bufferedImage.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                Rectangle rec = getAutoSize(image);
                g2.drawImage(((ImageIcon) image).getImage(), rec.x, rec.y, rec.width, rec.height, null);
                if (getBlur() != null) {
                    createBlurImage(g2);
                }
                g2.dispose();
            }
        }
    }
    /**
     * Methode de dessin du fond d'ecran sur le composant.
     * Elle appelle la methode {@link #createImage()} pour redessiner l'image de fond.
     * 
     * @param grphcs L'objet {@link Graphics} utilise pour dessiner l'image sur le composant.
     */
    protected void paintComponent(Graphics grphcs) {
        grphcs.drawImage(bufferedImage, 0, 0, null);
        super.paintComponent(grphcs);
    }
    /**
     * Recupère le composant qui sera floute en arrière-plan.
     * 
     * @return Le composant à flouter.
     */
    public Component getBlur() {
        return blur;
    }
    /**
     * Definit un composant à flouter en arrière-plan.
     * Après la definition du composant, l'image est redessinee avec l'effet de flou.
     * 
     * @param blur Le composant à flouter.
     */
    public void setBlur(Component blur) {
        this.blur = blur;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createImage();
                repaint();
            }
        });
    }
    /**
     * Redefinit les dimensions du composant et met à jour l'image de fond.
     * Cette methode est utilisee pour ajuster la taille de l'image en fonction du redimensionnement du composant.
     * 
     * @param i La position en X du composant.
     * @param i1 La position en Y du composant.
     * @param i2 La largeur du composant.
     * @param i3 La hauteur du composant.
     */
    //Scale l'image
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createImage();
                repaint();
            }
        });
    }
    /**
     * Calcule les dimensions et la position de l'image redimensionnee pour s'adapter au composant.
     * L'image est redimensionnee proportionnellement pour occuper tout l'espace du composant.
     * 
     * @param image L'image à redimensionner.
     * @return Un rectangle representant les nouvelles dimensions et la position de l'image.
     */
    private Rectangle getAutoSize(Icon image) {
        int w = getWidth();
        int h = getHeight();
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        int x = (w - width) / 2;
        int y = (h - height) / 2;
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }
    /**
     * Cree l'image floutee du composant passe en paramètre.
     * Cette methode genère une image floutee du composant et l'ajoute au fond d'ecran.
     * 
     * @param g L'objet {@link Graphics2D} utilise pour dessiner l'image floutee.
     */
    private void createBlurImage(Graphics2D g) {
        int x = getBlur().getX();
        int y = getBlur().getY();
        int width = getBlur().getWidth();
        int height = getBlur().getHeight();
        if (width > 0 && height > 0) {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.dispose();
            g.drawImage(img, x, y, null);
        }
    }
}
