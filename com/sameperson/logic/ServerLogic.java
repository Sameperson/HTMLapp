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
                OutputStream os = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(os);
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
                } else if(this.getFileExtension(uri).equals("html")||this.getFileExtension(uri).equals("txt")) {
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
                } else {
                    downloadFile(os, uri);
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
        String address = "src/resources"+uri;
        File file = new File(address);
        Scanner fileReader = new Scanner(file);

        while(fileReader.hasNextLine()) {
            textRespond += fileReader.nextLine() + "\n";
        }
        return textRespond;
    }

    private void downloadFile(OutputStream os, String uri) throws IOException {
        String address = "src/resources" + uri;
        File file = new File(address);

        byte [] byteBuffer  = new byte [(int)file.length()];

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(byteBuffer, 0, byteBuffer.length);


        System.out.println("Sending " + uri + "(" + byteBuffer.length + " bytes)");
        os.write(byteBuffer, 0, byteBuffer.length);
        os.flush();
    }

    private String getFileExtension(String uri) {
        String[] splitter = uri.split("[/.]");
        return splitter[splitter.length-1];
    }
}