#!/bin/bash

echo "Starting infrastructure..."
docker compose up -d

echo "Waiting for Kafka Connect to be healthy..."
until curl -s http://localhost:8083/connectors > /dev/null 2>&1; do
  echo "Kafka Connect not ready yet, waiting..."
  sleep 5
done

echo "Registering Debezium connector..."
curl -X POST http://localhost:8083/connectors \
  -H "Content-Type: application/json" \
  -d @debezium-connector.json

echo "Done. Infrastructure is ready."