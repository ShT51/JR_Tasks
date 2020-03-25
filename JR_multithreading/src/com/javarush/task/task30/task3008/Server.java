package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }


        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message message = connection.receive();
                if (message.getType() == MessageType.USER_NAME) {
                    String user = message.getData();
                    if (user != null && !user.isEmpty() && !connectionMap.containsKey(user)) {
                        connectionMap.put(user, connection);
                        connection.send(new Message(MessageType.NAME_ACCEPTED));
                        return user;
                    }
                }
            }
        }

        private void notifyUsers(Connection connection, String userName) throws IOException {

            for (Map.Entry<String, Connection> entry : connectionMap.entrySet()) {
                String name = entry.getKey();

                if (!name.equals(userName)) {
                    connection.send(new Message(MessageType.USER_ADDED, name));
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message userMessage = connection.receive();
                if (userMessage.getType() == MessageType.TEXT) {
                    String userMessageData = userName.concat(": ").concat(userMessage.getData());
                    Message broadcastMessage = new Message(MessageType.TEXT, userMessageData);
                    sendBroadcastMessage(broadcastMessage);
                } else {
                    ConsoleHelper.writeMessage("This message is not a TEXT type");
                }
            }
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage(String.format("Connection is established with remote address: %s", socket.getRemoteSocketAddress().toString()));
            try {
                Connection connection = new Connection(socket);
                String userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                notifyUsers(connection, userName);
                serverMainLoop(connection, userName);
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public static void sendBroadcastMessage(Message message) {
        for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
            Connection connection = pair.getValue();
            try {
                connection.send(message);
            } catch (IOException e) {
                System.out.println("Вы не смогли отправить сообщение");
            }
        }
    }

    public static void main(String[] args) {
        int port = ConsoleHelper.readInt();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is started");
            while (true) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
