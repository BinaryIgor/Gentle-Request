package com.iprogrammerr.gentle.request.multipart;

public interface FormPart extends Part {

    String name() throws Exception;

    String filename() throws Exception;
}
