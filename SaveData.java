import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class SaveData {

    private static String university="";
    private static String budget = "";
    private static String mintm = "";
    private static String maxtm = "";
    private static String uniDistance = "";
    private static String metroDistance = "";
    private static String budget_preference = "";
    private static String tm_preference = "";   
    private static String unidistance_preference = "";
    private static String mmmdistance_preference = "";


    public static void main(String[] args) throws IOException {
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        System.out.println("Server running at http://localhost:8000");

        
        server.createContext("/save", new SaveHandler());

        
        server.setExecutor(null);
        server.start();
    }

    static class SaveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {

                
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

                
                InputStream inputStream = exchange.getRequestBody();
                String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8); // UTF-8 encoding

            
                String[] parts = requestBody.replace("{", "").replace("}", "").replace("\"", "").split(",");
                for (String part : parts) {
                    String[] keyValue = part.split(":");
                    if (keyValue[0].trim().equals("university")) {
                        university = keyValue[1].trim();
                    } else if (keyValue[0].trim().equals("budget")) {
                        budget = keyValue[1].trim();
                    } else if (keyValue[0].trim().equals("mintm")) {
                        mintm = keyValue[1].trim();
                    } else if (keyValue[0].trim().equals("maxtm")) {
                        maxtm = keyValue[1].trim();
                    } else if (keyValue[0].trim().equals("uniDistance")) {
                        uniDistance = keyValue[1].trim();
                    } else if (keyValue[0].trim().equals("metroDistance")) {
                        metroDistance = keyValue[1].trim();
                    } else if (keyValue[0].trim().equals("budget_preference")) {
                        budget_preference = keyValue[1].trim();
                    } else if (keyValue[0].trim().equals("tm_preference")) {
                        tm_preference = keyValue[1].trim();                
                    } else if (keyValue[0].trim().equals("unidistance_preference")) {
                        unidistance_preference = keyValue[1].trim();
                    } else if (keyValue[0].trim().equals("mmmdistance_preference")) {
                        mmmdistance_preference = keyValue[1].trim();                   
                    }
                }

        
                String response = "Τα δεδομένα αποθηκεύτηκαν με επιτυχία!";
                exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);

            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            }
   

            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                exchange.sendResponseHeaders(204, -1);
            } else {
                
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
}
