package filetransfer;

import java.io.*;
import java.net.*;

class C_Server {

    private final String file = "C:\\Users\\DevSingh\\Desktop\\mario" ;

    public static void main(String args[]) {

        while (true) {
            ServerSocket welcomeSocket = null;
            Socket connectionSocket = null;
            BufferedOutputStream outToClient = null;

            try {
                welcomeSocket = new ServerSocket(3248); // server port
                connectionSocket = welcomeSocket.accept(); // listen and accept connection
                outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (outToClient != null) { // if OutputStream not empty
                File myFile = new File(file); // initialize file
                byte[] mybytearray = new byte[(int) myFile.length()]; // byte array of file size

                FileInputStream fis = null;

                try {
                    fis = new FileInputStream(myFile); // link with the file
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                BufferedInputStream bis = new BufferedInputStream(fis);//wrap

                try {
                    bis.read(mybytearray, 0, mybytearray.length);
                    outToClient.write(mybytearray, 0, mybytearray.length);
                    outToClient.flush();
                    outToClient.close();
                    connectionSocket.close();

                    // File sent
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}