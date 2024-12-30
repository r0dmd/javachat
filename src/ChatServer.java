// Importamos las clases necesarias para manejar sockets y streams
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 5000; // Puerto en el que el servidor escuchará
    private static Set<PrintWriter> clientWriters = new HashSet<>(); // Lista de flujos de salida para cada cliente

    public static void main(String[] args) {
        System.out.println("Servidor de chat iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Aceptamos nuevas conexiones de clientes
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Clase interna para manejar la conexión de cada cliente
    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                this.out = out;
                synchronized (clientWriters) {
                    clientWriters.add(out); // Añadimos el flujo de este cliente al conjunto global
                }
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + message);
                    broadcast(message); // Enviamos el mensaje a todos los clientes conectados
                }
            } catch (IOException e) {
                System.out.println("Error con cliente: " + e.getMessage());
            } finally {
                // Limpiamos al cliente al desconectarse
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Método para enviar mensajes a todos los clientes conectados
        private void broadcast(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}
