package com.empresaanimal.Refugio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada para cuando un recurso no es encontrado
 * Se utiliza cuando se busca un animal, empleado, adoptante, etc. que no existe
 * Retorna un status HTTP 404 (Not Found)
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    private String resourceName;
    private String fieldName;
    private Object fieldValue;
    
    /**
     * Constructor con mensaje personalizado
     * @param message Mensaje de error personalizado
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor con mensaje y causa
     * @param message Mensaje de error
     * @param cause Causa de la excepción
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructor con detalles específicos del recurso
     * @param resourceName Nombre del recurso (ej: "Animal", "Empleado")
     * @param fieldName Nombre del campo (ej: "id", "nombre")
     * @param fieldValue Valor del campo buscado
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    
    /**
     * Constructor para ID específico
     * @param resourceName Nombre del recurso
     * @param id ID del recurso buscado
     */
    public ResourceNotFoundException(String resourceName, Long id) {
        this(resourceName, "id", id);
    }
    
    /**
     * Constructor para ID específico (int)
     * @param resourceName Nombre del recurso
     * @param id ID del recurso buscado
     */
    public ResourceNotFoundException(String resourceName, Integer id) {
        this(resourceName, "id", id);
    }
    
    // Getters
    public String getResourceName() {
        return resourceName;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public Object getFieldValue() {
        return fieldValue;
    }
    
    // Métodos estáticos de conveniencia para casos comunes
    public static ResourceNotFoundException forAnimal(Long id) {
        return new ResourceNotFoundException("Animal", id);
    }
    
    public static ResourceNotFoundException forEmpleado(Long id) {
        return new ResourceNotFoundException("Empleado", id);
    }
    
    public static ResourceNotFoundException forAdoptante(Long id) {
        return new ResourceNotFoundException("Adoptante", id);
    }
    
    public static ResourceNotFoundException forAdopcion(Long id) {
        return new ResourceNotFoundException("Adopción", id);
    }
    
    public static ResourceNotFoundException forHistorialMedico(Long id) {
        return new ResourceNotFoundException("Historial Médico", id);
    }
}