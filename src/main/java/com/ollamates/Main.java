package com.ollamates;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.generate.OllamaStreamHandler;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static final String LLAMA3 = "llama3.1";
    private static final int HTTP_OK = 200;
    static final String host = "http://localhost:11434/";
    private final static String PROMPT = "who are you";
    public static void main(String[] args) {


        OllamaAPI ollamaAPI = new OllamaAPI(host);
        // define a stream handler (Consumer<String>)
        OllamaStreamHandler streamHandler = (s) -> {
            System.out.println(s);
        };
      //  ollamaAPI.setRequestTimeoutSeconds(120);
        // Should be called using separate thread to gain non blocking streaming effect.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Inside : " + Thread.currentThread().getName());
                    OllamaResult result = ollamaAPI.generate(LLAMA3,
                            "What is the capital of France? And what's France's connection with Mona Lisa?",
                            false, new OptionsBuilder().build(), streamHandler);
                    System.out.println("Full response: " + result.getResponse());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        System.out.println("Creating Thread...");
        Thread thread = new Thread(runnable);
        thread.start();




    }
}