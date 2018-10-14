package com.iprogrammerr.gentle.request.binary;

public final class ConfigurablePattern implements BinaryPattern {

    private final byte[] pattern;

    public ConfigurablePattern(byte[] pattern) {
	this.pattern = pattern;
    }

    @Override
    public int index(byte[] content) {
	int index = -1;
	for (int i = 0; content.length - i >= this.pattern.length; ++i) {
	    if (content[i] != this.pattern[0]) {
		continue;
	    }
	    boolean found = true;
	    for (int j = 1; j < this.pattern.length; ++j) {
		if (content[i + j] != this.pattern[j]) {
		    found = false;
		    break;
		}
	    }
	    if (found) {
		index = i;
		break;
	    }
	}
	return index;
    }

    @Override
    public byte[] value() {
	return this.pattern;
    }

}
