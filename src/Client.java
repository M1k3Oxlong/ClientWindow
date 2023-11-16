import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class Client {

    private Socket s;
    private BufferedReader br;
    private BufferedWriter bw;
    static MyFrame frame;
    private String username;
    public Client(Socket s){
        try{
            this.s = s;
            this.bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.username = "username";
        } catch (IOException e){
            closeEverything(s, br, bw);
        }
    }

    public static void main(String[] args) throws IOException {
         frame = new MyFrame();


        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();*/
        Socket s = new Socket("localhost", 12345);
        Client client = new Client(s);
        client.listenForMessage();
        client.sendMessage();
    }

    public void sendMessage(){
        try{
            //bw.write(username);
            bw.newLine();
            bw.flush();
            Scanner scanner = new Scanner(System.in);
            while (s.isConnected()){
                String messageToSend = scanner.nextLine();
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e){
            closeEverything(s, br, bw);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }) {
            @Override
            public void run() {
                String msgFromChat;
                while (s.isConnected()){
                    try {
                        msgFromChat = br.readLine();
                        if(msgFromChat.contains("#")){
                            String urlString = msgFromChat;
                            while(urlString.charAt(0)!='#'){
                                urlString = urlString.substring(1);
                            }
                            urlString = urlString.substring(1);
                            //System.out.println("Link: "+urlString);
                            frame.addSentLabel(urlString);
                        }else{
                            //System.out.println(msgFromChat);
                            frame.addChatLabel(msgFromChat);
                        }

                    } catch (IOException e){
                        closeEverything(s, br, bw);
                    }
                }
            }
        }.start();
    }

    public void closeEverything(Socket s, BufferedReader br, BufferedWriter bw){
        try {
            if (br!=null){
                br.close();
            }
            if (bw!=null){
                bw.close();
            }
            if (s!=null){
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /*public static void main(String[] args) throws IOException {
        Socket s;
        s = new Socket("10.0.128.179", 3306);
        try {
            Scanner scanner = new Scanner(System.in);
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(in);
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            while (true) {
                String something = scanner.nextLine();
                pw.println(something);
                pw.flush();
            }
        } catch (IOException e){
            closeEverything(s, )
        }
    }*/

}


