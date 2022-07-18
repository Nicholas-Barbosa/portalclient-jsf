package com.farawaybr.portal.websocket;

import javax.inject.Inject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.farawaybr.portal.service.jsonb.JsonbService;
import com.farawaybr.portal.websocket.message.ChatMessage;

public class JsonEncoder implements Encoder.Text<Object>, Decoder.Text<ChatMessage> {

	@Inject
	private JsonbService jsonbService;

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public String encode(Object object) throws EncodeException {
		// TODO Auto-generated method stub
		return jsonbService.toJson(object);
	}

	@Override
	public ChatMessage decode(String s) throws DecodeException {
		// TODO Auto-generated method stub
		return jsonbService.fromJson(s, ChatMessage.class);
	}

	@Override
	public boolean willDecode(String s) {
		// TODO Auto-generated method stub
		return true;
	}

}
