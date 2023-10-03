# Restaurant Dining Review API

## Overview
This API provides a system for managing and retrieving dining reviews for various restaurants, focusing on user-based scores related to specific food allergies (peanut, egg, dairy). 
The API enables the creation, updating, and querying of restaurant data and their respective allergy-friendly scores, which are calculated based on user reviews.

## Key Concepts
  - `Restaurant` data model holds the name and location of a restaurant
  - `Review` data model holds an individual user review for specified food allergies at a restaurant
  - `User` data model holds the name, location, and allergy information

## Getting Started
To use the API, here are a few endpoints to get you started:
- `/user/signup` - POST: Add unique username for reviewing restaurants
- `/restaurants/{id}` - GET: Retrieve a specific restaurant by ID
- `/reviews/create` POST: Add a review to a restaurant
- `/restaurant/search` GET: Retrieve a list of restaurants with allergy-specific reviews and matching zip code

Note: For POST requests, ensure that your request header includes `Content-Type: application/json` and the JSON body is correctly formatted. See [API Docs](https://app.swaggerhub.com/apis-docs/marjoriekohn/Dining-Review/1.0.0) for more details.

### Example GET Request

```sh
curl -X 'GET' \
  'https://dining-review-83290fd71e9d.herokuapp.com/reviews/admin/pending' \
  -H 'accept: */*'
```

### Example Response

```sh
[
  {
    "id": 1,
    "username": "mrannald1k",
    "restaurantId": 88,
    "peanutScore": 5,
    "eggScore": 4,
    "dairyScore": 1,
    "comments": null,
    "status": "PENDING"
  },
  {
    "id": 3,
    "username": "ebraham1n",
    "restaurantId": 42,
    "peanutScore": 2,
    "eggScore": 5,
    "dairyScore": 2,
    "comments": "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.",
    "status": "PENDING"
  }
]
```

## Technologies Used
- **Spring Boot** - For creating the REST API.
- **Spring Data JPA** - For data access and manipulation using Hibernate.
- **H2 Database** - As an embedded database for development.
- **Lombok** - To reduce boilerplate code in the project.

## Documentation

### JavaDocs
- For information about classes, methods, and endpoints refer to [JavaDocs](https://marjoriekohn.github.io/dining-review/).
 
### API Documentation
- For API documentation and endpoint details refer to [API Docs](https://app.swaggerhub.com/apis-docs/marjoriekohn/Dining-Review/1.0.0).

## License
- This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments
- The [Spring Framework documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html) for providing a comprehensive guide on developing REST APIs.

