package com.hazebyte.util;

import com.hazebyte.net.Network;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MojangClient {

    private static final String UUID_API_SERVER = "https://api.mojang.com/users/profiles/minecraft/%s";

    private static final String TEXTURE_API_SERVER = "https://sessionserver.mojang.com/session/minecraft/profile/%s";

    /**
     * This returns the UUID in string form. The UUID will not contain any hyphens and returns the raw
     * result.
     *
     * <p>We expect the response from {@value UUID_API_SERVER} to be along the following lines.
     *
     * <p>{ name: "Bart7567", id: "9779df3ed3e944d5aaba78700bf4946f" }
     *
     * @param playerName
     * @return the UUID from the player name, otherwise null if there is an error.
     */
    public static String getUUIDFromName(String playerName) throws ParseException {
        // Create the URL and get the response from the website.
        String url = String.format(UUID_API_SERVER, playerName);
        String response = Network.request(url);

        // If the response is null or empty, return.
        if (response == null || response.isEmpty()) {
            return null;
        }

        // Parse the player's UUID from the response.
        // This assumes the the response is always the same.
        // This will break if mojang changes their API.
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response);
        if (!json.containsKey("id")) {
            throw new NullPointerException("Unable to parse id from response: " + response);
        }

        String id = (String) json.getOrDefault("id", null);
        return id;
    }

    /**
     * Gets the texture from an UUID
     *
     * @param uuid
     * @return
     */
    public static String getTextureFromUUID(String uuid) throws ParseException {
        String url = String.format(TEXTURE_API_SERVER, uuid);
        String response = Network.request(url);

        // If the response is null or empty, return.
        if (response == null || response.isEmpty()) {
            return null;
        }

        // Parse the texture from the response
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response);
        if (!json.containsKey("properties")) {
            throw new NullPointerException("Unable to parse properties from response: " + response);
        }

        JSONArray jsonArray = (JSONArray) json.get("properties");
        for (Object current : jsonArray) {
            JSONObject currentJsonObj = (JSONObject) current;
            if (!currentJsonObj.get("name").equals("textures")) {
                continue;
            }

            String texture = (String) currentJsonObj.getOrDefault("value", null);
            return texture;
        }

        return null;
    }
}
