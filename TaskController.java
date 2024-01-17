package com.example.demo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController("TaskController")
public class TaskController {
    private  int maxValue = Integer.MIN_VALUE;
    private  String maxPath = "";


    @GetMapping("/test")
    public void test() throws Exception {
        String url = "https://github.com/utegsk/test/blob/main/products.json";
        String response = sendGetRequest(url);
        JsonNode rootNode = new ObjectMapper().readTree(response);
        rootNode=rootNode.get("payload");
        rootNode=rootNode.get("blob");
        rootNode=rootNode.get("rawLines");
        String rawResp=rootNode.toPrettyString();
        rawResp=rawResp.replace("[","");
        rawResp=rawResp.replace("]","");
        rawResp=rawResp.replace("\", \"","");
        rawResp=rawResp.substring(2,rawResp.length());
        rawResp=rawResp.substring(0,rawResp.length()-2);
        rawResp=rawResp.replace("\\","");
        rootNode=new ObjectMapper().readTree(rawResp);
        System.out.println("\n\nTask 1\n");
        printJsonStructure(rootNode, "");
        System.out.println("\n\nTask 2");
        findMaxValue(rootNode, "");
        System.out.println("Correct answer is: " + maxPath + ": " + maxValue);
    }

    private String sendGetRequest(String url) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to Fetch Request Json From Url " + response.statusCode());
        }
    }

    private  void printJsonStructure(JsonNode node, String indent) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                System.out.println(indent + entry.getKey());
                if (entry.getValue().isTextual() && entry.getValue().asText().contains(entry.getKey())) {
                    printJsonStructure(entry.getValue(), indent + "....");
                } else {
                    printJsonStructure(entry.getValue(), indent + "..");
                }
            });
        }
    }
    private  void findMaxValue(JsonNode node, String path) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                findMaxValue(entry.getValue(), path + entry.getKey() + " -> ");
            });
        } else if (node.isInt()) {
            int value = node.asInt();
            if (value > maxValue) {
                maxValue = value;
                maxPath = path.substring(0, path.length() - 4);
            }
        }
    }


}



