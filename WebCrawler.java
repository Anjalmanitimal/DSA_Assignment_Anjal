import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class WebCrawler {
    private ExecutorService executorService;
    private BlockingQueue<String> urlQueue;

    public WebCrawler() {
        executorService = Executors.newFixedThreadPool(10);
        urlQueue = new LinkedBlockingQueue<>();
    }

    public void addUrl(String url) {
        urlQueue.add(url);
    }

    public void startCrawling() {
        while (!urlQueue.isEmpty()) {
            String url = urlQueue.poll();
            executorService.submit(new CrawlTask(url));
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    private class CrawlTask implements Runnable {
        private String url;

        public CrawlTask(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                processContent(response.toString());

            } catch (Exception e) {
                System.err.println("Error crawling URL: " + url);
                e.printStackTrace();
            }
        }

        private void processContent(String content) {
            System.out.println("Crawled content from " + url + ": " + content.substring(0, Math.min(100, content.length())));
        }
    }

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        crawler.addUrl("https://example.com");
        crawler.addUrl("https://example.org");
        crawler.startCrawling();
        crawler.shutdown();
    }
}