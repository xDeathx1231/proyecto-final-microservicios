package com.ef.banner.cursos.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.ef.banner.cursos.config.RabbitMQConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductorRabbitMQ {

    private final RabbitTemplate rabbitTemplate;

    public void enviarMensaje(String mensaje) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.NOMBRE_EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                mensaje
        );
        System.out.println(" Mensaje enviado a RabbitMQ: " + mensaje);
    }
}
