
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Scanner;

public class HackerNews{

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String HN_API_BASE_URL = "https://hacker-news.firebaseio.com/v0/";

    public static long[] hackerNewsTopStories() {
        HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create(HN_API_BASE_URL + "topstories.json"))
         .build();

         try{
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            Scanner scanner = new Scanner(response.body()).useDelimiter("[,\\[\\]]");

            return scanner.tokens().mapToLong(Long::parseLong).toArray();
         } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
         } finally {
            
         }
         return new long [0];
    }

    public static long[] hackerNewsTopStories(int limit) {
        HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create(HN_API_BASE_URL + "topstories.json"))
         .build();

         try{
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            Scanner scanner = new Scanner(response.body()).useDelimiter("[,\\[\\]]");

            return scanner.tokens().mapToLong(Long::parseLong).limit(limit).toArray();
         } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
         } finally {
            
         }
         return new long [0];
    }

    public static String news(long id){
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(HN_API_BASE_URL + "item/" + id + ".json"))
        .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {

        int numberOfNews = 10;
        
        long[] hn = hackerNewsTopStories(numberOfNews);
        System.out.println("Number of HN stories: " + hn.length + "\n-----------");
        Arrays.stream(hn).forEach(n -> System.out.println(news(n) + "\n\n"));

    }
}