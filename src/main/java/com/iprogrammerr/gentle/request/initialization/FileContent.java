package com.iprogrammerr.gentle.request.initialization;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import com.iprogrammerr.gentle.request.binary.PacketsBinary;

public final class FileContent implements UnreliableInitialization<byte[]> {

	private final File source;

	public FileContent(File source) {
		this.source = source;
	}

	@Override
	public byte[] value() throws Exception {
		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(this.source))) {
			return new PacketsBinary(is, this.source.length()).content();
		}
	}
}
