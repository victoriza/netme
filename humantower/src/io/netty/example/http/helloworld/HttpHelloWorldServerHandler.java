/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.example.http.helloworld;

import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.MessageList;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

import main.java.HumanTowerService;

import org.springframework.beans.factory.annotation.Required;

import es.moodbox.humantower.service.impl.HumanTowerImpl;

public class HttpHelloWorldServerHandler extends SimpleChannelInboundHandler<Object> {

	private MessageList<Object> out;
	
	private HumanTowerService hts;
	
	@Override
	protected void beginMessageReceived(ChannelHandlerContext ctx) {
		out = MessageList.newInstance();
	}

	@Override
	protected void endMessageReceived(ChannelHandlerContext ctx) {
		ctx.write(out);
		out = null;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
			HttpRequest req = (HttpRequest) msg;

			if (is100ContinueExpected(req)) {
				out.add(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
			}
			boolean keepAlive = isKeepAlive(req);

			///humanEdgeWeight?level=foo&index=bar
			String uri = req.getUri();
			
			if (uri.contains("/humanEdgeWeight?")){
				
				String params = uri.replace("/humanEdgeWeight?", "");
				Map<String, String> aux = getQueryMap(params);
				
				String pLevel = aux.get("level");
				if (pLevel == null || pLevel.isEmpty()) {
					pLevel = "0";
				}
				String pIndex = aux.get("index");
				if (pIndex == null || pIndex.isEmpty()) {
					pIndex = "0";
				}
				int level = Integer.parseInt(pLevel);
				int index = Integer.parseInt(pIndex);
				
				HumanTowerService hts = new HumanTowerImpl();
				float result = 0;
				if (index == 0) {
					result = hts.getHumanEdgeWeight(level, index);
				}
				else {
					result = hts.getHumanEdgeWeight(level, index);
				}
				
				ByteBuf resultMessage = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Weight: "+String.valueOf(result), CharsetUtil.US_ASCII));
				
				FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, resultMessage.duplicate());
				response.headers().set(CONTENT_TYPE, "text/plain");
				response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

				if (!keepAlive) {
					out.add(response);
					ctx.write(out).addListener(ChannelFutureListener.CLOSE);
					out = MessageList.newInstance();
				} else {
					out.add(response);
					response.headers().set(CONNECTION, Values.KEEP_ALIVE);
				}
			}
		}
	}

	public Map<String, String> getQueryMap(String query)  
	{  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params)  
	    {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}