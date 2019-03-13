package simplejava.concurrent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {
	public static void main(String[] args) throws IOException {
		InetSocketAddress addr = new InetSocketAddress(8888);
		HttpServer server = HttpServer.create(addr, 0);
		server.setExecutor(Executors.newCachedThreadPool());
		
		server.createContext("/v1/resource/grant", new ResourceGrantHandler());
		server.createContext("/v1/resource/notify", new ResourceNotifyHandler());
		
		server.start();
		System.out.println("Server is listening on port 8888");
	}
}

class ResourceGrantHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String requestMethod = exchange.getRequestMethod();
		if (requestMethod.equalsIgnoreCase("POST")) {
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Content-Type", "text/plain");			
			byte[] body = "TestTestTest".getBytes();	
			exchange.sendResponseHeaders(200, body.length);
			OutputStream responseBody = exchange.getResponseBody();
			responseBody.write(body);
			responseBody.close();

		} else {
			exchange.sendResponseHeaders(404, -1);
		}

	}

}


class ResourceNotifyHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String requestMethod = exchange.getRequestMethod();
		if (requestMethod.equalsIgnoreCase("POST")) {
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Content-Type", "text/plain");
			responseHeaders.set("Custom-header", "TestTestTest");
			exchange.sendResponseHeaders(200, -1);
		} else {
			exchange.sendResponseHeaders(404, -1);
		}

	}

}