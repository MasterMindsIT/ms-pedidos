# 🏗️ Proyecto de Microservicios con Spring Boot, Spring Cloud y Observabilidad Completa

Este repositorio contiene un ecosistema didáctico de microservicios diseñado para **aprender y practicar arquitectura moderna**, incluyendo descubrimiento de servicios, configuración centralizada, resiliencia, mensajería asíncrona y observabilidad.

El proyecto combina:
- **Spring Boot 3.x**
- **Spring Cloud**
- **Kafka**
- **Docker Compose**
- **Prometheus, Grafana y Zipkin**

Está pensado para que **nuevos programadores** comprendan cómo interactúan estos componentes.

---

## 🎯 Objetivo de Aprendizaje

Con este proyecto, aprenderás:

✅ Qué es un microservicio y por qué usarlo.  
✅ Cómo se registran y descubren servicios con Eureka.  
✅ Cómo centralizar configuración con Config Server.  
✅ Cómo comunicar servicios vía Feign(sincrono) y Kafka(asincrono).  
✅ Cómo implementar resiliencia con Circuit Breaker y Retry.  
✅ Cómo monitorizar métricas y trazas de extremo a extremo.

---

## 📚 Requisitos Previos

Para seguir este proyecto se recomienda:

- Conocimientos básicos de **Java y Spring Boot**.
- Familiaridad con **Docker** y línea de comandos.
- Entender qué es REST y qué es una API.

**Herramientas necesarias:**

- JDK 17 o superior
- Maven
- Docker Desktop

