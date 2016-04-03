package filetransfer;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

class C_Client {
    public static void main(String args[]) {
}}

//    private final String IPaddr = "127.0.0.1";
//    private final int serverPort = 3248;
//    private final String fileOut = "t.torrent";
//
//    public static void main(String args[]) {
//        byte[] aByte = new byte[1];
//        int bytesRead;
//
//        Socket clientSocket = null;
//        InputStream is = null;
//
//        try {
//            clientSocket = new Socket( IPaddr , serverPort );
//            is = clientSocket.getInputStream();
//        } catch (IOException ex) {
//            // Do exception handling
//        }
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        if (is != null) {
//
//            FileOutputStream fos = null;
//            BufferedOutputStream bos = null;
//            try {
//                fos = new FileOutputStream( fileOut );
//                bos = new BufferedOutputStream(fos);
//                bytesRead = is.read(aByte, 0, aByte.length);
//
//                do {
//                    baos.write(aByte);
//                    bytesRead = is.read(aByte);
//                } while (bytesRead != -1);
//
//                bos.write(baos.toByteArray());
//                bos.flush();
//                bos.close();
//                clientSocket.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//}