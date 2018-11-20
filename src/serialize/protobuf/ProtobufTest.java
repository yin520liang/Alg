package serialize.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import serialize.protobuf.Person.PersonProto;

/**
 * protobuf:
 * <p>
 *  体量小、简单、高效。 存储、通信领域；平台无关、语言无关、可扩展
 * <p>
 * <a href="https://developers.google.com/protocol-buffers/">developer guide</a>,
 * <a href="https://halfrost.com/protobuf_encode/">高效的数据压缩编码方式 Protobuf</a>
 * 
 * @author MZCN501A0145
 *
 */
public class ProtobufTest {

	public static void main(String[] args) throws InvalidProtocolBufferException {
		Person.PersonProto person = Person.PersonProto.newBuilder()
				.setId(1)
				.setName("jim")
				.setEmail("sss@gmail.com")
				.build();
		// serielize
		byte[] person2Bytes = person.toByteArray();
		System.out.println("bytes length:" + person2Bytes.length);
		// deserielize
		PersonProto reconstructedPerson = PersonProto.parseFrom(person2Bytes);
		System.out.println("id:" + reconstructedPerson.getId());
		System.out.println("name:" + reconstructedPerson.getName());
		System.out.println("email:" + reconstructedPerson.getEmail());
	}

}
