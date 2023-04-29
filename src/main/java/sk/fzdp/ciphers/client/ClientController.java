package sk.fzdp.ciphers.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;

	@RequestMapping(path = "/client/start", method = RequestMethod.GET)
	public String startClient() {

		return clientService.start();
	}
	
	@RequestMapping(path = "/client/stop", method = RequestMethod.GET)
	public String stopClient() {

		return clientService.stop();
	}

}
