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



//On crée le fond de notre interface
public class Background extends JComponent{
    
    private Icon image;
    private BufferedImage bufferedImage;
    private Component blur;
    
    //Sélectionne l'image de fond
    public Background() {
        image = new ImageIcon(getClass().getResource("mim.jpg"));
    }
    
    //Place l'image sélectionnée au centre de l'interface
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
   
    protected void paintComponent(Graphics grphcs) {
        grphcs.drawImage(bufferedImage, 0, 0, null);
        super.paintComponent(grphcs);
    }
    
    public Component getBlur() {
        return blur;
    }

    public void setBlur(Component blur) {
        this.blur = blur;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createImage();
                repaint();
            }
        });
    }
    
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
