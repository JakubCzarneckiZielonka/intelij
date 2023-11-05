import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Otodom {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        System.setProperty("http.agent", "Mozilla/5.0");
        URL otodom = new URL("https://www.otodom.pl/pl/wyniki/sprzedaz/mieszkanie/mazowieckie/wolominski/kobylka/kobylka?viewType=listing");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(otodom.openStream()));

        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null){
            stringBuilder.append(inputLine);
            stringBuilder.append(System.lineSeparator());
        }
        in.close();

        String content = stringBuilder.toString();
        for (int i = 0; i < content.length(); i++){
            i = content.indexOf("https://www.otodom.pl/oferta/");
            if (i < 0)
                break;
            String substring = content.substring(i);
            String link = substring.split(".html")[0];

            readWebsite(link, i + ".html");
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void readWebsite(String link, String fileName) throws IOException {
        URL otodom = new URL("https://www.otodom.pl/pl/wyniki/sprzedaz/mieszkanie/mazowieckie/wolominski/kobylka/kobylka?viewType=listing");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(otodom.openStream()));

        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null){
            stringBuilder.append(inputLine);
            stringBuilder.append(System.lineSeparator());
        }
        in.close();


        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,false));
        bw.write(stringBuilder.toString());
        bw.close();
    }

}
