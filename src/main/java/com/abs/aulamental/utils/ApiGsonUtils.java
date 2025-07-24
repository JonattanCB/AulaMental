package com.abs.aulamental.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public final class ApiGsonUtils {

    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .version(HttpClient.Version.HTTP_2)
            .build();

    private static final Gson GSON = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
            .serializeNulls()
            .create();

    private ApiGsonUtils() { }

    public static <T> T get(String url, Class<T> type)
            throws IOException, InterruptedException {
        return get(url, null, type);
    }

    public static <T> T get(String url, String bearerToken, Class<T> type)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = base(url).GET();
        addBearer(builder, bearerToken);

        HttpRequest request = builder.build();
        String json = send(request);
        return GSON.fromJson(json, type);
    }

    public static <B, R> R post(String url, B body, Class<R> type)
            throws IOException, InterruptedException {
        return post(url, null, body, type);
    }

    public static <B, R> R post(String url, String bearerToken,
                                B body, Class<R> type)
            throws IOException, InterruptedException {

        String jsonBody = GSON.toJson(body);

        HttpRequest.Builder builder = base(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json");
        addBearer(builder, bearerToken);

        HttpRequest request = builder.build();
        String json = send(request);
        return GSON.fromJson(json, type);
    }

    public static <B, R> HttpResponse<String> postRaw(String url, String bearerToken,
                                                      B body) throws IOException, InterruptedException {

        String jsonBody = GSON.toJson(body);

        HttpRequest.Builder builder = base(url)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json");
        addBearer(builder, bearerToken);

        HttpRequest request = builder.build();
        return HTTP.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpRequest.Builder base(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json");
    }

    private static void addBearer(HttpRequest.Builder builder, String token) {
        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }
    }

    private static String send(HttpRequest request)
            throws IOException, InterruptedException {

        HttpResponse<String> response = HTTP.send(request,
                HttpResponse.BodyHandlers.ofString());

        int sc = response.statusCode();
        if (sc >= 200 && sc < 300) {
            return response.body();
        }
        throw new IOException("HTTP " + sc + ": " + response.body());
    }
}
