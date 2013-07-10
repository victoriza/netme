package es.moodbox.humantower.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("serverHandler")
public class ServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, String msg)
			throws Exception {
		System.out.print(msg);
		ctx.channel().write(msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel is active\n");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("\nChannel is disconnected");
		super.channelInactive(ctx);
	}
}