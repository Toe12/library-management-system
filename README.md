# Project Setup

## Database and Redis setup
- Please refer to [docker_script](docker_script) and[dockerized_mysql_redis_setup.md](docker_script%2Fdockerized_mysql_redis_setup.md) , for MySQL database and redis setup.

# Library System Management API 
### Postman Collections: [library-system-managment.postman_collection.json](library-system-managment.postman_collection.json)

#### Base Endpoint {{ base_url }}
- http://localhost:8080

## Books API

### Retrieve All Books
- **Method**: GET
- **URL**: `{{base_url}}/api/books/retrieveAll`
- **Description**: Retrieves a list of all books.

### Create Book
- **Method**: POST
- **URL**: `{{base_url}}/api/books/create`
- **Body**:
    ```json
    {
        "title": "Book Title v4",
        "authorFirstName": "firstName", 
        "authorLastName": "lastName",
        "isbn": "978-1-6112-2991-4"
    }
    ```
- **Description**: Creates a new book with the given details.

### Retrieve Book by ID
- **Method**: GET
- **URL**: `{{base_url}}/api/books/retrieve/{bookId}`
- **Description**: Retrieves details of a book by its ID.

### Update Book
- **Method**: PUT
- **URL**: `{{base_url}}/api/books/update/{bookId}`
- **Body**:
    ```json
    {
        "bookId": "b4c178de-1fb9-4269-b71d-093a0634ba89",
        "title": "Book Title v3.0",
        "authorFirstName": "Toe",
        "authorLastName": "Arkar",
        "isbn": "978-1-6112-2991-3"
    }
    ```
- **Description**: Updates the details of a book by its ID.

### Delete Book
- **Method**: DELETE
- **URL**: `{{base_url}}/api/books/delete/{bookId}`
- **Description**: Deletes a book by its ID.

## Patrons API

### Create Patron
- **Method**: POST
- **URL**: `{{base_url}}/api/patrons/create`
- **Body**:
    ```json
    {
        "firstName": "Toe",
        "lastName": "Arkar",
        "phoneNumber": "3145454566",
        "email": "toearkar.15@gmail.com"
    }
    ```
- **Description**: Creates a new patron with the given details.

### Delete Patron by ID
- **Method**: DELETE
- **URL**: `{{base_url}}/api/patrons/delete/{patronId}`
- **Description**: Deletes a patron by its ID.

### Retrieve All Patrons
- **Method**: GET
- **URL**: `{{base_url}}/api/patrons/retrieveAll`
- **Description**: Retrieves a list of all patrons.

### Retrieve Patron by ID
- **Method**: GET
- **URL**: `{{base_url}}/api/patrons/retrieve/{patronId}`
- **Description**: Retrieves details of a patron by their ID.

### Update Patron
- **Method**: PUT
- **URL**: `{{base_url}}/api/patrons/update/{patronId}`
- **Body**:
    ```json
    {
        "patronId": "301b9d17-bede-40cc-abcc-997ff50e3196",
        "firstName": "Toe",
        "lastName": "Arkar",
        "phoneNumber": "3145454578",
        "email": "toearkar.15@gmail.com"
    }
    ```
- **Description**: Updates the details of a patron by their ID.

## Borrowing Records API

### Patron Borrow Books
- **Method**: POST
- **URL**: `{{base_url}}/api/borrow/{bookId}/patron/{patronId}`
- **Description**: Records that a patron has borrowed a book.

### Return Book
- **Method**: PUT
- **URL**: `{{base_url}}/api/return/{bookId}/patron/{patronId}`
- **Description**: Records the return of a borrowed book.

