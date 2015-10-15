package org.revo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class main {
    final static String host = "baboon.rmq.cloudamqp.com";
    final static String queue = "revo";
    final static String username = "gcdwsjsu";
    final static String password = "Z14IKszdYSvdMyb9N3ZkFvISSqIlnyg9";
    private static Channel channel;
    private static Connection connection;

    public static ConnectionFactory getFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setVirtualHost(username);
        factory.setPassword(password);
        return factory;
    }

    static void closeConnection() {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
        } catch (TimeoutException e) {
        }
    }

    static void SendMesage(String queuename, byte[] message) {
        try {
            channel.basicPublish("", queuename, null, message);
        } catch (IOException e) {
        }
    }

    static Channel createConnection() {
        try {
            connection = getFactory().newConnection();
            channel = connection.createChannel();
            channel.basicConsume(queue, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(message);
                }
            });
        } catch (TimeoutException | IOException e) {
            System.out.println(e.getMessage());
        }


//        Observable.interval(1000, TimeUnit.MILLISECONDS).subscribe(i -> {
//            SendMesage("toServer", "revo".getBytes());
//        });
        return channel;
    }

    public static void main(String[] args) {
        createConnection();
    }
}
