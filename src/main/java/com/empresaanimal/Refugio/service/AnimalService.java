package com.empresaanimal.Refugio.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.empresaanimal.Refugio.dto.AnimalCreateDto;
import com.empresaanimal.Refugio.dto.AnimalDto;
import com.empresaanimal.Refugio.dto.AnimalUpdateDto;
import com.empresaanimal.Refugio.model.Animal;

public interface AnimalService {
    Page<AnimalDto> obtenerAnimalesDisponibles(Pageable pageable);
    AnimalDto obtenerAnimalPorId(Long id);
    AnimalDto crearAnimal(AnimalCreateDto animalCreateDto); // Modificado para enviar correo
    AnimalDto actualizarAnimal(Long id, AnimalUpdateDto animalUpdateDto); // Modificado para enviar correo
    void eliminarAnimal(Long id);
    Page<AnimalDto> buscarPorEspecie(String especie, Pageable pageable);
    Page<AnimalDto> buscarPorNombre(String nombre, Pageable pageable);
    Map<String, Long> obtenerEstadisticasPorEspecie();
    List<AnimalDto> obtenerAnimalesQueRequierenAtencion();
    Optional<Animal> obtenerAnimalEntidadPorId(Long id);

    // Nuevo método para enviar notificaciones
    void enviarNotificacionPorEmail(AnimalDto animalDto, String accion);
}