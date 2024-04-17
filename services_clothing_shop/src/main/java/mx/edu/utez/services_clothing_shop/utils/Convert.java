package mx.edu.utez.services_clothing_shop.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

public class Convert {
    public static String toJSON(Object object)  {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            Logger.getLogger(EncryptionFunctions.class.getName())
                    .severe("Error al convertir en JSON: " + e.getMessage());
        }
        return null;
    }

    public static String removeEmbedded(String json) {
        if (json == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            JsonNode rootNode = mapper.readTree(json);
            if (rootNode.isObject()) {
                removeEmbeddedJson((ObjectNode) rootNode);
                return mapper.writeValueAsString(rootNode);
            }
        } catch (Exception e) {
            Logger.getLogger(Convert.class.getName()).severe("Error al eliminar JSON anidado: " + e.getMessage());
        }
        return json;
    }

    private static void removeEmbeddedJson(ObjectNode rootNode) {
        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            JsonNode valueNode = field.getValue();
            if (valueNode.isObject() || valueNode.isArray()) {
                fields.remove();
            }
        }
    }




}
