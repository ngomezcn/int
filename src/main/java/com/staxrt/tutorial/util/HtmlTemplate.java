package com.staxrt.tutorial.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HtmlTemplate {
    private String templatePath;
    private Map<String, String> placeholders;

    public HtmlTemplate(String templatePath) {
        this.templatePath = templatePath;
        this.placeholders = new HashMap<>();
    }

    public void assign(String placeholder, String value) {
        placeholders.put(placeholder, value);
    }

    public String build() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(templatePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }

        String templateContent = contentBuilder.toString();

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = entry.getKey();
            String value = entry.getValue();
            templateContent = templateContent.replace(placeholder, value);
        }

        return templateContent;
    }
}