package com.empresaanimal.Refugio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    private final JavaMailSender javaMailSender;
    
    // Constructor injection (recomendado)
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    public void sendAnimalNotification(String to, String subject, String text) {
        try {
            logger.info("Preparando email para: {}", to);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("refugioanimalweb@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            
            logger.info("Enviando email a: {} con asunto: {}", to, subject);
            javaMailSender.send(message);
            logger.info("Email enviado exitosamente a: {}", to);
            
        } catch (Exception e) {
            logger.error("Error enviando email a {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Error enviando email: " + e.getMessage(), e);
        }
    }
    
    public void testConnection() {
        try {
            logger.info("Probando configuración de email...");
            // Crear un mensaje simple para probar
            SimpleMailMessage testMessage = new SimpleMailMessage();
            testMessage.setFrom("refugioanimalweb@gmail.com");
            testMessage.setTo("refugioanimalweb@gmail.com");
            testMessage.setSubject("Test de configuración");
            testMessage.setText("Este es un email de prueba para verificar la configuración.");
            
            javaMailSender.send(testMessage);
            logger.info("Email de prueba enviado correctamente");
            
        } catch (Exception e) {
            logger.error("Error en configuración de email: {}", e.getMessage(), e);
            throw new RuntimeException("Error en configuración de email: " + e.getMessage(), e);
        }
    }
}