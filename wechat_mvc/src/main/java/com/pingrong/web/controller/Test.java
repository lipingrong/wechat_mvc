package com.pingrong.web.controller;


import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Test {
	public static void main(String[] args) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(222);
		buffer.writeDouble(32.2);
		byte[] bytes = new byte[buffer.writerIndex()];
		buffer.readBytes(bytes);
		System.out.println(Arrays.toString(bytes));

		ChannelBuffer yuanBuffer = ChannelBuffers.wrappedBuffer(bytes);
		System.out.println(yuanBuffer.readInt());
		System.out.println(yuanBuffer.readDouble());


	}
}
