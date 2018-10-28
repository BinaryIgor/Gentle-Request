package com.iprogrammerr.gentle.request.initalization;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.binary.PacketsBinary;
import com.iprogrammerr.gentle.request.initialization.FileContent;

public final class FileContentThatContains extends TypeSafeMatcher<FileContent> {

	private final File source;

	public FileContentThatContains(File source) {
		this.source = source;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(FileContent item) {
		boolean matched;
		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(this.source))) {
			byte[] content = new PacketsBinary(is, this.source.length()).content();
			assertTrue(Arrays.equals(content, item.value()));
			matched = true;
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
