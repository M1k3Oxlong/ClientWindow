import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GifLabel extends JLabel{

    public GifLabel(String url) throws MalformedURLException {
        URL gifUrl = new URL(url);
        Icon gifIcon = new ImageIcon(gifUrl);
        this.setIcon(gifIcon);
        this.setBounds(10,10,1000,500);
    }

}
