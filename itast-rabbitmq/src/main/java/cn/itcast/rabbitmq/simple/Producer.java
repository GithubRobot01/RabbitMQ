package cn.itcast.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单模式:发送消息
 */
public class Producer {
    static final String QUEUE_NAME="simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂(设置RabbitMQ的连接参数)
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");//主机默认localhost
        connectionFactory.setPort(5672);//端口默认5672
        connectionFactory.setVirtualHost("/itcast");//虚拟主机默认/
        connectionFactory.setUsername("admin");//用户名默认guest
        connectionFactory.setPassword("123456");//密码默认guest
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建频道
        Channel channel = connection.createChannel();
        //声明队列
        //参数:队列名称,是否持久化队列(消息会持久化保存在服务器上),是否独占本连接,是否在不使用的时候队列自动删除,其他参数
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //发送消息
        String message="你好,兔子";
        //参数:交换机名称,没有则指定空字符串(表示使用默认的交换机),路由key,简单模式中可以使用队列名称
        //消息其他属性,消息内容
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息已发送!"+message);
        //关闭资源
        channel.close();
        connection.close();
    }
}
