package New; /**
 * Created by ADMIN on 23/11/2558.
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class C_Server {
    ArrayList clientOutputStream;
    int progress = 0;


    public class ClientHandler implements Runnable{
        BufferedReader reader;
        Socket socket;
        public ClientHandler(Socket clientSocker){

            try{
                socket = clientSocker;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);
                }catch (Exception ex){
                ex.printStackTrace();

            }

        }
        public void run(){
            String message;
            try {
                while ((message = reader.readLine()) != null){

                    int add_progress = Integer.parseInt(message.split(":")[1].substring(1));
                    System.out.println("read from RUN() --> " + add_progress );
                    progress += add_progress;
                    System.out.println("read from RUN() progress --> " + progress );
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new C_Server().go();
    }


    public void go(){
        System.out.println("SERVER: go()");
        clientOutputStream = new ArrayList();
        try{
            ServerSocket serverSocket = new ServerSocket(5000);

            while (true){

                Socket clientSocker = serverSocket.accept();

                System.out.println("SERVER: clientSocket port = " + clientSocker.getPort());
                PrintWriter writer = new PrintWriter(clientSocker.getOutputStream());

                clientOutputStream.add(writer);

                Thread t = new Thread(new ClientHandler(clientSocker));
                t.start();
                System.out.println("SERVER: got a connection");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



}
