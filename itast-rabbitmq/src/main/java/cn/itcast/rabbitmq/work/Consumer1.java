package cn.itcast.rabbitmq.work;

import cn.itcast.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1. 创建连接工厂；
        //2. 创建连接；（抽取一个获取连接的工具类）
        Connection connection = ConnectionUtil.getConnection();
        //3. 创建频道；
        final Channel channel = connection.createChannel();
        //4. 声明队列；
        channel.queueDeclare(Producer.QUEUE_NAME,true,false,false,null);

        //每次可以预取多少个消息
        channel.basicQos(1);

        //5. 创建消费者（接收消息并处理消息）；
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    //路由key
                System.out.println("路由key为:"+envelope.getRoutingKey());
                //交换机
                System.out.println("交换机:"+envelope.getExchange());
                //消息id
                System.out.println("消息id:"+envelope.getDeliveryTag());
                //接收到的消息
                System.out.println("消费者1接收到的消息:"+new String(body,"utf-8"));

                Thread.sleep(1000);

                //确认消息
                //参数:消息id,false表示只有当前这条被处理
                channel.basicAck(envelope.getDeliveryTag(),false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //6. 监听队列
        //参数:队列名
        //是否要自动确认,设置为true表示消息接收到自动回复MQ消息接收到了,MQ会将消息从队列中删除,如果设置为false则需要手动确认
        //消费者
        channel.basicConsume(Producer.QUEUE_NAME,true,defaultConsumer);
    }
}
