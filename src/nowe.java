import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class nowe {
    public static void main(String[] args) {
        String saveDirectory = "C:/MojeOferty"; // Zmień na żądany katalog
        File directory = new File(saveDirectory);
        directory.mkdirs();

        try {
            URL otodom = new URL("https://www.otodom.pl/pl/wyniki/sprzedaz/dom/mazowieckie/wolominski/kobylka/kobylka");
            HttpURLConnection connection = (HttpURLConnection) otodom.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder pageContent = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    pageContent.append(inputLine).append("\n");
                }
                in.close();

                String pattern = "https://www.otodom.pl/pl/oferta/[^\"]+";
                Pattern regex = Pattern.compile(pattern);
                Matcher matcher = regex.matcher(pageContent.toString());

                while (matcher.find()) {
                    String offerUrl = matcher.group();
                    String fileName = offerUrl.substring(offerUrl.lastIndexOf("/") + 1) + ".html";
                    saveOfferToFile(offerUrl, fileName, saveDirectory);
                }
            } else {
                System.out.println("Failed to fetch data. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveOfferToFile(String offerUrl, String fileName, String saveDirectory) throws IOException {
        URL offer = new URL(offerUrl);
        HttpURLConnection offerConnection = (HttpURLConnection) offer.openConnection();
        offerConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int offerResponseCode = offerConnection.getResponseCode();

        if (offerResponseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader offerReader = new BufferedReader(new InputStreamReader(offerConnection.getInputStream()));
            String offerInputLine;
            StringBuilder offerContent = new StringBuilder();

            while ((offerInputLine = offerReader.readLine()) != null) {
                offerContent.append(offerInputLine).append("\n");
            }
            offerReader.close();

            String fullPath = saveDirectory + "/" + fileName;
            BufferedWriter offerWriter = new BufferedWriter(new FileWriter(fullPath, false));
            offerWriter.write(offerContent.toString());
            offerWriter.close();
        }
    }
}