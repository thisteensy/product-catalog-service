# Development Guide

## Local debugging commands

When running locally, the following commands are useful for inspecting the pipeline without a UI.

**Check consumer lag for the validation consumers:**
```bash
docker exec kafka /opt/kafka/bin/kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --describe \
  --group product-validation

docker exec kafka /opt/kafka/bin/kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --describe \
  --group track-validation
```

**Check Debezium connector status:**
```bash
curl http://localhost:8083/connectors/music-catalog-connector/status
```

**Inspect messages in the products topic:**
```bash
docker exec kafka /opt/kafka/bin/kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic catalog.music_catalog.products \
  --from-beginning \
  --max-messages 10
```

**Inspect the DLQ:**
```bash
docker exec kafka /opt/kafka/bin/kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic product-dlq \
  --from-beginning
```

**Check Kafka Streams application state:**
```bash
curl http://localhost:8080/actuator/health
```

The `kafkaStreams` component will show one of the following states: `RUNNING`, `REBALANCING`, `ERROR`, `PAUSED`, or `NOT_RUNNING`. `REBALANCING` is normal on startup or after a redeployment. `ERROR` means the streams thread has died and needs investigation.

**Check how many records the topology has processed:**
```bash
docker logs product-catalog-service | grep "Processed [^0] total records"
```

This filters the Kafka Streams periodic summary log for any poll cycle where records were actually processed, useful for confirming the topology is consuming events rather than sitting idle.

**Trace validation pipeline activity:**
```bash
docker logs product-catalog-service | grep "transitioned to"
```

All status transitions are logged at INFO level, so the full validation lifecycle is traceable through the application logs.