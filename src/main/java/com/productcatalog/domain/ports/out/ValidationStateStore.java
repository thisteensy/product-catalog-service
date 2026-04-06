package com.productcatalog.domain.ports.out;

import java.util.List;
import java.util.UUID;

public interface ValidationStateStore {
    List<String> getDspTargets(UUID productId);
}