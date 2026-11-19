package com.alvraro.papeos_backend.model.dto;

import java.time.LocalDate;
import java.util.List;

public record ComidaSummary(Integer id, LocalDate fecha, String tipo, List<String> platos) {}
