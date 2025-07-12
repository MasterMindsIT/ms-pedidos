#!/bin/bash

# Verifica que tmux esté instalado
if ! command -v tmux &> /dev/null; then
  echo "tmux no está instalado. Instálalo con: sudo apt install tmux"
  exit 1
fi

SESSION_NAME="logs_ms_pedidos"

# Iniciar nueva sesión tmux
tmux new-session -d -s $SESSION_NAME

# Panel 0: Logs de monitoreo (prometheus, grafana, promtail, loki, zipkin)
tmux rename-window -t $SESSION_NAME:0 'monitoring'
tmux send-keys -t $SESSION_NAME:0 'docker compose logs -f prometheus grafana promtail loki zipkin' C-m

# Panel 1: Logs de microservicios (gateway, order, inventory, notification)
tmux new-window -t $SESSION_NAME:1 -n 'microservicios'
tmux send-keys -t $SESSION_NAME:1 'docker compose logs -f gateway order-service inventory-service notification-service' C-m

# Panel 2: Logs de sistema base (eureka, kafka, zookeeper, config-server)
tmux new-window -t $SESSION_NAME:2 -n 'infraestructura'
tmux send-keys -t $SESSION_NAME:2 'docker compose logs -f eureka-server kafka zookeeper config-server' C-m

# Conectar a la sesión
tmux attach-session -t $SESSION_NAME
