package es.moodbox.humantower.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.MessageList;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageList<Object> msgs) {
	    MessageList<ByteBuf> messages = msgs.cast();
	    try {
	        for (ByteBuf in : messages) {
	            while (in.isReadable()) { // (1)
	                System.out.println((char) in.readByte());
	                System.out.flush();
	            }
	        }
	    } finally {
	        msgs.releaseAllAndRecycle(); // (2)
	    }
	}
	
//    @Override
//    public void messageReceived(ChannelHandlerContext ctx, MessageList<Object> msgs) { // (2)
//        // Discard the received data silently.
//        msgs.releaseAllAndRecycle();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (3)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
