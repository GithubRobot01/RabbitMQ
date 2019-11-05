package cn.itcast.rabbitmq.routing;

import cn.itcast.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式:发送消息
 */
public class Producer {
    //交换机名称
    static final String DIRECT_EXCHANGE = "direct_exchange";
    //队列名称
    static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";
    static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = ConnectionUtil.getConnection();
        //创建频道
        Channel channel = connection.createChannel();
        //声明交换机
        //参数:交换机名称,交换机类型
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明队列
        //参数:队列名称,是否持久化队列(消息会持久化保存在服务器上),是否独占本连接,是否在不使用的时候队列自动删除,其他参数
        channel.queueDeclare(DIRECT_QUEUE_INSERT,true,false,false,null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE,true,false,false,null);
        //队列绑定到交换机
        //参数:队列名称,交换机名称,路由key
        channel.queueBind(DIRECT_QUEUE_INSERT,DIRECT_EXCHANGE,"insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE,DIRECT_EXCHANGE,"update");
        //发送消息
        String message1="路由模式,路由key为insert";
        //参数:交换机名称,没有则指定空字符串(表示使用默认的交换机),路由key,简单模式中可以使用队列名称
        //消息其他属性,消息内容
        channel.basicPublish(DIRECT_EXCHANGE,"insert",null,message1.getBytes());
        System.out.println("消息已发送!"+message1);

        String message2="路由模式,路由key为update";
        //参数:交换机名称,没有则指定空字符串(表示使用默认的交换机),路由key,简单模式中可以使用队列名称
        //消息其他属性,消息内容
        channel.basicPublish(DIRECT_EXCHANGE,"update",null,message2.getBytes());
        System.out.println("消息已发送!"+message2);

        //关闭资源
        channel.close();
        connection.close();
    }
}
