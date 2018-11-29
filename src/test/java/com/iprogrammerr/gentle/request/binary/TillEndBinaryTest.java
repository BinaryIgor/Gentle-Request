package com.iprogrammerr.gentle.request.binary;

import static org.junit.Assert.assertThat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;

public final class TillEndBinaryTest {

	@Test
	public void canReadContent() throws Exception {
		byte[] content = new MockedBinary(1_000_000, 10_000_000).content();
		try (ServerSocket server = new ServerSocket(0)) {
			new Thread(() -> {
				try (Socket socket = new Socket(server.getInetAddress(), server.getLocalPort());
						BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream())) {
					Random random = new Random();
					int part = content.length / 4 + random.nextInt(content.length / 2);
					os.write(Arrays.copyOfRange(content, 0, part));
					Thread.sleep(10 + random.nextInt(1000));
					os.write(Arrays.copyOfRange(content, part, content.length));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}).start();
			assertThat(new TillEndBinary(new BufferedInputStream(server.accept().getInputStream())),
					new BinaryThatContains(content));
		}
	}
}
