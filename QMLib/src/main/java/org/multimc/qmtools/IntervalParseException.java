package org.multimc.qmtools;

import com.google.gson.JsonParseException;

public class IntervalParseException extends JsonParseException {
    public IntervalParseException(String message) {
        super(message);
    }
}
