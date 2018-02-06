package com.jp.base.tool.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * 
 * @author leven
 *
 */
public class JsonUtil {
	private static ObjectMapper om = new ObjectMapper();
	private static ObjectMapper om2 = new ObjectMapper();

	static {
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		om.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

		om2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om2.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		om2.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

		SimpleModule module = new SimpleModule();
		module.addSerializer(String.class, new StringUnicodeSerializer());
		om2.registerModule(module);
	}

	public static <T> T deserializeRequstVo(String param, Class<T> clazz, Class<?> ActualClazz) {
		T t = null;
		try {
			JavaType javaType = getCollectionType(clazz, ActualClazz);
			t = om.readValue(param, javaType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

    public static <T> T deserializeRequstVo(String param, Class<T> clazz) {
        T t = null;
        try {
            t = (T) om.readValue(param, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

	public static <T> T readValue(String param, Class<T> clazz) {
		T t = null;
		try {
			t = om.readValue(param, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static <T> T readValue(String param, TypeReference<T> typeReference) {
		T t = null;
		try {
			t = om.readValue(param, typeReference);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static String writeValueAsString(Object obj) {

		try {
			return om.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String writeValueAsUnicodeString(Object obj) {

		try {
			return om2.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static byte[] writeValueAsBytes(Object obj) {

		try {
			return om.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return om.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	/**
	 * @Title: 将对象序列化为json字符串
	 * @Description: TODO
	 * @author: liw .
	 * @version: V3.0
	 */
	public static String deserializeStrByObj(Object object) {
		try {
			return om.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JsonNode readTree(String param) {
		try {
			return om.readTree(param);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String writerWithDefaultPrettyPrinter(Object json) {

		try {
			return om.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	
	public static void main(String[] args) {


	}
}

class StringUnicodeSerializer extends JsonSerializer<String> {

	private final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
	private final int[] ESCAPE_CODES = CharTypes.get7BitOutputEscapes();

	private void writeUnicodeEscape(JsonGenerator gen, char c) throws IOException {
		gen.writeRaw('\\');
		gen.writeRaw('u');
		gen.writeRaw(HEX_CHARS[(c >> 12) & 0xF]);
		gen.writeRaw(HEX_CHARS[(c >> 8) & 0xF]);
		gen.writeRaw(HEX_CHARS[(c >> 4) & 0xF]);
		gen.writeRaw(HEX_CHARS[c & 0xF]);
	}

	private void writeShortEscape(JsonGenerator gen, char c) throws IOException {
		gen.writeRaw('\\');
		gen.writeRaw(c);
	}

	@Override
	public void serialize(String str, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		int status = ((JsonWriteContext) gen.getOutputContext()).writeValue();
		switch (status) {
		case JsonWriteContext.STATUS_OK_AFTER_COLON:
			gen.writeRaw(':');
			break;
		case JsonWriteContext.STATUS_OK_AFTER_COMMA:
			gen.writeRaw(',');
			break;
		case JsonWriteContext.STATUS_EXPECT_NAME:
			throw new JsonGenerationException("Can not write string value here");
		}
		gen.writeRaw('"');// 写入JSON中字符串的开头引号
		for (char c : str.toCharArray()) {
			if (c >= 0x80) {
				writeUnicodeEscape(gen, c); // 为所有非ASCII字符生成转义的unicode字符
			} else {
				// 为ASCII字符中前128个字符使用转义的unicode字符
				int code = (c < ESCAPE_CODES.length ? ESCAPE_CODES[c] : 0);
				if (code == 0) {
					gen.writeRaw(c); // 此处不用转义
				} else if (code < 0) {
					writeUnicodeEscape(gen, (char) (-code - 1)); // 通用转义字符
				} else {
					writeShortEscape(gen, (char) code); // 短转义字符 (\n \t ...)
				}
			}
		}
		gen.writeRaw('"');// 写入JSON中字符串的结束引号
	}

}
