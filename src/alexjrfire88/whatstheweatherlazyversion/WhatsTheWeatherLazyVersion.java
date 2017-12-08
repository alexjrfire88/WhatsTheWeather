package alexjrfire88.whatstheweatherlazyversion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhatsTheWeatherLazyVersion {
	
	private static DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	private static Path file = Paths.get("Super cool weather man thing boy man.txt.txt");
	
	public static void main(String[] args) throws IOException, InterruptedException {
		BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_16, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		while (true) {
			URL url = new URL("https://weather.com/weather/today/l/42.36,-71.06");
			URLConnection bridge = url.openConnection();
			bridge.setRequestProperty("User-Agent", "Chrome/23.0.1271.95");
			bridge.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(bridge.getInputStream()));
			String line = "";
			String combined = "";
			while ((line = reader.readLine()) != null) {
				combined += line + "\n";
			}
			Date date = new Date();
			System.out.println("\n" + format.format(date));
			Pattern pattern = Pattern.compile("\"temperature\":([0-9]+)");
			Matcher matcher = pattern.matcher(combined);
			String temperature = "";
			if (matcher.find()) {
				temperature = "Temperature: " + matcher.group(1) + "°F";
				System.out.println(temperature);
			}
			pattern = Pattern.compile("\"phrase\":\"([a-zA-Z ]+)");
			matcher = pattern.matcher(combined);
			matcher.find();
			String weather = "";
			if (matcher.find()) {
				weather = "Weather: " + matcher.group(1);
				System.out.println(weather);
			}
			writer.write(format.format(date) + "\n");
			writer.write(temperature + "\n");
			writer.write(weather + "\n\n");
			writer.flush();
			Thread.sleep(100000);
		}
	}
	
}