**Recursos sugeridos:**

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Docker Getting Started](https://docs.docker.com/get-started/)

---

## 🚀 Cómo Ejecutar el Proyecto

1️⃣ Clona este repositorio:

```bash
git clone https://github.com/MasterMindsIT/ms-pedidos
cd ms-pedidos
O simplemente forkea el repositorio
```

2️⃣ Lanza Docker Compose(lo debes tener instalado):

```bash
docker compose up --build
```

3️⃣ Verifica los servicios en tu navegador:

- **Eureka:** [http://localhost:8761](http://localhost:8761)
- **Config Server:** [http://localhost:8888](http://localhost:8888)
- **Zipkin:** [http://localhost:9411](http://localhost:9411)
- **Prometheus:** [http://localhost:9090](http://localhost:9090)
- **Grafana:** [http://localhost:3000](http://localhost:3000)

4️⃣ Envía peticiones a través del Gateway (puerto 8080).

---

## 🔄 Flujo de una Petición

Para comprender cómo fluyen los datos, aquí tienes un ejemplo de **ciclo completo**:

1. El cliente hace un POST al **API Gateway** (`/api/orders`).
2. Gateway enruta la solicitud a **Order Service**.
3. Order Service consulta stock en **Inventory Service** vía Feign Client.
4. Si hay stock, se publica un evento en **Kafka**.
5. **Notification Service** consume el evento y simula el envío de correo.
6. Toda la trazabilidad se almacena en **Zipkin**.
7. Las métricas de latencia y estado se recopilan en **Prometheus**.
8. Los logs se centralizan en **Loki** y se visualizan en **Grafana**.

---

## 🧩 Tecnologías y Herramientas

### 🟢 Spring Cloud
Spring Cloud es un conjunto de herramientas que facilitan el desarrollo de sistemas distribuidos y microservicios, incluyendo:
- Descubrimiento de servicios (Eureka)
- Configuración centralizada (Config Server)
- Comunicación declarativa (Feign Client)
- Resiliencia (Resilience4j)
- API Gateway (Spring Cloud Gateway)

🔗 [Spring Cloud Docs](https://spring.io/projects/spring-cloud)

---

### 🟢 Eureka Server y Eureka Client
**Eureka Server** actúa como registro de descubrimiento de servicios.  
Cada **Eureka Client** (tu microservicio) se registra automáticamente y puede descubrir otros servicios sin conocer sus direcciones físicas.  
Interfaz web: [http://localhost:8761](http://localhost:8761)  
Puerto: **8761**

🔗 [Eureka Documentation](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)

---

### 🟢 Config Server
Centraliza la configuración de los microservicios.  
Permite gestionar parámetros comunes (puertos, claves, rutas) desde un único lugar.  
Interfaz REST en: [http://localhost:8888](http://localhost:8888)  
Puerto: **8888**
Las configuraciones estan en mi repositorio, puedes cambiarlas al tuyo para hacer nuevas pruebas que necesites con diferentes configuraciones [Config Server](https://github.com/MasterMindsIT/mock-api)

🔗 [Spring Cloud Config](https://spring.io/projects/spring-cloud-config)

---

### 🟢 Spring Cloud Gateway
Es el punto único de entrada a las APIs.  
Enruta tráfico dinámicamente y aplica filtros de autenticación o transformación.  
Toda la configuración está en `application.yml`.  
Puerto: **8080**

🔗 [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)

---

### 🟢 Feign Client
Permite declarar clientes HTTP de manera sencilla, usando interfaces Java.

Spring genera el cliente automáticamente y hace el descubrimiento vía Eureka.

🔗 [OpenFeign Docs](https://spring.io/projects/spring-cloud-openfeign)

---

### 🟢 Resilience4j
Librería ligera de resiliencia.  
Permite implementar:
- Circuit Breaker, en uso
- Retry, en uso
- Rate Limiter, sin usar
- Bulkhead, sin usar

Con esto puedes evitar fallos en cascada y hacer tu sistema más robusto.

🔗 [Resilience4j Docs](https://resilience4j.readme.io/docs/getting-started)

---

### 🟢 Kafka y Zookeeper
**Kafka** es la plataforma de mensajería distribuida que permite que microservicios se comuniquen vía eventos.  
**Zookeeper** coordina la configuración interna de Kafka.

Puertos típicos:
- Kafka: 9092
- Zookeeper: 2181

🔗 [Apache Kafka](https://kafka.apache.org/)

---

### 🟢 Zipkin
Herramienta de **trazabilidad distribuida**.  
Visualiza en qué pasos y servicios ocurre cada petición.  
Interfaz: [http://localhost:9411](http://localhost:9411)  
Puerto: **9411**

🔗 [Zipkin](https://zipkin.io/)

---

### 🟢 Prometheus
Sistema de monitoreo y recolección de métricas.  
Necesita un `prometheus.yml` para definir qué microservicios monitorear.  
Interfaz: [http://localhost:9090](http://localhost:9090)  
Puerto: **9090**

🔗 [Prometheus](https://prometheus.io/)

---

### 🟢 Grafana
Capa visual de dashboards para métricas y logs.  
Es necesario crear datasources manualmente (Prometheus y Loki).  
Interfaz: [http://localhost:3000](http://localhost:3000)  
Usuario por defecto: `admin/admin`

🔗 [Grafana](https://grafana.com/)

---

### 🟢 Loki y Promtail
**Loki** almacena logs centralizados.  
**Promtail** recolecta logs de archivos o contenedores.  
Loki no tiene interfaz propia; se consulta desde Grafana.  
Puerto: **3100**

🔗 [Loki](https://grafana.com/oss/loki/)

---

### 🟢 Micrometer y Spring Boot Actuator
**Micrometer** actúa como puente entre las métricas internas y Prometheus.  
**Spring Boot Actuator** expone endpoints como:
- `/actuator/health`
- `/actuator/prometheus`
- `/actuator/logfile`

Son esenciales para observabilidad.

🔗 [Micrometer](https://micrometer.io/)

---

### 🟢 Docker Compose
Herramienta que permite orquestar todos los contenedores en red compartida.  
Gracias a Compose puedes levantar:
- Eureka
- Config Server
- Kafka
- Prometheus
- Grafana
- Zipkin
en un solo comando.

🔗 [Docker Compose](https://docs.docker.com/compose/)

---

## 📘 Referencias Oficiales

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Apache Kafka](https://kafka.apache.org/)
- [Zipkin](https://zipkin.io/)
- [Prometheus](https://prometheus.io/)
- [Grafana](https://grafana.com/)
- [Resilience4j](https://resilience4j.readme.io/)
- [Micrometer](https://micrometer.io/)
- [Docker Compose](https://docs.docker.com/compose/)

---

## ✨ Licencia

Este proyecto es únicamente educativo y puedes usarlo libremente para tus estudios.

