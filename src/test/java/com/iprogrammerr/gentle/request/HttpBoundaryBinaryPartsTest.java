package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.binary.HttpBoundaryBinaryParts;
import com.iprogrammerr.gentle.request.mock.MockedBinary;
import com.iprogrammerr.gentle.request.multipart.FormPart;
import com.iprogrammerr.gentle.request.multipart.HttpFormPart;
import com.iprogrammerr.gentle.request.multipart.HttpMultipartForm;

public final class HttpBoundaryBinaryPartsTest {

    @Test
    public void canSplit() throws Exception {
	List<FormPart> parts = new ArrayList<>();
	parts.add(new HttpFormPart("secret", "secret"));
	parts.add(new HttpFormPart("secret2", "secret.png", "image/png", new MockedBinary().content()));
	String boundary = "abcde6";
	HttpMultipartForm multipartForm = new HttpMultipartForm(boundary, parts);
	HttpBoundaryBinaryParts hbbp = new HttpBoundaryBinaryParts("--" + boundary);
	List<byte[]> binaryParts = hbbp.parts(multipartForm.body());
	assertTrue(binaryParts.size() == parts.size());
	for (int i = 0; i < binaryParts.size(); i++) {
	    assertTrue(Arrays.equals(parts.get(i).parsed(), binaryParts.get(i)));
	}
    }
}
