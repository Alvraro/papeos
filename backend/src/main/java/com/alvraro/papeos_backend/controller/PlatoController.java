package com.alvraro.papeos_backend.controller;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alvraro.papeos_backend.model.Plato;
import com.alvraro.papeos_backend.model.dto.PlatoSummary;
import com.alvraro.papeos_backend.model.dto.SummaryMapper;
import com.alvraro.papeos_backend.repository.PlatoRepository;

@RestController
@RequestMapping("/platos")
public class PlatoController {
	@Autowired
	private PlatoRepository platoRepository;

	private final SummaryMapper summaryMapper = Mappers.getMapper(SummaryMapper.class);

	@PostMapping("/create")
	public ResponseEntity<PlatoSummary> createPlato(@RequestBody Plato plato) {
		Plato savedPlato = platoRepository.save(plato);
		return ResponseEntity.ok(summaryMapper.toPlatoSummary(savedPlato));
	}

	@PostMapping("/get")
	public ResponseEntity<PlatoSummary> getPlatoById(Integer id) {
		return platoRepository
			.findById(id)
			.map(savedPlato -> ResponseEntity.ok(summaryMapper.toPlatoSummary(savedPlato)))
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/search")
	public ResponseEntity<List<PlatoSummary>> findByNombreContainingIgnoreCase(@RequestBody(required = false) String partialName) {
		if(partialName == null || partialName.isBlank())
			return ResponseEntity.ok(summaryMapper.toPlatoSummaries(platoRepository.findAll()));
		return ResponseEntity.ok(summaryMapper.toPlatoSummaries(platoRepository.findByNombreContainingIgnoreCase(partialName)));
	}

	@PostMapping("/update")
	public ResponseEntity<PlatoSummary> updatePlato(@RequestBody Plato plato) {
		return platoRepository.findById(plato.getId())
			.map(_ -> {
				Plato updatedPlato = platoRepository.save(plato);
				return ResponseEntity.ok(summaryMapper.toPlatoSummary(updatedPlato));
			})
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deletePlato(Integer id) {
		return platoRepository.findById(id)
			.map(plato -> {
				platoRepository.delete(plato);
				return ResponseEntity.ok("Plato deleted successfully");
			})
			.orElseGet(() -> ResponseEntity.notFound().build());
	}
}