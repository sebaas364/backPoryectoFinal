package co.edu.unbosque.proyectoFinalC3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectoFinalC3.service.ArchivoService;

/**
 * Controlador REST para operaciones de conversión de archivos.
 * <p>
 * Proporciona endpoints para convertir archivos entre diferentes formatos.
 * </p>
 */
@RestController
@RequestMapping("/archivo")
@CrossOrigin(origins = { "*" })
public class ArchivoController {

	@Autowired
	private ArchivoService archivoServ;

    /**
     * Endpoint para convertir un archivo entre formatos.
     * 
     * @param nombreArchivo Nombre del archivo a convertir
     * @param fromFormat Formato origen del archivo
     * @param toFormat Formato destino para la conversión
     * @return ResponseEntity con el resultado de la operación:
     *         - 200 OK si la conversión fue exitosa (incluye URL de descarga)
     *         - 400 BAD_REQUEST si hay campos vacíos o error en la conversión
     */
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
