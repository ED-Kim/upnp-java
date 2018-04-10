package com.tjapp.upnp;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.tjapp.upnp.*;
import java.io.*;
import java.net.*;

public class TestHttpServer {

	private static Logger logger = Logger.getLogger("TestHttpServer");
	
	@Test
	public void test_run() throws Exception {


		HttpServer server = new HttpServer(9900);
		server.bind("/", new HttpServer.Handler() {
				public HttpResponse handle(HttpRequest request) {
					HttpResponse response = new HttpResponse(200);
					response.setData("hello".getBytes());
					return response;
				}
			});
		logger.debug("[start server]");
		
		new Thread(server.getRunnable()).start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// java native
		URL url = new URL("http://localhost:9900/");
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
		String line = reader.readLine();
		assertEquals(line, "hello");

		// wrapper - http client
		HttpClient client = new HttpClient();
		HttpResponse response = client.doGet(new URL("http://localhost:9900/"));
		assertEquals(response.text(), "hello");

		logger.debug("stop http server");
		server.stop();
	}
}
