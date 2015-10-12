package org.revo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class main {
    final static String host = "baboon.rmq.cloudamqp.com";
    final static String queue = "revo";
    final static String username = "gcdwsjsu";
    final static String password = "Z14IKszdYSvdMyb9N3ZkFvISSqIlnyg9";


    public static void main(String[] argv) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setVirtualHost(username);
        factory.setPassword(password);

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(message);
                }
            };
            channel.basicConsume(queue, true, consumer);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (TimeoutException e) {
        }
    }
}
