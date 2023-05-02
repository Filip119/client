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

	//@Value("${cezar.url}")
	private String cezarUrl = "https://cezar-istio-test.apps.openshift-demo.dr3.demo.sk";

	//@Value("${reciprocal.url}")
	private String reciprocalUrl = "https://reciprocal-istio-test.apps.openshift-demo.dr3.demo.sk";

	//@Value("${swappairs.url}")
	private String swappairsUrl = "https://swappairs-istio-test.apps.openshift-demo.dr3.demo.sk";

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
