# Etapa base original de Promtail
FROM grafana/promtail:2.9.1 AS base

# Etapa para construir utilidades necesarias
FROM alpine:3.18 AS build

# Instalamos curl y wget
RUN apk add --no-cache curl wget

# Etapa final: imagen funcional con herramientas para healthcheck
FROM base AS final

# Copiamos wget y curl desde el contenedor intermedio
COPY --from=build /usr/bin/wget /usr/bin/wget
COPY --from=build /usr/bin/curl /usr/bin/curl

ENTRYPOINT ["/usr/bin/promtail"]

