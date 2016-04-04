import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/* Servlet class handles file management and sending files */
public class Servlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String param = request.getParameter("p");

        /* If false request */
        if (param == null)
        {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter p missing");
            return;
        }

        /* Create buffer to read */
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        /* Assume file is inside current dir */
        File fileTorrent = new File("test.torrent");
        FileInputStream input = new FileInputStream(fileTorrent);
        String filename = "test";

        /* Prep work for response parameter */
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""+filename+"\"");
        response.setContentLength((int) fileTorrent.length());

        OutputStream output = response.getOutputStream();

        /* Write and send in bytes */
        try {
            int byteRead;
            while ((byteRead = input.read()) != -1) {
                output.write(buffer, 0, byteRead);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            output.close();
            input.close();
        }
    }
}