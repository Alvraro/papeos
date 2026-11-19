package com.alvraro.papeos_backend.model.dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alvraro.papeos_backend.model.Alimento;
import com.alvraro.papeos_backend.model.CategoriaAlimento;
import com.alvraro.papeos_backend.model.Comida;
import com.alvraro.papeos_backend.model.Plato;
import com.alvraro.papeos_backend.model.TipoComida;

@Mapper
public interface SummaryMapper {
	@Mapping(target = "categoria", expression = "java(mapCategoria(alimento.getCategoria()))")
	AlimentoSummary toAlimentoSummary(Alimento alimento);
	List<AlimentoSummary> toAlimentoSummaries(List<Alimento> alimentos);

	PlatoSummary toPlatoSummary(Plato plato);
	List<PlatoSummary> toPlatoSummaries(List<Plato> platos);

	@Mapping(target = "tipo", expression = "java(mapTipo(comida.getTipo()))")
	ComidaSummary toComidaSummary(Comida comida);
	List<ComidaSummary> toComidaSummaries(List<Comida> comidas);

	default String mapCategoria(CategoriaAlimento categoria) {
		return categoria == null ? null : categoria.getNombre();
	}

	default String mapTipo(TipoComida tipo) {
		return tipo == null ? null : tipo.toString();
	}
}