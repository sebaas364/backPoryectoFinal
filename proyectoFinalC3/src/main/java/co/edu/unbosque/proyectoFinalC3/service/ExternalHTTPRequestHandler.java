package co.edu.unbosque.proyectoFinalC3.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.convertapi.client.Config;
import com.convertapi.client.ConversionResult;
import com.convertapi.client.ConversionResultFile;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	public ExternalHTTPRequestHandler() {
	}

	public static String doGetAndParse(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();
		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Status code -> " + response.statusCode());

		String uglyJson = response.body();

		return prettyPrintUsingGson(uglyJson);
	}

	public static String prettyPrintUsingGson(String uglyJson) {
		Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
		JsonElement jsonElement = JsonParser.parseString(uglyJson);
		String prettyJsonString = gson.toJson(jsonElement);
		return prettyJsonString;
	}

	public static String doPost(String url, String json) {

		HttpRequest solicitud = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(url)).header("Content-Type", "application/json").build();

		HttpResponse<String> respuesta = null;
		try {
			respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("doPost no funciono");
		}
		return respuesta.statusCode() + "\n" + respuesta.body();
	}
	
	public static String convertApi(String fromFormat, String toFormat, String direccionArchivo, String direccionGuardado) {
		
		Config.setDefaultApiCredentials("secret_gbOobMcpbN7ucGqY");
		
		try {
			
			CompletableFuture<ConversionResult> resultado = ConvertApi.convert(fromFormat, toFormat,
				    new Param("File", Paths.get(direccionArchivo)));
			
			resultado.get().saveFile(Paths.get(direccionGuardado)).get();
			
			ConversionResultFile file = resultado.get().getFile(0);
			return file.getUrl();
			
			
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("a");
		}
		return "";
		
	}
	
}
