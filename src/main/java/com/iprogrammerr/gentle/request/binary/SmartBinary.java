package com.iprogrammerr.gentle.request.binary;

import org.json.JSONObject;

public interface SmartBinary {

    byte[] value();

    String stringValue();

    JSONObject jsonValue();
}
