import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MyFrame extends JFrame implements KeyListener {

    JTextField textField;
    JButton sendButton;
    Socket s;

    InputStreamReader isr;
    BufferedReader br;

    PrintWriter pw;

    static String username = null;

    //static ArrayList<ChatTextLabel> chatText = new ArrayList<ChatTextLabel>();

    JPanel chatPanel;
    JPanel namePanel;

    JScrollPane scroll;

    static ArrayList<ChatTextLabel> chatTextList;
    static ArrayList<GifLabel> gifLabelList;

    public MyFrame() throws MalformedURLException {
        chatTextList = new ArrayList<ChatTextLabel>();
        gifLabelList = new ArrayList<GifLabel>();

        ImageIcon icon = new ImageIcon("logo.png");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setTitle("Pisscord");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(new Color(44,47,51));
        this.setLayout(null);


        this.addKeyListener(this);

        this.setVisible(true);

        chatPanel = new JPanel();
        chatPanel.setBackground(new Color(58,63,67));
        //chatPanel.setBounds(0,50,this.getWidth(),this.getHeight()-45);
        chatPanel.setBounds(0,50,this.getWidth(),6000);
        chatPanel.setLayout(null);

        scroll = new JScrollPane(chatPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(this.getWidth()-25,50,10,550);

        namePanel = new JPanel();
        namePanel.setBackground(new Color(54,58,61));
        namePanel.setBounds(0,0,1280,50);
        namePanel.setLayout(null);

        //Show Logo in Panel
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon("iconpng.png"));
        logoLabel.setVerticalAlignment(JLabel.TOP);
        logoLabel.setHorizontalAlignment(JLabel.LEFT);
        logoLabel.setBounds(0,5,75, 45);

        textField = new JTextField();
        textField.setBackground(new Color(40,44,50));
        textField.setForeground(new Color(170,170,170));
        textField.setText("Enter Your Name");
        textField.setCaretColor(Color.white);
        textField.setHorizontalAlignment(JLabel.LEFT);
        textField.setBounds(0,this.getHeight()-75, this.getWidth()-100, 35);
        textField.addKeyListener(this);

        /*sendButton = new JButton(new ImageIcon("sendButton.png"));
        sendButton.setBackground(new Color(0,0,0,0));
        sendButton.setVerticalAlignment(JButton.TOP);
        sendButton.setHorizontalAlignment(JButton.LEFT);
        sendButton.setBounds(this.getWidth()-80,this.getHeight()-140,100, 100);
        sendButton.addActionListener(this);*/
/*
        URL gifUrl = new URL("https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/5eeea355389655.59822ff824b72.gif");
        Icon gifIcon = new ImageIcon(gifUrl);
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(200,0,500,400);*/

        //frame.add(label);
        //new ChatTextLabel("Du bist gejoint Diga", true, chatPanel);
        //this.add(scroll, BorderLayout.CENTER);
        //chatPanel.add(sendButton);
        //this.add(gifLabel);
        namePanel.add(logoLabel);
        this.add(textField);
        this.add(chatPanel);
        this.add(namePanel);


        try {
            //s = new Socket("10.0.128.112", 33067);
            s = new Socket("localhost", 12345);
            pw = new PrintWriter(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        } catch (IOException ex) {
            System.out.println("Hier");
            ex.printStackTrace();
        }
        chatPanel.repaint();
    }

    /*@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            if (username == null) {
                username = textField.getText();
                textField.setText(null);
            } else {
                sendMessage();
            }
        }

    }*/

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(username==null){
                username = textField.getText();
                pw.println(username);
                pw.flush();

            }else{
                sendMessage();
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void sendMessage(){
        pw.println(username+": "+textField.getText());
        pw.flush();
        textField.setText(null);
    }

    public void addChatLabel(String msgContent) {

        //new ChatTextLabel(msgContent, false, chatPanel);
        if (chatTextList.size() > 9||gifLabelList.size()>0){
            gifLabelList.clear();
            chatTextList.clear();
            chatPanel.removeAll();
        }
        System.out.println(chatTextList.size());
        chatTextList.add(new ChatTextLabel(msgContent, false, chatTextList.size()));
        chatPanel.add(chatTextList.get(chatTextList.size()-1));
        //TimeUnit.SECONDS.sleep(2);
        this.repaint();
    }
    public void addSentLabel(String url) throws MalformedURLException {//TODO
        System.out.println("url:"+url);
        gifLabelList.clear();
        chatPanel.removeAll();
        gifLabelList.add(new GifLabel(url));
        chatPanel.add(gifLabelList.get(gifLabelList.size()-1));
        this.repaint();
    }
    /*public static void addChatText(ArrayList<ChatTextLabel> arrayList, JPanel panel){
        new ChatTextLabel("test pls", true, panel);
        arrayList.add(new ChatTextLabel("test pls", true, panel));
        System.out.println(arrayList.get(0));
    }*/
}
