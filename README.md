# Hospital-Management
```mermaid
sequenceDiagram

User->>Controller: Login

Controller->>AuthenticationManager: Authenticate

AuthenticationManager->>JWT Service: Generate Token

JWT Service-->>User: JWT

User->>Controller: Request with JWT

Controller->>Security Filter: Validate

Security Filter-->>Controller: Success

Controller-->>User: Response
```
