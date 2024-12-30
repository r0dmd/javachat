# Chat Application

Este es un proyecto de aplicación de chat básica utilizando Java y Swing para la interfaz gráfica. Permite a múltiples usuarios conectarse a un servidor y enviarse mensajes en tiempo real. El servidor maneja las conexiones de los clientes, mientras que los clientes envían y reciben mensajes a través de una interfaz gráfica moderna y sencilla.

## Descripción

La aplicación consta de tres partes principales:

1. **ChatServer.java**: El servidor que escucha las conexiones de los clientes, recibe los mensajes y los retransmite a todos los usuarios conectados.
2. **ChatClient.java**: El cliente que se conecta al servidor, permite enviar mensajes y recibirlos desde otros usuarios.
3. **ChatClientGUI.java**: La interfaz gráfica del cliente, que usa Java Swing para mostrar los mensajes, permitir al usuario escribir y enviar mensajes, y gestionar la desconexión del servidor.

## Características

- **Conexión de múltiples usuarios**: El servidor acepta conexiones simultáneas de múltiples clientes.
- **Envío de mensajes en tiempo real**: Los clientes pueden enviar y recibir mensajes instantáneamente.
- **Interfaz gráfica**: Con una interfaz sencilla, moderna y estilizada usando Java Swing.
- **Entrada de nombre de usuario**: Los usuarios deben ingresar su nombre al inicio para personalizar su experiencia.
- **Mensaje de salida**: Cuando un usuario se desconecta, el resto de los usuarios recibe un mensaje de que ha dejado la conversación.

## Requisitos

- Java 8 o superior.
- Un editor de texto o IDE compatible con Java (como IntelliJ IDEA, Eclipse, NetBeans, etc.).

## Instrucciones de Uso

### 1. Clonar el repositorio

Si no tienes el proyecto localmente, puedes clonarlo desde GitHub:

``````
git clone <URL_DEL_REPOSITORIO>
cd chat-application
``````

### 2. Compilar y ejecutar el servidor

Primero, necesitas iniciar el servidor. Este se encarga de escuchar las conexiones de los clientes.

Abre una terminal en el directorio del proyecto y compila el servidor:

``````
javac ChatServer.java
``````

Luego, ejecuta el servidor:

``````
java ChatServer
``````

El servidor se iniciará en el puerto **5000**. Asegúrate de que este puerto esté disponible en tu máquina.

### 3. Compilar y ejecutar el cliente

Para el cliente, primero compila el código:

``````
javac ChatClient.java ChatClientGUI.java
``````

Después, ejecuta el cliente en una nueva terminal:

``````
java ChatClientGUI
``````

El cliente te pedirá que ingreses un nombre. Una vez lo ingreses, la ventana del chat aparecerá y podrás comenzar a enviar mensajes.

### 4. Usar la aplicación

- **Enviar mensajes**: Escribe tu mensaje en el campo de texto y presiona Enter para enviarlo.
- **Ver mensajes**: Los mensajes de otros usuarios aparecerán en el área de texto de la interfaz.
- **Salir del chat**: Haz clic en el botón **"Salir"** para desconectarte. Un mensaje será enviado al servidor informando a los demás usuarios que te has desconectado.

## Explicación del Código

### ChatServer.java

El servidor escucha en el puerto **5000** esperando que los clientes se conecten. Cada vez que un cliente se conecta, el servidor crea un nuevo hilo para manejar la comunicación con ese cliente. Los mensajes recibidos de un cliente se retransmiten a todos los demás clientes conectados.

### ChatClient.java

El cliente establece una conexión con el servidor y permite que el usuario envíe mensajes. Recibe los mensajes enviados por el servidor y los muestra en la interfaz gráfica. Los mensajes son enviados a través de un `PrintWriter` y se reciben usando un `BufferedReader`.

### ChatClientGUI.java

La interfaz gráfica es construida usando Java Swing. Incluye:

- Un `JTextArea` para mostrar los mensajes del chat.
- Un `JTextField` para que el usuario escriba sus mensajes.
- Un botón **"Exit"** para cerrar la aplicación y notificar al servidor que el usuario ha salido.

La aplicación también permite a los usuarios ingresar su nombre al inicio y muestra el nombre de cada usuario junto con un sello de tiempo en cada mensaje.

## Notas Adicionales

- Asegúrate de que el puerto 5000 esté libre y disponible en tu red local para la correcta conexión entre el servidor y los clientes.
- Si deseas usar un puerto diferente, asegúrate de modificar el valor del puerto en ambos, el servidor y los clientes, para que coincidan.
- Este proyecto puede ser expandido agregando funcionalidades como chat privado, emojis, o almacenamiento de mensajes.