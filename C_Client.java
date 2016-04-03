package New; /**
 * Created by ADMIN on 23/11/2558.
 */
import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class C_Client {
    PrintWriter writer;
    Socket socket;


    int downloaded_byte = 0;

    // for file full size
    int file_size = 5000;


    public void go() {

        setUpNetworking();


        // Timer for timing after ... seconds --> start sending progress to server
        Timer timer = new Timer();

        class RepeatTask extends TimerTask {
            @Override
            public void run() {
                System.out.println("Time count 20 second");

                // get the byte from the downloaded file
                // downloaded_byte += file_size (byte);
                downloaded_byte += 20;
                System.out.println("and total receiving byte :" + downloaded_byte);

                send_track(); // to server

                // if download finish
                if (downloaded_byte == file_size) {
                    timer.cancel();

                }

            }
        }



        // timer start after 10 second and repeat every 20 seconds
        timer.schedule(new RepeatTask(), 10, 20);


        // this method performs the task
    }

    private void setUpNetworking(){
        try {

            socket = new Socket("127.0.0.1", 5000);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("CLIENT: networking established");
        }catch (IOException ex){
            ex.printStackTrace();

        }
    }

    public void send_track(){
        try {
            // send how many it can download to server in string
            // "Progress : 20%"
//            System.out.println(writer);
            writer.println("Progress : " + downloaded_byte);
            writer.flush(); // flush to the server

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new C_Client().go();
    }
}
