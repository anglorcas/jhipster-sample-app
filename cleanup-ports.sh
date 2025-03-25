#!/bin/bash

# Cleanup script for JHipster Docker ports
# Usage: ./cleanup-ports.sh

# Stop and remove Docker containers
echo "Stopping Docker containers..."
docker compose down

# List of ports to clean (from docker-compose.yml)
PORTS=(9090 3000 9999 9091)

# Kill processes using these ports
echo "Cleaning up processes on ports: ${PORTS[*]}"
for port in "${PORTS[@]}"; do
    echo "Checking port $port..."
    # Use lsof to find and kill processes
    pid=$(sudo lsof -t -i :$port)
    if [ -n "$pid" ]; then
        echo "Killing process $pid using port $port"
        sudo kill -9 $pid
    else
        echo "Port $port is clean"
    fi
done

echo "Cleanup complete!"