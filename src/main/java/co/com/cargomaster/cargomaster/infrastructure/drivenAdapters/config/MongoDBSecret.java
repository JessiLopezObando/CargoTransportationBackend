package co.com.cargomaster.cargomaster.infrastructure.drivenAdapters.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MongoDBSecret {
    private final String uri;
}
