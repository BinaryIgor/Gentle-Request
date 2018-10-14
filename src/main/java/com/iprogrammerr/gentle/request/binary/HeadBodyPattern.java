package com.iprogrammerr.gentle.request.binary;

public final class HeadBodyPattern implements BinaryPattern {

    private final BinaryPattern base;

    private HeadBodyPattern(BinaryPattern base) {
	this.base = base;
    }

    public HeadBodyPattern() {
	this(new ConfigurablePattern("\r\n\r\n".getBytes()));
    }

    @Override
    public byte[] value() {
	return this.base.value();
    }

    @Override
    public int index(byte[] content) {
	return this.base.index(content);
    }

}
