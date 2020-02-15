package com.ningmeng.testproducer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Lenovo on 2020/2/14.
 */
public class Producer03_routing {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    //交换机类型
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
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

            //声明交换机String exchange,BuiltineExchangeTYpe type;
            /**
             * 参数明细
             * 1、交换机名称
             * 2、交换机类型，fanout、topic、direct、headers
             */
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);

            //声明队列
            // (String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);

            //交换机和队列绑定String queue, String exchange, String routingKey
            /**
             * 参数明细
             * 1、队列名称
             *  2、交换机名称
             *  3、路由key
             */
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_ROUTING_INFORM,"QUEUE_INFORM_EMAIL");
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_ROUTING_INFORM,"QUEUE_INFORM_SMS");

            //发送邮件信息
            for (int i=0;i<10;i++){
                String message = "email inform to user"+i;
                //向交换机发送消息 String exchange, String routingKey, BasicProperties props,byte[] body
                /**
                 * 参数明细
                 *  1、交换机名称，不指令使用默认交换机名称 Default Exchange
                 *  2、routingKey（路由key），根据key名称将消息转发到具体的队列，这里填写队列名称表示消 息将发到此队列
                 *  3、消息属性
                 * 4、消息内容
                 */
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, "QUEUE_INFORM_EMAIL", null, message.getBytes());
                System.out.println("Send Message is:'" + message + "'");
            }
            //发送短信信息
            for (int i=0;i<10;i++) {
                String message = "sms inform to user" + i;
                //向交换机发送消息 String exchange, String routingKey, BasicProperties props,byte[] body
                /**
                 * 参数明细
                 *  1、交换机名称，不指令使用默认交换机名称 Default Exchange
                 *  2、routingKey（路由key），根据key名称将消息转发到具体的队列，这里填写队列名称表示消 息将发到此队列
                 *  3、消息属性
                 * 4、消息内容
                 */
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, "QUEUE_INFORM_SMS", null, message.getBytes());
                System.out.println("Send Message is:'" + message + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            if (channel!=null){
                try {
                    channel.close();
                }catch (IOException e){
                    e.printStackTrace();
                }catch (TimeoutException e){
                    e.printStackTrace();
                }
            } if (connection!=null){
                try {
                    connection.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
