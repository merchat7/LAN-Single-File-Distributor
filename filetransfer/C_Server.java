package filetransfer;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class C_Server {

    public static void main(String[] args) {
            {
                Server server = new Server(8080);


                ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
                context.setResourceBase(".");
                context.setContextPath("/");
                server.setHandler(context);

                ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
                holderDefault.setInitParameter("dirAllowed", "true");
                context.addServlet(holderDefault, "/");
                ServletHolder holderEvents = new ServletHolder("ws-events", EventServlet.class);
                context.addServlet(holderEvents, "/events/*");

                try {
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    server.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
