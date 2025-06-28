package com.ef.banner.cursos.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumidorRabbitMQ {

    @RabbitListener(queues = "cola-cursos")
    public void recibirMensaje(String mensaje) {
        System.out.println(" Mensaje recibido en consumidor RabbitMQ: " + mensaje);
    }
}
