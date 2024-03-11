# TRAIN-API Project

## Requisitos
- Java 17
- Gradle 7.2

## Documentación de Servicios con Swagger
[Swagger UI](http://localhost:8080/swagger-ui/index.html)

## Pasos para Ejecutar el Proyecto
1. Ejecutar el siguiente comando para limpiar y construir el proyecto:
    ```bash
    ./gradlew clean build
    ```

2. Iniciar la aplicación con el siguiente comando:
    ```bash
    ./gradlew bootRun
    ```

## Descripción
Este proyecto implementa una API para gestionar rutas y ubicaciones de trenes. Proporciona servicios para calcular distancias, contar rutas, encontrar la ruta más corta y realizar otras operaciones relacionadas con el transporte ferroviario.

## Endpoints Principales
1. **Calcular Distancia para una Ruta Específica:**
    - Método: `GET`
    - URL: `/api/routes/calculateDistance/{routeDescription}`
    - Descripción: Calcula la distancia para la ruta especificada.

2. **Contar Viajes con un Número Máximo de Paradas:**
    - Método: `GET`
    - URL: `/api/routes/countTripsWithMaxStops/{startLocation}/{endLocation}/{maxStops}`
    - Descripción: Cuenta el número de viajes desde la ubicación de inicio hasta la ubicación de destino con un máximo de paradas especificado.

3. **Contar Viajes con un Número Exacto de Paradas:**
    - Método: `GET`
    - URL: `/api/routes/countTripsWithExactStops/{startLocation}/{endLocation}/{exactStops}`
    - Descripción: Cuenta el número de viajes desde la ubicación de inicio hasta la ubicación de destino con un número exacto de paradas.

4. **Calcular Ruta Más Corta:**
    - Método: `GET`
    - URL: `/api/routes/calculateShortestRoute/{startLocation}/{endLocation}`
    - Descripción: Calcula la ruta más corta desde la ubicación de inicio hasta la ubicación de destino.

## Información Adicional
Este proyecto utiliza Java 17 y Gradle 7.2. La documentación de servicios está disponible en Swagger para facilitar el consumo de la API.

Para ejecutar el proyecto, sigue los pasos mencionados anteriormente. Visita la [Swagger UI](http://localhost:8080/swagger-ui/index.html) para explorar y probar los servicios proporcionados.

**Nota:** Asegúrate de tener instalado Java 17 y Gradle 7.2 antes de ejecutar el proyecto.
