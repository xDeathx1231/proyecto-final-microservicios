package com.ef.banner.cursos.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

   
    public static final String NOMBRE_COLA = "cola-cursos";
    public static final String NOMBRE_EXCHANGE = "exchange-cursos";
    public static final String ROUTING_KEY = "routingKey-cursos";

    //  Creaa la cola
    @Bean
    public Queue queue() {
        return new Queue(NOMBRE_COLA, true); // durable = true
    }

    // Crea el exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(NOMBRE_EXCHANGE);
    }

    // Crea el binding (cola y exchange usandoo el  routing key)
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
