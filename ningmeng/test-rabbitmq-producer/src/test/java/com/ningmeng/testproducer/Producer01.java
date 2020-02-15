package com.ningmeng.testproducer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Lenovo on 2020/2/13.
 */
public class Producer01 {
    //队列名称
    private static final String QUEUE="helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
       //连接
        Connection connection = null;
        //通道中的那个东西
        Channel channel=null;
    try {
        //创建连接工厂
        ConnectionFactory factory=  new ConnectionFactory();
        factory.setHost("localhost");
        //浏览管理页面使用端口
        factory.setPort(5672);
        //用户名和密码
        factory.setUsername("guest");
        factory.setPassword("guest");
        //rabbitmq默认虚拟机名称为"/".一个虚拟机相当于一个独立的mq服务
        factory.setVirtualHost("/");

        //创建于rabbitmq服务于TCP连接
        connection =  factory.newConnection();
        //创建与EXchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
        channel = connection.createChannel();
        /**
         * 声明队列，如果rabbie中没有次队列将自动创建
         * param1:队列名称
         * param2:是否持久化,如果重启rabbie的话，消息不会丢失
         * param3:队列是否独占此连接
         * param4:队列不再使用时是否自动删除此队列
         * param5:队列参数，存活时间（map）
         */
        channel.queueDeclare(QUEUE,true,false,false,null);
        String message="helloworls小明"+System.currentTimeMillis();
        /**
         * 消息发布方法
         * param1：Exchange的名称，如果没有指定，则使用Default Exch
         * param2:routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
         * param3:消息包含的属性
         * param4：消息体
         *
         *  这里没有指定交换机，消息将发送给默认交换机，每个队列也会绑定那个默认的交换机，
         *  但是不能显 示绑定或解除绑定
         *  默认的交换机，routingKey等于队列名称
         */
        channel.basicPublish("",QUEUE,null,message.getBytes());
        System.out.print("发送的消息是:'"+message+"'");
    }catch (Exception e){
        e.printStackTrace();
    }finally {
        if (channel!=null){
            channel.close();
        }
        if (connection!=null){
            connection.close();
        }
    }

    }
}
