package de.itemis.seatreservationservice.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("seatReservation")
    ActiveMQQueue queue() {
        return new ActiveMQQueue("seatReservation");
    }
    /*@Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
        typeIdMappings.put("JMS_TYPE", Object.class);

        converter.setTypeIdMappings(typeIdMappings);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("JMS_TYPE");

        return converter;
    }*/
}
