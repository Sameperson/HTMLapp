package com.sameperson.logic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerLogic {


    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            Socket clientSocket = null;
            while(true) {
                clientSocket = serverSocket.accept();
                InputStreamReader inputReader = new InputStreamReader(clientSocket.getInputStream());
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader reader = new BufferedReader(inputReader);

                String userRequest = reader.readLine();
                String uri = "";
                if(userRequest!=null) {
                    uri = getURI(userRequest);
                }

                System.out.println(userRequest);

                if(uri.equals("/")|uri.equals("/favicon.ico")|uri.equals("")) {
                    writer.print("HTTP/1.1 200 OK\r\n" +
                            "\r\n" +
                            "<html>\r\n" +
                            "<head>\r\n" +
                            "  <title>WELCOME TO SERVER APP!</title>\r\n" +
                            "</head>\r\n" +
                            "<body>\r\n" +
                            "  <a href=\'calculator.html\'>Calculator</a>\r\n" +
                            "</body>\r\n" +
                            "</html>");
                } else if(this.getExtension(uri).equals("png")) {
                    writer.print(readImage(uri));
                } else {
                    try {
                        writer.print(readTextFile(uri));
                    } catch(FileNotFoundException fnfe) {
                        writer.print("HTTP/1.1 200 OK\r\n" +
                                "\r\n" +
                                "<html>\r\n" +
                                "<head>\r\n" +
                                "  <title>An Example Page</title>\r\n" +
                                "</head>\r\n" +
                                "<body>\r\n" +
                                "Sorry, but file <b>" +
                                uri +
                                "</b> doesn't exist!\r\n" +
                                "</body>\r\n" +
                                "</html>");
                    }
                }

                writer.flush();
                writer.close();
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("IO error occurred: " + e.toString());
        }
    }

    private String getURI(String header) {
        //System.out.println(header);
        return header.split(" ")[1];
    }

    private String readTextFile(String uri) throws IOException {
        String textRespond = "HTTP/1.1 200 OK\r\n" +
                "\r\n";
        String address = "resources"+uri;
        File file = new File(address);
        Scanner fileReader = new Scanner(file);

        /* textRespond += "<head>\r\n" +
                "  <title>" + uri + "/title>\r\n" +
                "</head>\r\n"; */

        while(fileReader.hasNextLine()) {
            textRespond += fileReader.nextLine() + "\n";
        }
        return textRespond;
    }

    private String readImage(String uri) {
        String imageRespond = "HTTP/1.1 200 OK\r\n" +
                "\r\n";
        String address = "resources" +uri;
        File file = new File(address);
        imageRespond+= "<html>\n" +
                "<body>\n" +
                "\n" +
                "<img src=\"" + uri + "\" alt=\"" + uri + "\" style=\"width:304px;height:228px;\">\n" +
                "\n" +
                "</body>";

        return imageRespond;
    }

    private String getExtension(String uri) {
        String[] splitter = uri.split("[/.]");
        return splitter[splitter.length-1];
    }
}