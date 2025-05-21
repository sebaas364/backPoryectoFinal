package co.edu.unbosque.proyectoFinalC3.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectoFinalC3.dto.ArchivoDTO;
import co.edu.unbosque.proyectoFinalC3.service.ArchivoService;

@RestController
@RequestMapping("/archivo")
@CrossOrigin(origins = { "*" })
public class ArchivoController {
	
	@Autowired
	private ArchivoService archivoServ;
	
}
