package STAGE.stage;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Increase the maximum nesting depth for writing JSON
        StreamWriteConstraints writeConstraints = StreamWriteConstraints.builder()
                .maxNestingDepth(3000) // Set your desired maximum nesting depth
                .build();
        mapper.getFactory().setStreamWriteConstraints(writeConstraints);

        // Increase the maximum nesting depth for reading JSON
        StreamReadConstraints readConstraints = StreamReadConstraints.builder()
                .maxNestingDepth(2000) // Set your desired maximum nesting depth
                .build();
        mapper.getFactory().setStreamReadConstraints(readConstraints);

        return mapper;
    }
}