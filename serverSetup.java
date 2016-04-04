import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class serverSetup implements Runnable {

    static ArrayList<String> data = new ArrayList<>();

    protected int serverPort = 8080;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;

    BufferedReader in = null;
    String inputLine;
    String toAdd;

    public serverSetup(int port) throws Exception {

        this.serverPort = port;

        /* Start server */
        Server server = new Server(8080);

        /* Setup HTTP Connector */
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(8080);
        http.setIdleTimeout(30000);

        /* Set connector */
        server.addConnector(http);

        /* Setup Servlet Handler */
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(Servlet.class, "/*");
    }

    public void run(){
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        openServerSocket();

        /* If server is not closed */
        while (!isStopped()) {
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept(); //Accept client
            } catch (IOException e) {
                /* If server closed */
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException("Can't accept client");
            }
            /* Start worker thread on new client */
            new Thread(new WorkerRunnable(clientSocket, "Multithreaded Server")).start();

            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Buffer reader to receive possible progress report
            } catch (IOException e) { e.printStackTrace(); }

            try {
                /* Each progress report will be one string */
                inputLine = in.readLine();

                if (inputLine.length() != 0){
                    toAdd = inputLine; //Read until the end
                }

                String[] split = toAdd.split(",");

                /* If progress report == Done */
                if (split[1].equals("Done")){
                    for (String i:data){ //Search for progress is database
                        if (i.contains(split[1])){
                            data.remove(i); //Remove progress
                        }
                    }
                } else { data.add(toAdd); /* Add progress to database */ }

            } catch (IOException e) { e.printStackTrace(); }
        }
        System.out.println("Server Stopped.");
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}



