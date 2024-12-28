import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    // Lista para almacenar a todos los clientes conectados
    // El tipo genérico <ClientHandler> asegura que solo se puedan agregar objetos del tipo ClientHandler, que en este caso es una clase que definimos nosotros en el mismo proyecto
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        // Usamos try-with-resources para asegurarnos de que el ServerSocket se cierre al terminar
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            // Mensaje indicando que el servidor está listo para aceptar conexiones
            System.out.println("Servidor inicializado. Esperando clientes...");

            // Bucle infinito para aceptar múltiples conexiones de clientes
            while (true) {
                // Acepta una conexión de un cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket);

                // Crea un manejador para el cliente y lo agrega a la lista de clientes conectados
                ClientHandler clientThread = new ClientHandler(clientSocket, clients);
                clients.add(clientThread);

                // Inicia un nuevo hilo para gestionar la comunicación con este cliente
                new Thread(clientThread).start();
            }
        } catch (IOException e) {
            // Captura y muestra cualquier error que ocurra con el servidor
            System.out.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// Clase para manejar la comunicación con cada cliente
class ClientHandler implements Runnable {
    private Socket clientSocket; // El socket para comunicarse con el cliente
    private List<ClientHandler> clients; // Lista compartida de todos los clientes conectados
    private PrintWriter out; // Flujo de salida para enviar mensajes al cliente
    private BufferedReader in; // Flujo de entrada para recibir mensajes del cliente

    // Constructor que inicializa los flujos y el socket del cliente
    public ClientHandler(Socket socket, List<ClientHandler> clients) throws IOException {
        this.clientSocket = socket;
        this.clients = clients;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    // Método que define el comportamiento del hilo para cada cliente
    public void run() {
        try {
            String inputLine; // Variable para almacenar los mensajes entrantes
            // Bucle para recibir mensajes del cliente
            while ((inputLine = in.readLine()) != null) {
                // Difunde el mensaje recibido a todos los clientes conectados
                for (ClientHandler aClient : clients) {
                    aClient.out.println(inputLine);
                }
            }
        } catch (IOException e) {
            // Manejo de errores durante la comunicación con el cliente
            System.out.println("Error con el cliente: " + e.getMessage());
        } finally {
            // Limpieza de recursos cuando el cliente se desconecta
            try {
                in.close(); // Cierra el flujo de entrada
                out.close(); // Cierra el flujo de salida
                clientSocket.close(); // Cierra el socket del cliente
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
