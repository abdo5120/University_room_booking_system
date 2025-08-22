# University_room_booking_system

## Domain Model & Business Logic

This section outlines the core JPA entities that model the university's resources and the key business rules and exceptions that must be handled in the service layer.

---

###  Core Entities

The domain model is designed to be normalized and flexible, representing users, physical spaces, and the booking process. Each entity's role and its connections to others are described below.

* **User**: Represents an individual person in the system (Student, Faculty, or Admin). It is the central point for authentication and ownership of bookings.
    * **Key Relationships**:
        * → `Role` (`ManyToMany`): A `user_roles` join table links a user to one or more roles, defining their permissions. The `User` entity owns this relationship.
        * → `Department` (`ManyToOne`): Each user belongs to a single department. The `users` table contains a `department_id` foreign key.
        * ← `Booking` (`OneToMany`): A user can make many bookings. This is the inverse side of the relationship; the `bookings` table contains the `user_id` to link back to the user who made the reservation.

* **Role**: A simple lookup table for user roles (e.g., `ROLE_STUDENT`, `ROLE_ADMIN`).
    * **Key Relationships**:
        * ← `User` (`ManyToMany`): Linked to users via the `user_roles` join table.

* **Department**: Represents an academic or administrative department. It groups users and can be assigned ownership of rooms to enforce booking restrictions.
    * **Key Relationships**:
        * ← `User` (`OneToMany`): A department can have many associated users.
        * ← `Room` (`OneToMany`): A department can own multiple rooms, which restricts booking access for those rooms.

* **Building**: Represents a physical building on campus that contains rooms.
    * **Key Relationships**:
        * ← `Room` (`OneToMany`): A building serves as a container for many rooms. The `rooms` table holds a `building_id` to link to its location.

* **Room**: The core bookable resource. It represents a single classroom, lab, or study space.
    * **Key Relationships**:
        * → `Building` (`ManyToOne`): Each room must belong to one building, established by a `building_id` foreign key in the `rooms` table.
        * → `Department` (`ManyToOne`): A room can optionally belong to a department (`department_id` can be `NULL`). If not null, it restricts bookings to users from that department.
        * → `RoomFeature` (`ManyToMany`): A `room_to_features` join table links a room to its various amenities (e.g., Projector, Whiteboard).
        * ← `Booking` (`OneToMany`): A room can be booked many times. The `bookings` table links back to the room via a `room_id`.

* **RoomFeature**: A lookup table for reusable amenities a room can have.
    * **Key Relationships**:
        * ← `Room` (`ManyToMany`): Linked to rooms via the `room_to_features` join table.

* **Booking**: The central transactional entity. It represents a single reservation of a `Room` by a `User` for a specific time slot.
    * **Key Relationships**:
        * → `User` (`ManyToOne`): Owns the relationship to the user who made the booking. The `bookings` table has a `user_id` foreign key.
        * → `Room` (`ManyToOne`): Owns the relationship to the room being booked. The `bookings` table has a `room_id` foreign key.
        * ← `BookingHistory` (`OneToMany`): A booking can have multiple history entries tracking its lifecycle.

* **BookingHistory**: Serves as an immutable audit log for a booking's lifecycle. A new record is created for every status change.
    * **Key Relationships**:
        * → `Booking` (`ManyToOne`): Each history record is tied to a single booking via a `booking_id` foreign key.
        * → `User` (`ManyToOne`): Each record is linked via `changed_by_user_id` to the user who performed the action (e.g., the student who cancelled or the admin who approved).

---

###  Service Layer Logic & Expected Exceptions

Contributors working on the service layer are responsible for enforcing the business rules defined below. When a rule is violated, the service must throw the specified custom exception. A global exception handler (`@ControllerAdvice`) will then catch these and generate the appropriate HTTP error response.

| Scenario                                                                                           | Example Service Method(s)                                | Exception to Throw                  | HTTP Status |
| -------------------------------------------------------------------------------------------------- | -------------------------------------------------------- | ----------------------------------- | :---------: |
| A user requests a `Room`, `Booking`, `Department`, etc., with an ID that does not exist.             | `getRoomById()`, `approveBooking()`                      | `ResourceNotFoundException`         | `404`       |
| A user attempts to book a room for a time slot that overlaps with an existing `APPROVED` or `PENDING` booking. | `createBooking()`, `approveBooking()`                    | `BookingConflictException`          | `409`       |
| A user tries to book a room restricted to a different department.                                  | `createBooking()`                                        | `UnauthorizedActionException`       | `403`       |
| A student or faculty member tries to cancel a booking that does not belong to them.                | `cancelBooking()`                                        | `UnauthorizedActionException`       | `403`       |
| A user tries to cancel a booking after its `startTime` has already passed.                           | `cancelBooking()`                                        | `BookingCancellationException`      | `400`       |
| A booking request is submitted where the `endTime` is before or the same as the `startTime`.         | `createBooking()`                                        | `InvalidTimeSlotException`          | `400`       |
| A new user tries to register with an email or `universityId` that is already in use.               | `authService.registerUser()`                             | `ResourceAlreadyExistsException`    | `409`       |

###  DTOs (Data Transfer Objects)

DTOs define the public-facing contract of our API. They are used for all request and response bodies to decouple the API layer from the internal domain model.

* **Authentication DTOs**
    * `RegisterUserDto`: Used as the request body for creating a new user. Includes all necessary fields and validation for a new registration.
    * `LoginRequestDto`: A simple object containing `email` and `password` for the login endpoint.
    * `AuthResponseDto`: The response object sent after a successful login, containing the JWT `accessToken` and basic user info.

* **Booking DTOs**
    * `BookingRequestDto`: Used to request a new booking. Contains `roomId`, `startTime`, `endTime`, and `purpose`. Includes validation to ensure booking times are in the future.
    * `BookingResponseDto`: A detailed response object for a single booking. It includes nested `RoomSummaryDto` and `UserSummaryDto` to provide context without exposing the full internal entities.

* **Admin DTOs**
    * `RoomDto`: A comprehensive DTO used by admins for all CRUD operations on rooms. It uses a `Set<Long>` of `featureIds` to manage a room's amenities.
    * `BookingApprovalDto`: A lightweight request body for an admin to approve or reject a pending booking, containing only the new `status` and an optional `reason`.