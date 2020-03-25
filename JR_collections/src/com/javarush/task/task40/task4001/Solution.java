package com.javarush.task.task40.task4001;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

/* 
POST, а не GET
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        solution.sendPost(new URL("https://enjlsz9h3w4e8.x.pipedream.net"), "name=Hulio&country=Tatuin");
    }

    public void sendPost(URL url, String urlParameters) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connection.setRequestMethod("POST"); <-- можно явно не указывать, так мы вызвали getOutputStream
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setDoOutput(true);

        // отправляем параметры
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8))) {
            bufferedWriter.write(urlParameters);
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException e) {
            System.out.println("Problems with POST");
            e.printStackTrace();
        }

        if (connection.getResponseCode() != 200) {
            System.err.println("connection failed");
        }

            // обрабатываем ответ
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String responseMessage = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
                System.out.println("Response: " + responseMessage);
            } catch (IOException e) {
                System.out.println("Problems with Response");
                e.printStackTrace();
            }
        /*String responseLine;
        StringBuilder response = new StringBuilder();
        while ((responseLine = bufferedReader.readLine()) != null) {
            response.append(responseLine);
        }
        bufferedReader.close();
        System.out.println("Response: " + response.toString());*/


    }
}
