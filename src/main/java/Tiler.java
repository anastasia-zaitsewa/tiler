import javax.swing.*;

import java.awt.*;

import static javax.swing.WindowConstants.*;

public class Tiler {

    private JFrame mainFrame;
    private JPanel controlPanel;

    public Tiler() {
        prepareGUI();
    }

    private void prepareGUI() {
        SwingUtilities.invokeLater(this::prepareFrame);
    }

    public void showImage(ImageIcon imageIcon) {
        JLabel label = new JLabel();
        label.setIcon(imageIcon);
        mainFrame.add(label, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void prepareFrame() {
        mainFrame = new JFrame("Show Image");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Tiler().showImage();
    }
}
