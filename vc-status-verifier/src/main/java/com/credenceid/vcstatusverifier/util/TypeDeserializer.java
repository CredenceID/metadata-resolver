package com.credenceid.vcstatusverifier.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TypeDeserializer extends JsonDeserializer<List<String>> {

    @Override
    public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        //current JSON node
        var node = jp.getCodec().readTree(jp);
        List<String> types = new ArrayList<>();

        if (node.isArray()) {
            // If the node is an array, iterate through it and add to the list
            ArrayNode arrayNode = (ArrayNode) node;
            for (Iterator<JsonNode> it = arrayNode.elements(); it.hasNext(); ) {
                types.add(jp.getCodec().treeToValue(it.next(), String.class));
            }
        } else if (node.isObject()) {
            // If the node is a single object, treat it as a single CredentialStatus
            types.add(jp.getCodec().treeToValue(node, String.class));
        }

        return types;
    }
}
