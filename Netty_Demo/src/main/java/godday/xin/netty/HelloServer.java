package godday.xin.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/*
实现一个客户端发送请求
 */
public class HelloServer {
    public static void main(String[] args) throws InterruptedException {
      /*  //主线程组
        EventLoopGroup maineventExecutors = new NioEventLoopGroup();
        //从线程组
        EventLoopGroup salveeventExecutors = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //neety服务器创建,serverBootStrap 启动类
            serverBootstrap.group(maineventExecutors, salveeventExecutors)
                    .channel(NioServerSocketChannel.class).childHandler(new HelloServerInitializer());
            ChannelFuture channelFuture =serverBootstrap.bind(8088).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            maineventExecutors.shutdownGracefully();
            salveeventExecutors.shutdownGracefully();
        }


*/

        System.out.println("hello");
        // 定义一对线程组
        // 主线程组, 用于接受客户端的连接，但是不做任何处理，跟老板一样，不做事
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组, 老板线程组会把任务丢给他，让手下线程组去做任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // netty服务器的创建, ServerBootstrap 是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)			// 设置主从线程组
                    .channel(NioServerSocketChannel.class)	// 设置nio的双向通道
                    .childHandler(new HelloServerInitializer()); // 子处理器，用于处理workerGroup

            // 启动server，并且设置8088为启动的端口号，同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8089).sync();

            // 监听关闭的channel，设置位同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
