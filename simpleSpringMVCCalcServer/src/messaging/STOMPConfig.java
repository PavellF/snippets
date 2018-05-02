package messaging;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import util.Properties;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
@Configuration
@EnableWebSocketMessageBroker
public class STOMPConfig extends AbstractWebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/main").withSockJS();
		
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay("/topic", "/queue")
		.setSystemHeartbeatReceiveInterval(Properties.get().getInt("SYSTEM_HEARTBEAT_RECEIVE_INTERVAL"))
		.setSystemHeartbeatSendInterval(Properties.get().getInt("SYSTEM_HEARTBEAT_SEND_INTERVAL"))
		.setRelayHost(Properties.get().getString("MESSAGE_BROKER_RELAY_HOST"))
		.setRelayPort(Properties.get().getInt("MESSAGE_BROKER_RELAY_PORT"))
		.setClientLogin(Properties.get().getString("MESSAGE_BROKER_LOGIN"))
		.setClientPasscode(Properties.get().getString("MESSAGE_BROKER_PASSWORD"));
		registry.setApplicationDestinationPrefixes("/app");
	}

	
}
