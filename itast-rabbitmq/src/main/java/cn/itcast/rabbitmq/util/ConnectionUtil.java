package cn.itcast.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        //创建连接工厂(设置RabbitMQ的连接参数)
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");//主机默认localhost
        connectionFactory.setPort(5672);//端口默认5672
        connectionFactory.setVirtualHost("/itcast");//虚拟主机默认/
        connectionFactory.setUsername("admin");//用户名默认guest
        connectionFactory.setPassword("123456");//密码默认guest
        //创建连接
        return connectionFactory.newConnection();
    }
}
