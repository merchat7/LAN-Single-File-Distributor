/* Test case for starting a server */
/* Values are expected to be changed for actual working program */

public class startServer {
    public static void main(String[] args) throws Exception {
        serverSetup mainServer = new serverSetup(9000); //Note: 8080 doesn't work for some reason

        new Thread(mainServer).start();

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        mainServer.stop();
    }
}
