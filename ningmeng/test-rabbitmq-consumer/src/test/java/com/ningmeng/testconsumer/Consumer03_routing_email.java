package com.ningmeng.testconsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Lenovo on 2020/2/14.
 */
public class Consumer03_routing_email {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "inform_queue_email";
    private static final String EXCHANGE_ROUTING_INFORM="inform_routing_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //浏览管理页面使用端口
        factory.setPort(5672);
        //用户名和密码
        factory.setUsername("guest");
        factory.setPassword("guest");
        //rabbitmq默认虚拟机名称为"/".一个虚拟机相当于一个独立的mq服务
        factory.setVirtualHost("/");

        //创建于rabbitmq服务于TCP连接
        connection = factory.newConnection();
        //创建与EXchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
        channel = connection.createChannel();
        //生命交换机String exchange,BuiltineExchangeTYpe type;
        /**
         * 参数明细
         * 1、交换机名称
         * 2、交换机类型，fanout、topic、direct、headers
         */
        channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
        //声明队列
        // (String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
        channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);

        //交换机和队列绑定String queue, String exchange, String routingKey
        /**
         * 参数明细
         * 1、队列名称
         *  2、交换机名称
         *  3、路由key
         */
        channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, "QUEUE_INFORM_EMAIL");

        //定义消费方法
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            /**
             * 消费者接收消息调用此方法
             *
             * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
             * @param envelope    消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志 (收到消息失败后是否需要重新发送)
             * @param properties
             * @param body
             * @throws IOException
             */
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws java.io.IOException {

                //交换机
                String exchange = envelope.getExchange();
                //消息id
                long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String msg = new String(body, "utf-8");
                System.out.println("receive message.." + msg);
            }

        };
        /**
         *  监听队列String queue, boolean autoAck,Consumer callback
         *   参数明细
         *   1、队列名称
         *   2、是否自动回复，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置 为false则需要手动回复 *
         *   3、消费消息的方法，消费者接收到消息后调用此方法 */
        channel.basicConsume(QUEUE_INFORM_EMAIL,true,defaultConsumer);

    }
}
