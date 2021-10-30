package ru.netology;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ru.netology.Server.SERVER_HOST;
import static ru.netology.Server.SERVER_PORT;

public class Client {
    public static void main(String[] args) throws IOException {
        final InetSocketAddress socketAddress = new InetSocketAddress(SERVER_HOST, SERVER_PORT);

        final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
        try (final Scanner scanner = new Scanner(System.in);
             final SocketChannel socketChannel = SocketChannel.open()) {

            socketChannel.connect(socketAddress);

            while (true) {
                System.out.println("Введите строку");
                final String line = scanner.nextLine();
                if ("end".equals(line)) {
                    return;
                }

                socketChannel.write(ByteBuffer.wrap(line.getBytes(StandardCharsets.UTF_8)));
                final int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8));
                inputBuffer.clear();
            }
        }
    }
}