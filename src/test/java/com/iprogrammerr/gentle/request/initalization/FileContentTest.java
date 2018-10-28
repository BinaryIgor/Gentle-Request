package com.iprogrammerr.gentle.request.initalization;

import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import com.iprogrammerr.gentle.request.initialization.FileContent;
import com.iprogrammerr.gentle.request.matching.FunctionThatThrowsException;

public final class FileContentTest {

	@Test
	public void canReadContent() {
		File file = new File(String.format("src%stest%sresources%sjava.png", File.separator,
				File.separator, File.separator));
		assertThat(new FileContent(file), new FileContentThatContains(file));
	}

	@Test
	public void shouldFailOnInvalidFile() {
		assertThat(() -> new FileContent(new File("???")).value(),
				new FunctionThatThrowsException());
	}
}
