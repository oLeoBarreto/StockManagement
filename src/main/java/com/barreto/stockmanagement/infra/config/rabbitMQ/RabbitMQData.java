package com.barreto.stockmanagement.infra.config.rabbitMQ;

public class RabbitMQData {
    public static final String queueName = "Queue.User";
    public static final String exchangeName = "EXG.User.Fanout";
    public static final String routingKey = "User.newbie";
}
