# Etapa de build: preparamos herramientas
FROM alpine:3.18 AS build

RUN apk add --no-cache curl wget

# Etapa final: promtail oficial + herramientas copiadas
FROM grafana/promtail:2.9.1

USER root

# Copiamos binarios y dependencias de curl y wget desde Alpine
COPY --from=build /usr/bin/wget /usr/bin/wget
COPY --from=build /usr/bin/curl /usr/bin/curl
COPY --from=build /lib /lib
COPY --from=build /usr/lib /usr/lib

USER nobody

ENTRYPOINT ["/usr/bin/promtail"]
