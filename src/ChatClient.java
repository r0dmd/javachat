// Importamos las clases necesarias para manejar sockets, streams y funciones
import java.io.*;
import java.net.*;
import java.util.function.Consumer;

public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Consumer<String> onMessageReceived;

    // Constructor que inicializa la conexión al servidor
    public ChatClient(String serverAddress, int serverPort, Consumer<String> onMessageReceived) throws IOException {
        this.socket = new Socket(serverAddress, serverPort);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.onMessageReceived = onMessageReceived;
    }

    // Método para enviar mensajes al servidor
    public void sendMessage(String msg) {
        out.println(msg);
    }

    // Método para iniciar el cliente y escuchar mensajes del servidor
    public void startClient() {
        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    onMessageReceived.accept(line); // Pasamos el mensaje recibido al GUI
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
