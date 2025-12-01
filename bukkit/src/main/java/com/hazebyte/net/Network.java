package com.hazebyte.net;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class Network {

    /**
     * Perform a GET request to a website and return the response.
     *
     * @param website
     * @return the network response
     */
    public static String request(String website) {
        return request(website, "GET");
    }

    /**
     * Perform a request to a website and return the response.
     *
     * @param website
     * @param method
     * @return the network response
     */
    public static String request(String website, String method) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(website);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            int status = connection.getResponseCode();
            try (InputStreamReader streamReader =
                            new InputStreamReader(
                                    status > 299 ? connection.getErrorStream() : connection.getInputStream());
                    BufferedReader reader = new BufferedReader(streamReader)) {
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            }
        } catch (Exception ex) {
            CorePlugin.getPlugin()
                    .getLogger()
                    .log(
                            Level.WARNING,
                            "Failed to perform HTTP " + method + " request to " + website,
                            ex);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
