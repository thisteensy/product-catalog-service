package com.productcatalog.infrastructure.streams;

import com.productcatalog.domain.ports.out.ProductRepository;
import com.productcatalog.domain.ports.out.ValidationStateStore;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class KTableValidationStateStore implements ValidationStateStore {

    private static final String STATE_STORE = "product-validation-state";

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;
    private final ProductRepository productRepository;

    public KTableValidationStateStore(StreamsBuilderFactoryBean streamsBuilderFactoryBean,
                                      ProductRepository productRepository) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
        this.productRepository = productRepository;
    }

    @Override
    public List<String> getDspTargets(UUID productId) {
        try {
            ReadOnlyKeyValueStore<String, ProductValidationState> store =
                    streamsBuilderFactoryBean.getKafkaStreams()
                            .store(StoreQueryParameters.fromNameAndType(
                                    STATE_STORE, QueryableStoreTypes.keyValueStore()));
            ProductValidationState state = store.get(productId.toString());
            if (state != null && state.getDspTargets() != null && !state.getDspTargets().isEmpty()) {
                return state.getDspTargets();
            }
        } catch (Exception e) {
            // streams not ready or state not available, fall back to DB
        }
        return productRepository.findById(productId)
                .map(p -> p.getDspTargets())
                .orElse(List.of());
    }
}