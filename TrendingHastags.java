import java.util.*;
import java.util.regex.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TrendingHastags {
    public static void main(String[] args) {
        // Sample tweets dataset
        List<Tweet> tweets = Arrays.asList(
            new Tweet(135, 13, "Enjoying a great start to the day. #HappyDay #MorningVibes", "2024-02-01"),
            new Tweet(137, 14, "Another #HappyDay with good vibes! #FeelGood", "2024-02-02"),
            new Tweet(136, 15, "Productivity peaks! #WorkLife #ProductiveDay", "2024-02-04"),
            new Tweet(137, 16, "Exploring new tech frontiers. #TechLife #Innovation", "2024-02-06"),
            new Tweet(136, 17, "Gratitude for today's moments. #HappyDay #Thankful", "2024-02-07"),
            new Tweet(137, 18, "Innovation drives us. #TechLife #FutureTech", "2024-02-07"),
            new Tweet(135, 19, "Connecting with nature's serenity. #Nature #Peaceful", "2024-02-08")
        );

        // Process hashtags
        Map<String, Integer> hashtagCounts = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Tweet tweet : tweets) {
            LocalDate tweetDate = LocalDate.parse(tweet.tweetDate, formatter);
            if (tweetDate.getYear() == 2024 && tweetDate.getMonthValue() == 2) { // Filter February 2024 tweets
                Matcher matcher = Pattern.compile("#\\w+").matcher(tweet.text);
                while (matcher.find()) {
                    String hashtag = matcher.group();
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                }
            }
        }

        // Sort hashtags by count (descending) and then by hashtag (descending)
        List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCounts.entrySet());
        sortedHashtags.sort((a, b) -> {
            if (!b.getValue().equals(a.getValue())) {
                return b.getValue() - a.getValue(); // Sort by count descending
            }
            return b.getKey().compareTo(a.getKey()); // Sort by hashtag descending
        });

        // Print top 3 hashtags
        System.out.println("+------------+-------+");
        System.out.println("| Hashtag    | Count |");
        System.out.println("+------------+-------+");
        for (int i = 0; i < Math.min(3, sortedHashtags.size()); i++) {
            System.out.printf("| %-10s | %5d |\n", sortedHashtags.get(i).getKey(), sortedHashtags.get(i).getValue());
        }
        System.out.println("+------------+-------+");
    }
}

// Tweet class to represent tweets
class Tweet {
    int userId;
    int tweetId;
    String text;
    String tweetDate;

    public Tweet(int userId, int tweetId, String text, String tweetDate) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.text = text;
        this.tweetDate = tweetDate;
    }
}
