# Consumer Electronics Store Management System

## Setup

1. **Database Setup**:
    - Create a MySQL database.
    - Run the provided SQL scripts to create the necessary tables.
    - Update the `DatabaseConnection` class with your database URL, username, and password.

2. **Java Setup**:
    - Ensure you have Java installed (Java 8 or higher).
    - Download and add the MySQL JDBC driver to your project.

## Usage

1. **Running the Application**:
    - Compile the Java code.
    - Run the `Main` class to start the application.

2. **Application Menu**:
    - The application provides a console-based menu to manage products, customers, and orders.
    - Follow the prompts to add, view, update, or delete records.

## Database Schema

- **Product Table**:
    - `product_id` (Primary Key)
    - `name`
    - `description`
    - `price`
    - `stock_quantity`

- **Customer Table**:
    - `customer_id` (Primary Key)
    - `name`
    - `email`
    - `phone_number`
    - `address`

- **Order Table**:
    - `order_id` (Primary Key)
    - `customer_id` (Foreign Key references Customer Table)
    - `order_date`
    - `total_amount`
    - `status` (pending/confirmed/shipped/delivered/cancelled)

- **OrderItem Table**:
    - `order_item_id` (Primary Key)
    - `order_id` (Foreign Key references Order Table)
    - `product_id` (Foreign Key references Product Table)
    - `quantity`
    - `price`
