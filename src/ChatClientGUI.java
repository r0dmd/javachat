// Importamos las clases necesarias para el GUI y el manejo de eventos
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClientGUI extends JFrame {
    private JTextArea messageArea; // Área de texto para mostrar mensajes
    private JTextField textField; // Campo para escribir nuevos mensajes
    private JButton exitButton; // Botón de salir
    private ChatClient client;

    public ChatClientGUI() {
        super("JavaChat");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Variables de estilo
        Color backgroundColor = new Color(240, 240, 240);
        Color buttonColor = new Color(75, 75, 75);
        Color textColor = new Color(50, 50, 50);
        Font textFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        // Configuración del área de mensajes
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setBackground(backgroundColor);
        messageArea.setForeground(textColor);
        messageArea.setFont(textFont);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        // Pedimos al usuario que ingrese su nombre
        String name = JOptionPane.showInputDialog(this, "Ingresa tu nombre:", "Entrada de Nombre",
                JOptionPane.PLAIN_MESSAGE);
        this.setTitle("JavaChat - " + name);

        // Configuración del campo de texto para nuevos mensajes
        textField = new JTextField();
        textField.setFont(textFont);
        textField.setForeground(textColor);
        textField.setBackground(backgroundColor);
        textField.addActionListener(e -> {
            String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name + ": "
                    + textField.getText();
            client.sendMessage(message);
            textField.setText(""); // Limpiamos el campo de texto
        });

        // Configuración del botón de salir
        exitButton = new JButton("Salir");
        exitButton.setFont(buttonFont);
        exitButton.setBackground(buttonColor);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            String departureMessage = name + " ha salido del chat.";
            client.sendMessage(departureMessage);
            try {
                Thread.sleep(1000); // Esperamos un segundo para enviar el mensaje
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        });

        // Panel inferior con el campo de texto y el botón de salir
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Inicializamos y conectamos el cliente
        try {
            this.client = new ChatClient("127.0.0.1", 5000, this::onMessageReceived);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con el servidor", "Error de conexión",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    // Método para manejar mensajes recibidos
    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClientGUI().setVisible(true));
    }
}
