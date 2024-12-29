import java.io.*; 
import java.net.*;
import java.util.function.Consumer;

/**
 * Clase que representa un cliente de chat que se conecta a un servidor
 * y envía/recibe mensajes. 
 */
public class ChatClient {

    // Socket para la conexión con el servidor
    private Socket socket;
    // Flujo de entrada para recibir mensajes desde el servidor
    private BufferedReader in;
    // Flujo de salida para enviar mensajes al servidor
    private PrintWriter out;
    // Consumidor para manejar los mensajes recibidos del servidor
    private Consumer<String> onMessageReceived;

    /**
     * Constructor de la clase ChatClient.
     * 
     * @param serverAddress Dirección del servidor.
     * @param serverPort Puerto del servidor.
     * @param onMessageReceived Consumidor para manejar mensajes entrantes.
     * @throws IOException Si hay un error al inicializar la conexión.
     */
    public ChatClient(String serverAddress, int serverPort, Consumer<String> onMessageReceived) throws IOException {
        // Establece conexión con el servidor
        this.socket = new Socket(serverAddress, serverPort);
        // Configura los flujos de entrada y salida
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        // Define el consumidor para manejar los mensajes recibidos
        this.onMessageReceived = onMessageReceived;
    }

    /**
     * Método para enviar un mensaje al servidor.
     * 
     * @param msg El mensaje a enviar.
     */
    public void sendMessage(String msg) {
        out.println(msg); // Envia el mensaje al servidor
    }

    /**
     * Método para iniciar la escucha de mensajes del servidor.
     * Ejecuta un hilo para escuchar de forma asíncrona.
     */
    public void startClient() {
        // Hilo para escuchar mensajes entrantes del servidor
        new Thread(() -> {
            try {
                String line;
                // Lee continuamente mensajes desde el servidor
                while ((line = in.readLine()) != null) {
                    // Procesa cada mensaje recibido usando el consumidor
                    onMessageReceived.accept(line);
                }
            } catch (IOException e) {
                e.printStackTrace(); // Manejo de errores
            }
        }).start();
    }

    /**
     * Método principal para ejecutar pruebas locales del cliente.
     * Este método no es necesario en la integración con una GUI u otros sistemas.
     */
    public static void main(String[] args) {
        try {
            // Prueba del cliente con una dirección de servidor y puerto
            ChatClient client = new ChatClient("127.0.0.1", 5000, System.out::println);
            client.startClient(); // Inicia el cliente

            // Envia algunos mensajes de prueba (puede comentarse en producción)
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while ((input = consoleInput.readLine()) != null) {
                client.sendMessage(input);
            }
        } catch (IOException e) {
            System.err.println("Error inicializando el cliente: " + e.getMessage());
        }
    }
}
