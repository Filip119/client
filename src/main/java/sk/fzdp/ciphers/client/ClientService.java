package sk.fzdp.ciphers.client;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ClientService {
	@Autowired
	RestTemplate restTemplate;

	@Value("${cezar.url}")
	private String cezarUrl;

	@Value("${reciprocal.url}")
	private String reciprocalUrl;

	@Value("${swappairs.url}")
	private String swappairsUrl;

	private ClientTask clientTask = null;

	private ClientTask getTaskInstance() {
		if (clientTask == null) {
			clientTask = new ClientTask(restTemplate, cezarUrl, reciprocalUrl, swappairsUrl);
		}
		return clientTask;
	}

	public String start() {
		ClientTask task = getTaskInstance();
		if (task.isRunning()) {
			return "Task already running";
		}

		CompletableFuture.runAsync(task); // calls Runnable.run()
		return "Task started";
	}

	public String stop() {
		ClientTask task = getTaskInstance();
		if (!task.isRunning()) {
			return "Task not yet running";
		}

		task.stop();
		return "Task stopped";
	}
}
