package app.fileIO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IconReader {

    public static ImageIcon readIcon(String fileName) throws IOException {

        BufferedImage icon = ImageIO.read(new File("src/main/resources/" + fileName + ".png"));
        return new ImageIcon(icon);
    }
}
