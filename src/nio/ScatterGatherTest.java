package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/**
 * Java NIO API: scatter and gather
 * 
 * @author  
 *
 */
public class ScatterGatherTest {

	private static final int HEADER_LEN = 128; // bytes
	private static final int BODY_LEN = 1024; // 1k
	
	private static final String charset = "utf8";

	private ByteBuffer headerBuff = ByteBuffer.allocate(HEADER_LEN);
	private ByteBuffer bodyBuff = ByteBuffer.allocate(BODY_LEN);
	private ByteBuffer[] buffArray = { headerBuff, bodyBuff};


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * scatter
	 * @throws IOException 
	 */
	public Message receiveFixedHeaderMessage(Channel channel) throws IOException {
		clear();
		if(channel instanceof ScatteringByteChannel) {
			((ScatteringByteChannel) channel).read(buffArray);
			return new Message(
					new String(headerBuff.array(), charset), 
					new String(bodyBuff.array(), charset));
		}
		return null;
	}

	/**
	 * gather
	 * @throws IOException 
	 */
	public Channel sendFixedHeaderMessage(Message msg, Channel channel) throws IOException {
		clear();
		headerBuff.put(msg.header.getBytes(charset));
		bodyBuff.put(msg.body.getBytes(charset));
		if(channel instanceof GatheringByteChannel) {
			((GatheringByteChannel) channel).write(buffArray);
		}
		return channel;
	}
	
	private void clear() {
		headerBuff.clear();
		bodyBuff.clear();
	}

	class Message {
		String header;

		String body;

		Message(String header, String body) {
			this.header = header;
			this.body = body;
		}
	}

}
