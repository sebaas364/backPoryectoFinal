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

/**
 * Clase utilitaria para manejar solicitudes HTTP externas y conversión de archivos.
 * <p>
 * Proporciona métodos para:
 * <ul>
 *   <li>Realizar solicitudes HTTP GET y POST</li>
 *   <li>Formatear respuestas JSON</li>
 *   <li>Convertir archivos entre formatos usando ConvertAPI</li>
 * </ul>
 * </p>
 */
public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

    /**
     * Constructor por defecto.
     */
	public ExternalHTTPRequestHandler() {
		
	}

    /**
     * Realiza una solicitud HTTP GET y devuelve la respuesta formateada.
     * 
     * @param url URL del endpoint a consultar
     * @return Respuesta formateada en JSON
     */
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

    /**
     * Formatea un string JSON para mejorar su legibilidad.
     * 
     * @param uglyJson JSON sin formatear
     * @return JSON formateado
     */
	public static String prettyPrintUsingGson(String uglyJson) {
		Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
		JsonElement jsonElement = JsonParser.parseString(uglyJson);
		String prettyJsonString = gson.toJson(jsonElement);
		return prettyJsonString;
	}

    /**
     * Realiza una solicitud HTTP POST con un cuerpo JSON.
     * 
     * @param url URL del endpoint
     * @param json Cuerpo de la solicitud en formato JSON
     * @return Respuesta que incluye código de estado y cuerpo
     */
	public static String doPost(String url, String json) {
		HttpRequest solicitud = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(url)).header("Content-Type", "application/json").build();
		HttpResponse<String> respuesta = null;
		try {
			respuesta = HTTP_CLIENT.send(solicitud, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			System.out.println("doPost no funciono");
		}
		return respuesta.statusCode() + "\n" + respuesta.body();
	}

    /**
     * Convierte un archivo entre formatos usando ConvertAPI.
     * 
     * @param fromFormat Formato origen
     * @param toFormat Formato destino
     * @param direccionArchivo Ruta del archivo a convertir
     * @return URL del archivo convertido o string vacío en caso de error
     */
	public static String convertApi(String fromFormat, String toFormat, String direccionArchivo) {
		Config.setDefaultApiCredentials("secret_gbOobMcpbN7ucGqY");
		try {
			CompletableFuture<ConversionResult> resultado = ConvertApi.convert(fromFormat, toFormat,
					new Param("File", Paths.get(direccionArchivo)));
			// resultado.get().saveFile(Paths.get(direccionGuardado)).get();
			ConversionResultFile file = resultado.get().getFile(0);
			return file.getUrl();
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
			System.out.println("a");
		}
		return "";
	}

}
