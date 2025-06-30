package com.empresaanimal.Refugio.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.empresaanimal.Refugio.dto.AnimalCreateDto;
import com.empresaanimal.Refugio.dto.AnimalDto;
import com.empresaanimal.Refugio.dto.AnimalUpdateDto;
import com.empresaanimal.Refugio.model.Animal;
import com.empresaanimal.Refugio.repository.AnimalRepository;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<AnimalDto> obtenerAnimalesDisponibles(Pageable pageable) {
        return animalRepository.findByDisponibleAdopcionTrue(pageable)
                .map(animal -> modelMapper.map(animal, AnimalDto.class));
    }

   @Override
public AnimalDto obtenerAnimalPorId(Long id) {
    Animal animal = animalRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
    return modelMapper.map(animal, AnimalDto.class);
}

    @Override
public void enviarNotificacionPorEmail(AnimalDto animalDto, String mensaje) {
    // Implement the logic to send email notification here
    // This is just a placeholder - you'll need to implement actual email sending logic
    System.out.println("Enviando notificación por email para animal: " + animalDto.getNombre());
    System.out.println("Mensaje: " + mensaje);
    
    // In a real implementation, you would:
    // 1. Get the email address from somewhere (maybe from animalDto or another service)
    // 2. Use an email service to send the actual email
    // 3. Handle any exceptions
}

    @Override
    public AnimalDto crearAnimal(AnimalCreateDto animalCreateDto) {
        Animal animal = modelMapper.map(animalCreateDto, Animal.class);
        animal = animalRepository.save(animal);
        return modelMapper.map(animal, AnimalDto.class);
    }

    @Override
    public AnimalDto actualizarAnimal(Long id, AnimalUpdateDto animalUpdateDto) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Animal no encontrado"));
        modelMapper.map(animalUpdateDto, animal);
        animal = animalRepository.save(animal);
        return modelMapper.map(animal, AnimalDto.class);
    }

    @Override
    public void eliminarAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    @Override
    public Page<AnimalDto> buscarPorEspecie(String especie, Pageable pageable) {
        return animalRepository.findByEspecieAndDisponibleAdopcionTrue(especie, pageable)
                .map(animal -> modelMapper.map(animal, AnimalDto.class));
    }

    @Override
    public Page<AnimalDto> buscarPorNombre(String nombre, Pageable pageable) {
        return animalRepository.findByNombreContainingIgnoreCaseAndDisponibleAdopcionTrue(nombre, pageable)
                .map(animal -> modelMapper.map(animal, AnimalDto.class));
    }

    @Override
    public Map<String, Long> obtenerEstadisticasPorEspecie() {
        List<Object[]> resultados = animalRepository.countByEspecie();
        Map<String, Long> estadisticas = new HashMap<>();
        for (Object[] fila : resultados) {
            estadisticas.put((String) fila[0], (Long) fila[1]);
        }
        return estadisticas;
    }

    @Override
    public List<AnimalDto> obtenerAnimalesQueRequierenAtencion() {
        return animalRepository.findAnimalesQueRequierenAtencion()
                .stream()
                .map(animal -> modelMapper.map(animal, AnimalDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Animal> obtenerAnimalEntidadPorId(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
