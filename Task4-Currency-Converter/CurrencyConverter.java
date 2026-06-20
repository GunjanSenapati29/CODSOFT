import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyConverter {

    public static double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            String apiUrl = "https://open.er-api.com/v6/latest/" + baseCurrency;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            String jsonResponse = response.toString();

            Pattern pattern = Pattern.compile("\"" + targetCurrency + "\":([0-9.]+)");
            Matcher matcher = pattern.matcher(jsonResponse);

            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            } else {
                System.out.println("Target currency not found.");
            }

        } catch (Exception e) {
            System.out.println("Error fetching exchange rate: " + e.getMessage());
        }

        return -1;
    }

    public static String getCurrencySymbol(String currency) {
        switch (currency) {
            case "INR":
                return "₹";
            case "USD":
                return "$";
            case "EUR":
                return "€";
            case "GBP":
                return "£";
            case "JPY":
                return "¥";
            case "AUD":
                return "A$";
            case "CAD":
                return "C$";
            default:
                return currency + " ";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== Currency Converter =====");

        System.out.println("Available currencies: USD, INR, EUR, GBP, JPY, AUD, CAD");

        System.out.print("Enter base currency: ");
        String baseCurrency = sc.next().toUpperCase();

        System.out.print("Enter target currency: ");
        String targetCurrency = sc.next().toUpperCase();

        System.out.print("Enter amount to convert: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount. Amount must be greater than zero.");
            return;
        }

        double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);

        if (exchangeRate != -1) {
            double convertedAmount = amount * exchangeRate;

            System.out.println("\n===== Conversion Result =====");
            System.out.println("Base Currency: " + baseCurrency);
            System.out.println("Target Currency: " + targetCurrency);
            System.out.println("Exchange Rate: 1 " + baseCurrency + " = " + exchangeRate + " " + targetCurrency);
            System.out.printf("Converted Amount: %s%.2f%n", getCurrencySymbol(targetCurrency), convertedAmount);
        } else {
            System.out.println("Currency conversion failed.");
        }

        sc.close();
    }
}