package com.ningmeng.testconsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Lenovo on 2020/2/13.
 */
public class Consumer01 {
    private static final String QUEUE = "helloworld";
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory=  new ConnectionFactory();
        factory.setHost("localhost");
        //浏览管理页面使用端口
        factory.setPort(5672);

        //创建于rabbitmq服务于TCP连接
        Connection connection = factory.newConnection();
        //创建与EXchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE, true, false, false, null);
        //定义消费方式
        DefaultConsumer consumer = new DefaultConsumer(channel){
            /**
             * 消费者接收消息调用此方法
             * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
             * @param envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志 (收到消息失败后是否需要重新发送)
             *                  @param properties
             *                  @param body
             *                  @throws IOException
             */
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws java.io.IOException {

                //交换机
                String exchange = envelope.getExchange();
                //路由key
                 String routingKey = envelope.getRoutingKey();
                //消息id
                long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String msg = new String(body,"utf-8");
                System.out.println("receive message.." + msg);
            }
        };
        /**
         *  监听队列String queue, boolean autoAck,Consumer callback
         *   参数明细
         *   1、队列名称
         *   2、是否自动回复，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置 为false则需要手动回复 *
         *   3、消费消息的方法，消费者接收到消息后调用此方法 */
        channel.basicConsume(QUEUE,true,consumer);
    }
}
