package cn.itcast.rabbitmq.topic;

import cn.itcast.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布与订阅模式:发送消息
 */
public class Producer {
    //交换机名称
    static final String TOPIC_EXCHANGE = "topic_exchange";
    //队列名称
    static final String TOPIC_QUEUE_1 = "topic_queue_1";
    static final String TOPIC_QUEUE_2 = "topic_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = ConnectionUtil.getConnection();
        //创建频道
        Channel channel = connection.createChannel();
        //声明交换机
        //参数:交换机名称,交换机类型
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

        //发送消息
        String message="路由key为:item.insert";
        channel.basicPublish(TOPIC_EXCHANGE,"item.insert",null,message.getBytes());
        System.out.println("消息已发送!"+message);

        message="路由key为:item.update";
        channel.basicPublish(TOPIC_EXCHANGE,"item.update",null,message.getBytes());
        System.out.println("消息已发送!"+message);

        message="路由key为:item.delete";
        channel.basicPublish(TOPIC_EXCHANGE,"item.delete",null,message.getBytes());
        System.out.println("消息已发送!"+message);

        //关闭资源
        channel.close();
        connection.close();
    }
}
