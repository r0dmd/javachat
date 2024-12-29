import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ChatClientGUI extends JFrame {
    private JTextArea messageArea; // Área de texto para mostrar mensajes
    private JTextField textField; // Campo de texto para escribir mensajes
    private ChatClient client; // Cliente que gestiona la conexión al servidor

    public ChatClientGUI() {
        super("Chat Application");
        setSize(400, 500); // Dimensiones de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Cerrar la aplicación al cerrar la ventana

        // Área de texto no editable para mostrar mensajes
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        // Campo de texto para escribir mensajes
        textField = new JTextField();
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Validación: evitar mensajes vacíos
                String message = textField.getText().trim();
                if (!message.isEmpty()) {
                    client.sendMessage(message); // Enviar mensaje al servidor
                    textField.setText(""); // Limpiar campo de texto
                }
            }
        });
        add(textField, BorderLayout.SOUTH);

        // Inicializar y arrancar el cliente de chat
        try {
            this.client = new ChatClient("127.0.0.1", 5000, this::onMessageReceived); // Cliente conectado al servidor
            client.startClient(); // Inicia la conexión al servidor
        } catch (IOException e) {
            e.printStackTrace(); // Mostrar detalles del error en la consola
            // Mostrar mensaje de error al usuario
            JOptionPane.showMessageDialog(this, "Error connecting to the server", "Connection error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Salir de la aplicación si no se puede conectar
        }
    }

    // Método que maneja los mensajes recibidos del servidor
    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n")); // Agregar mensaje al área de texto
    }

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatClientGUI().setVisible(true); // Crear y mostrar la ventana
        });
    }
}
