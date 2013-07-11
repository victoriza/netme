package es.moodbox.humantower.server;
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

import es.moodbox.humantower.handlers.util.RequestUtil;
import es.moodbox.humantower.service.HumanTowerService;

public class HttpServerHandler extends SimpleChannelInboundHandler<Object> {

	private final static String URL_PATTERN = "/humanEdgeWeight?";

	private static final Object PARAM_LEVEL = "level";

	private static final Object PARAM_INDEX = "index";

	private HumanTowerService hts;

	private MessageList<Object> out;

	public HttpServerHandler(HumanTowerService myHts){
		hts = myHts; 
	}

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
			Double result = new Double("-1");
			String uri = req.getUri();
			
			//TODO:there needs to be a better way
			if (uri.contains(URL_PATTERN)){

				String paramaters = uri.substring(uri.indexOf("?") + 1);
				HashMap<String, String> params = RequestUtil.getParams(paramaters);

				String sLevel = params.get(PARAM_LEVEL);
				if (sLevel == null || sLevel.isEmpty()) {
					sLevel = "0";
				}
				String sIndex = params.get(PARAM_INDEX);
				if (sIndex == null || sIndex.isEmpty()) {
					sIndex = "0";
				}

				Integer level = RequestUtil.parseString(sLevel);
				Integer index = RequestUtil.parseString(sIndex);
				
				//bad params we return -1
				if (level < 0 || index < 0) {
					result = new Double(-1);
				} else {
					result = hts.getHumanEdgeWeight(level,index);
				}
			}
			
			ByteBuf sResult = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(String.valueOf(result), CharsetUtil.US_ASCII));

			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, sResult);
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

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}