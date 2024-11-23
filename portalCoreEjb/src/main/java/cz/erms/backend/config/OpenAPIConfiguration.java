package cz.erms.backend.config;

import cz.erms.backend.utility.ApplicationUtility;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    private static final String SERVERS_INVALID_DATA_MSG = """
                    Value of: {portal.core.openapi.servers} is invalid: %s.
                    Value must be in format: [server_url_0]|[server_desc_0]|[server_url_1]|[server_desc_1]|[server_url_...]|[server_desc_...]..."
            """;

    private static List<Server> getServers(String serversData) {
        List<Server> servers = new ArrayList<>();
        List<String> serversTokens = ApplicationUtility.getTokens(serversData);

        if (serversTokens.size() >= 2 && serversTokens.size() % 2 == 0) {
            for (int i = 0; i < serversTokens.size(); i += 2) {
                servers.add(new Server()
                        .url(serversTokens.get(i))
                        .description(serversTokens.get(i + 1)));
            }
        } else {
            throw new IllegalStateException(
                    String.format(SERVERS_INVALID_DATA_MSG, serversTokens));
        }

        return servers;
    }

    private static Contact getContact(String contactsData) {
        List<String> contactsTokens = ApplicationUtility.getTokens(contactsData);

        return new Contact().name(contactsTokens.get(0)).email(contactsTokens.get(1));
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${portal.core.openapi.title}") String title,
                                 @Value("${portal.core.openapi.version}") String version,
                                 @Value("${portal.core.openapi.description}") String description,
                                 @Value("${portal.core.openapi.contact}") String contact,
                                 @Value("${portal.core.openapi.servers}") String servers) {

        return new OpenAPI()
                .info(new Info().title(title)
                        .contact(getContact(contact))
                        .description(description)
                        .version(version)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .servers(getServers(servers));
    }
}
