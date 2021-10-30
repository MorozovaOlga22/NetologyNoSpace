package ru.netology;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static final String SERVER_HOST = "127.0.0.1";
    public static final int SERVER_PORT = 30_666;

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(SERVER_PORT));

        try (final SocketChannel socketChannel = serverSocketChannel.accept()) {
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

            while (socketChannel.isConnected()) {
                final int bytesCount = socketChannel.read(inputBuffer);
                if (bytesCount == -1) {
                    return;
                }

                final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                inputBuffer.clear();
                final String resultMsg = msg.replace(" ", "");
                socketChannel.write(ByteBuffer.wrap(resultMsg.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }
}