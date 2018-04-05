package utils;

import java.io.*;
import java.util.Scanner;

public class Readers {
    public static String readFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName)));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static String readFileAsResourceStream(InputStream pInputStream) {

        if (pInputStream == null) {
            System.out.println("null!!!!!!!!!");
        }

        StringBuilder result = new StringBuilder();
        try {
            try (Scanner scanner = new Scanner(pInputStream)) {

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    result.append(line).append("\n");
                }

                scanner.close();

            } catch (Exception e) {
                e.printStackTrace();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

//    readFloatBuffer()
}
