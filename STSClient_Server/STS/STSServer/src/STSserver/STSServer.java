package STSserver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.*;
import java.nio.*;
import java.util.*;
import java.util.stream.*;

/*
	@author Joel&Eranus
*/
class STSServer
{
    public static void main(String[] args) throws IOException {
        String clientResponce = "";
        int charTrack = 0;
        String responce = "";

        //create socket at port 6789
        ServerSocket welcomeSocket = new ServerSocket(6789);
        Socket connectionSocket = welcomeSocket.accept();
        //establish clientServer connection
        BufferedReader inputFromClient = new BufferedReader
                (new InputStreamReader(connectionSocket.getInputStream()));
        //create outputSteam to client
        DataOutputStream outputToClient = new DataOutputStream(connectionSocket.getOutputStream());

        while (true) {
            clientResponce = inputFromClient.readLine();
            String[] splitted = clientResponce.trim().split(" ");
            System.out.println("Client Response: " + clientResponce);

            for (int i = Integer.parseInt(splitted[1]); i <= Integer.parseInt(splitted[2]); i++) {
                try (Stream<String> lines = Files.lines(Paths.get("alice.txt"))) {
                    responce = lines.skip(i).findFirst().get() + "\n";
                    responce = i + " " + charTrack + " " + responce;
                    //two spaces, so we subtract 2.
                    charTrack += responce.length() - String.valueOf(i).length() - String.valueOf(charTrack).length() - 2;
                    outputToClient.writeBytes(responce.toUpperCase());
                    System.out.println("TO CLIENT: " + responce.toUpperCase());
                }
            }
            //charTrack = 0;
            System.out.println("Done");
        }
    }

}

