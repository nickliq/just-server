package ru.nickliq;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class UdpServer {
    private final int port;
    private final List<String> messageStorage; // Хранилище для сообщений в памяти

    public UdpServer(int port) {
        this.port = port;
        this.messageStorage = new ArrayList<>();
    }

    public void start() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP сервер запущен на порту " + port);

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Получено сообщение: " + message);

                // Сохранение сообщения в памяти
                storeMessage(message);
            }
        } catch (SocketException e) {
            System.err.println("Ошибка сокета: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }

    private void storeMessage(String message) {
        messageStorage.add(message);
        System.out.println("Сообщение сохранено в памяти. Всего сообщений: " + messageStorage.size());
    }

    public List<String> getMessageStorage() {
        return messageStorage;
    }

    public static void main(String[] args) {
        int port = 9876; // Порт для UDP сервера
        UdpServer server = new UdpServer(port);
        server.start();
    }
}