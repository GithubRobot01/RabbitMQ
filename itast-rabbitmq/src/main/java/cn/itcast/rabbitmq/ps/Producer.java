package cn.itcast.rabbitmq.ps;

import cn.itcast.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布与订阅模式:发送消息
 */
public class Producer {
    //交换机名称
    static final String FANOUT_EXCHANGE = "fanout_exchange";
    //队列名称
    static final String FANOUT_QUEUE_1 = "fanout_queue_1";
    static final String FANOUT_QUEUE_2 = "fanout_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = ConnectionUtil.getConnection();
        //创建频道
        Channel channel = connection.createChannel();
        //声明交换机
        //参数:交换机名称,交换机类型
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        //声明队列
        //参数:队列名称,是否持久化队列(消息会持久化保存在服务器上),是否独占本连接,是否在不使用的时候队列自动删除,其他参数
        channel.queueDeclare(FANOUT_QUEUE_1,true,false,false,null);
        channel.queueDeclare(FANOUT_QUEUE_2,true,false,false,null);
        //队列绑定到交换机
        //参数:队列名称,交换机名称,路由key
        channel.queueBind(FANOUT_QUEUE_1,FANOUT_EXCHANGE,"");
        channel.queueBind(FANOUT_QUEUE_2,FANOUT_EXCHANGE,"");
        //发送消息
        for (int i = 1; i <= 10; i++) {
            String message="你好,兔子"+i;
            //参数:交换机名称,没有则指定空字符串(表示使用默认的交换机),路由key,简单模式中可以使用队列名称
            //消息其他属性,消息内容
            channel.basicPublish(FANOUT_EXCHANGE,"",null,message.getBytes());
            System.out.println("消息已发送!"+message);
        }
        //关闭资源
        channel.close();
        connection.close();
    }
}
