package xc.http;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

public class Request {
	public String protocol;
	public String method;
	public String uri;
	public HashMap<String, String> kvs;
	public Request(String uri, String method, String protocol) {
		super();
		this.uri = uri;
		this.method = method;
		this.protocol = protocol;
	}
	public String addHeader(String key, String value) {
		String string = kvs.put(key, value);
		return string;
	}
	public String addHeader(SimpleEntry<String, String> kv) {
		String string = kvs.put(kv.getKey(), kv.getValue());
		return string;
	}
}
