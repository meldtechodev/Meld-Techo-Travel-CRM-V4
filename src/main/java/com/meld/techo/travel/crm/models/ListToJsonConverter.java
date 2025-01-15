package com.meld.techo.travel.crm.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

@Converter
public class ListToJsonConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
//            return objectMapper.writeValueAsString(attribute);
        	
        	String jsonString = objectMapper.writeValueAsString(attribute);
            System.out.println("Converted to JSON: " + jsonString); // Debug log
            return jsonString;
        } catch (Exception e) {
            throw new RuntimeException("Error converting list to JSON string", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
//            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {});
        	
        	System.out.println("Converting JSON to List: " + dbData); // Debug log
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON string to list", e);
        }
    }
}
