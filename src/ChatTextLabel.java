import javax.swing.*;
import java.awt.*;

public class ChatTextLabel extends JLabel {

    public ChatTextLabel(String content, boolean writtenBySelf, int i) {

        this.setText(content);

        this.setHorizontalAlignment(JLabel.LEFT);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setOpaque(true);
        this.setBackground(new Color(200,200,200));
        if(writtenBySelf){
            this.setBounds(600,i*60,600,30);
        }else{
            this.setBounds(0,i*60,600,30);
        }

        //panel.add(this);
    }
}
