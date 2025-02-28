# HMS-Microservice

This project is using a Spring Boot microservices architecture. It is designed to be scalable, secure, and cloud-ready, enabling users to manage hotels, user profiles, and ratings efficiently.
A centralized API Gateway acts as the single entry point for client requests, handling authentication via OKTA Auth and routing traffic to appropriate services. Config Service fetches dynamic configurations from GitHub, ensuring flexible and version-controlled configuration management. The architecture also includes a Service Registry, allowing seamless service discovery and communication with its multi-database design and configurable microservices. 

Architecture:
![Architecture](https://github.com/user-attachments/assets/5d3f76e2-3872-41cb-91c6-5c59314911e4)
![ServiceRegistry](https://github.com/user-attachments/assets/d22dfbda-add2-4dcc-a420-9f4b87661daa)
