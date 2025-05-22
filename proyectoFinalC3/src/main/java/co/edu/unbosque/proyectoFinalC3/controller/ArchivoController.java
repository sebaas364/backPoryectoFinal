package co.edu.unbosque.proyectoFinalC3.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.convertapi.client.ConvertApi;

import co.edu.unbosque.proyectoFinalC3.dto.ArchivoDTO;
import co.edu.unbosque.proyectoFinalC3.service.ArchivoService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/archivo")
@CrossOrigin(origins = { "*" })
public class ArchivoController {

	@Autowired
	private ArchivoService archivoServ;

	@PostMapping("convertirarchivo")
	public ResponseEntity<String> convertirArchivo(@RequestParam String nombreArchivo, @RequestParam String fromFormat,
			@RequestParam String toFormat) {

		int resultado = archivoServ.validarConvertirArchivo(nombreArchivo, fromFormat, toFormat);
		if (resultado == 0) {
			return ResponseEntity.ok("Conversión realizada con éxito, descargar archivo: "
					+ archivoServ.obtenerURLArchivoConv(nombreArchivo, fromFormat, toFormat));

		} else if (nombreArchivo.isBlank() || nombreArchivo == null || fromFormat == "" || fromFormat == null
				|| fromFormat.isBlank() || toFormat == "" || toFormat == null || toFormat.isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Se deben llenar todos los campos.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al convertir el archivo");
	}
}
