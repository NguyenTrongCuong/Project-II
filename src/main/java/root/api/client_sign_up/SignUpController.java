package root.api.client_sign_up;

import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.common.collect.Lists;

import root.amqp_service.AmqpService;
import root.aws_s3_service.AWSS3Service;
import root.entity.Authority;
import root.entity.Client;
import root.entity.ClientCredentials;
import root.entity_service.AuthorityService;
import root.entity_service.ClientCredentialsService;
import root.entity_service.ClientService;

@Controller
public class SignUpController {
	@Autowired
	private AWSS3Service awsS3Service;
	@Autowired
	private ClientCredentialsService clientCredentialsService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private AmqpService amqpService;
	
	@ModelAttribute("client")
	public Client setModel() {
		return new Client();
	}
	
	@GetMapping("/sign-up")
	public String signUp() {
		return "sign-up/client-sign-up/client-sign-up-page";
	}
	
	@PostMapping("/do-sign-up")
	public String doSignUp(@Valid Client client, BindingResult result, Model model) {
		//check whether input properties are valid or not
		if(result.hasErrors()) {
			return "sign-up/client-sign-up/client-sign-up-page";
		}
		
		//upload avatar of the client to aws s3 and get the avatar link back
		String avatarLink = this.awsS3Service.uploadFile(client.getAvatar());
		
		//set the avatar link for the client
		client.setAvatarLink(avatarLink);
		
		//format dob of the client to yyyy-MM-dd pattern
		client.formatDob(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		// create credentials for the client
		ClientCredentials clientCredentials = this.clientCredentialsService.convertClientToClientCredentials(client);
		
		//get the default authorities for the client
		Set<Authority> authorities = this.authorityService.findAuthoritiesById(Lists.newArrayList("ROLE_USER")).get();
		
		for(Authority element : authorities) {
			clientCredentials.getAuthorities().add(element);
			element.getCredentialsOfClient().add(clientCredentials);
		}
		clientCredentials.setClient(client);
		
		//save client, credentials of client to database and update authorities in database
		this.clientService.saveClient(client);
		this.clientCredentialsService.saveClientCredentials(clientCredentials);
		this.authorityService.updateAuthorities(authorities);
		
		//declare a message notification queue for the client
		this.amqpService.declareMessageNotificationQueue(client.getEmail());
		
		model.addAttribute("message", "Please sign in to continue");
		return "sign-in/client-sign-in/client-sign-in-page";
	}

}













































