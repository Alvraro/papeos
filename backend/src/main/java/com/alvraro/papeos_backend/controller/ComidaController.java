package com.alvraro.papeos_backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alvraro.papeos_backend.model.Comida;
import com.alvraro.papeos_backend.model.dto.ComidaSummary;
import com.alvraro.papeos_backend.model.dto.SummaryMapper;
import com.alvraro.papeos_backend.repository.ComidaRepository;

@RestController
@RequestMapping("/comidas")
public class ComidaController {
	@Autowired
	private ComidaRepository comidaRepository;

	private final SummaryMapper summaryMapper = Mappers.getMapper(SummaryMapper.class);

	@PostMapping("/create")
	public ResponseEntity<ComidaSummary> createComida(@RequestBody Comida comida) {
		Comida savedComida = comidaRepository.save(comida);
		return ResponseEntity.ok(summaryMapper.toComidaSummary(savedComida));
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<ComidaSummary> getComidaById(@RequestParam Integer id) {
		return comidaRepository
			.findById(id)
			.map(comida -> ResponseEntity.ok(summaryMapper.toComidaSummary(comida)))
			.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping("/get")
	public ResponseEntity<List<ComidaSummary>> getComidaByDateInterval(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"yyyy/MM/dd"}) LocalDate desde, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"yyyy/MM/dd"}) LocalDate hasta) {
		List<Comida> comidas = comidaRepository.findByFechaBetween(desde, hasta);
		return ResponseEntity.ok(summaryMapper.toComidaSummaries(comidas));
	}

	@PostMapping("/update")
	public ResponseEntity<ComidaSummary> updateComida(@RequestBody Comida comida) {
		return comidaRepository.findById(comida.getId())
			.map(_ -> {
				Comida updatedComida = comidaRepository.save(comida);
				return ResponseEntity.ok(summaryMapper.toComidaSummary(updatedComida));
			})
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteComida(Integer id) {
		return comidaRepository.findById(id)
			.map(Comida -> {
				comidaRepository.delete(Comida);
				return ResponseEntity.ok("Comida deleted successfully");
			})
			.orElseGet(() -> ResponseEntity.notFound().build());
	}	
}