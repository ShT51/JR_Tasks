package com.javarush.task.task40.task4006;

import java.io.*;
import java.net.Socket;
import java.net.URL;

/* 
Отправка GET-запроса через сокет
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        getSite(new URL("http://javarush.ru/social.html"));
    }

    public static void getSite(URL url) {
        String host = url.getHost();
        String file = url.getFile();
        try {
            Socket clientSocket = new Socket(host, url.getDefaultPort());

            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter pw = new PrintWriter(outputStream);
            pw.println("GET " + file + " HTTP/1.1");
            pw.println("Host:" + host + "\r\n");
            pw.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String responseLine;

            while ((responseLine = bufferedReader.readLine()) != null) {
                System.out.println(responseLine);
            }
            bufferedReader.close();
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}