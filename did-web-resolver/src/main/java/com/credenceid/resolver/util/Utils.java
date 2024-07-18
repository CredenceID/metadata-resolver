package com.credenceid.resolver.util;

import com.credenceid.resolver.dto.Error;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

public class Utils {
    public static Error byteToErrorModel(final byte[] bytes) {
        Gson gson = new Gson();
        return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), Error.class);
    }
}
