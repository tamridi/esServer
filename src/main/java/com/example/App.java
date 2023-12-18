package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("Server in ascolto nella porta 8080");

        while (true) {
            Socket s = ss.accept();
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String a = in.readLine();
            System.out.println(a);
            String b = "";
            do {
                b = in.readLine();
                System.out.println(b);
            } while (!b.equals(""));
            String[] parts = a.split(" ");
            System.out.println(parts[1]);
            try {
                File myObj = new File(parts[1].substring(1));

                Scanner myReader = new Scanner(myObj);
        
                out.writeBytes("HTTP/1.1 200 OK\n");
                out.writeBytes("Date: " + LocalDateTime.now().toString() + "\n");
                out.writeBytes("Server: meucci-server");
                out.writeBytes("Content-Type: text/html; charset=UTF-8\n");
                out.writeBytes("Content-Length: " + myObj.length() + "\n");
                out.writeBytes("\n");

                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    out.writeBytes(data + "\n");
                }
                myReader.close();
        
            } catch (FileNotFoundException e) {
                out.writeBytes("HTTP/1.1 404 Not Found\n");
                out.writeBytes("Date: " + LocalDateTime.now().toString() + "\n");
                out.writeBytes("Server: meucci-server");
                out.writeBytes("Content-Type: text/plain; charset=UTF-8\n");
                out.writeBytes("Content-Length: 26\n");
                out.writeBytes("\n");
                out.writeBytes("The resource was not found\n");
            }
            s.close();
        }

    }

}
