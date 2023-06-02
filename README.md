# Library Management System - Backend

This is the backend API service for the Library Management System application. It's built using Spring Boot and provides endpoints for managing user authentication, categories, books, book copies, user book histories, and reservations.

## Database Design

The system is designed around several key entities: User, Book, Category, Book Copy, Reservation, and UserBookHistory. The relationships among these entities are realized via a relational database. 

### User

Users are the consumers of the system. Users can authenticate, register, borrow books, return books, and view their borrowing history. 

### Book

Books are the key items in the library. Each book has associated data such as title, author, and category. The system allows for the creation, retrieval, update, and deletion of books.

### Category

Categories allow books to be organized in a logical manner. Categories can also be created, retrieved, updated, and deleted. Furthermore, users can retrieve books belonging to a specific category.

### Book Copy

Since there can be multiple copies of a single book in the library, the system needs to track each individual copy. This also allows for managing the borrowing and returning of specific book copies.

### Reservation

Reservations are a way for users to express their intention to borrow a specific book. The system supports the creation and deletion of reservations.

### UserBookHistory

UserBookHistory provides a historical record of all the books a user has borrowed. This can be used for analysis and reporting.

## RESTful API Design

The system is designed around REST principles:

- Resource-based URLs: The API endpoints are based on resources (User, Book, Category, etc.).
- Use of HTTP verbs: The system uses HTTP verbs (GET, POST, PUT, DELETE) to perform operations on the resources.
- Stateless: Every request from the client to the server must contain all the information needed to understand and process the request. The server does not store any client context between requests.
- Use of HTTP status codes: The API uses HTTP status codes to indicate the success or failure of a request.

## Authentication and Authorization

Security is crucial for a system like this. The system uses JWT (JSON Web Token) for authentication and authorization. 

JWT is a compact, URL-safe means of representing claims to be transferred between two parties. The claims in a JWT are encoded as a JSON object that is used as the payload of a JSON Web Signature (JWS) structure or as the plaintext of a JSON Web Encryption (JWE) structure, enabling the claims to be digitally signed or integrity protected with a Message Authentication Code (MAC) and/or encrypted.

The use of JWT ensures that the system is stateless and scalable because the server does not need to keep a record of tokens.

## Future Improvements

- Pagination and Sorting: Add support for pagination and sorting in the GET methods.
- Rate Limiting: To protect the API from abuse, add rate limiting.
- Real-time notifications: Implement real-time notifications for events like book returns and new reservations.
- Improved error handling: Implement more comprehensive error handling to better manage and respond to exceptions.

## API Endpoints

Below are the provided API endpoints. 

### Authentication
- POST /api/v1/auth/authenticate
- POST /api/v1/auth/register

### Categories
- GET /api/v1/categories
- POST /api/v1/categories
- GET /api/v1/categories/{id}
- PUT /api/v1/categories/{id}
- DELETE /api/v1/categories/{id}
- GET /api/v1/categories/{id}/books

### Book Copies
- GET /api/v1/copies
- POST /api/v1/copies
- GET /api/v1/copies/{id}
- PUT /api/v1/copies/{id}
- DELETE /api/v1/copies/{id}
- PUT /api/v1/copies/{id}/borrow
- PUT /api/v1/copies/{id}/return

### User Book History
- GET /api/v1/userBookHistory
- POST /api/v1/userBookHistory
- GET /api/v1/userBookHistory/history/{userId}
- PUT /api/v1/userBookHistory/{userId}/{bookId}
- DELETE /api/v1/userBookHistory/{userId}/{bookId}

### Reservations
- GET /api/v1/reservations
- POST /api/v1/reservations
- GET /api/v1/reservations/{id}
- DELETE /api/v1/reservations/{id}

### Books
- GET /api/v1/books
- POST /api/v1/books
- GET /api/v1/books/{id}
- PUT /api/v1/books/{id}
- DELETE /api/v1/books/{id}
- POST /api/v1/books/{id}/add-copies

## Installation & Setup

Make sure you have the following installed:
- Java (JDK 17)
- Maven

Clone this repository:

```bash
git clone https://github.com/phuong-nh/lib-manage-sys-backend.git
cd lib-manage-sys-backend
```

Build the application:

```bash
mvn clean install
```

Run the application:

```bash
# with the following environment variables: SECRET_KEY, DATABASE_USERNAME, DATABASE_PASSWORD, DATABASE_URL
mvn spring-boot:run
```

The application should now be running on http://localhost:8080.

## Contributing

This is a proof-of-concept project, but any contributions or suggestions are welcome.
