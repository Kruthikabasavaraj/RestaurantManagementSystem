'# 🍽️ Restaurant Management Service (Java + PostgreSQL)

A terminal-based restaurant management application built using Java and PostgreSQL. The system supports customer table booking, menu-based ordering, kitchen operations, billing, and admin controls — all through a CLI-based interface.

---

## 🚀 Features

- 📅 Table Booking for Customers
- 🍔 Menu Browsing and Item Selection
- 🧾 Order Creation and Tracking
- 🔥 Kitchen Module (Order Status Management)
- 💳 Billing and Payment Handling
- 🛠️ Admin Management (Menu, Orders, Bookings, Payments)
- 🗃️ PostgreSQL-backed data persistence
- 👨‍💻 Menu-driven CLI interface

---

## 🧱 Tech Stack

- **Java (JDK 11+)**
- **PostgreSQL**
- **JDBC**
- **Maven** (Optional)
- Terminal/Console UI

---

## 🗄️ Database Schema Overview

- `customer(id, name, phone)`
- `table_booking(id, customer_id, booking_time, num_people, is_active)`
- `menu(id, name, price)`
- `orders(id, table_booking_id, waiter_name, status, created_at)`
- `order_items(id, order_id, menu_id, quantity)`
- `payments(id, booking_id, amount, payment_method, payment_time)`

---

## 🛠️ Setup Instructions

### ✅ Prerequisites

- Java 11 or higher
- PostgreSQL installed and running
- Git (optional)
- Terminal or IDE like IntelliJ / Eclipse

---

### 📦 1. Clone the Repository

```bash
git clone https://github.com/kruthikab_Zeta/RestaurantManagementService.git
cd RestaurantManagementService
```

### 📂 2. Add PostgreSQL JDBC Driver

✅ **Option 1: If You're Using Maven (Recommended)**

1. Open your project in IntelliJ
2. Open the `pom.xml` file (in the root of the project)
3. Add the following dependency inside `<dependencies>`:

```xml
<dependency>
   <groupId>org.postgresql</groupId>
   <artifactId>postgresql</artifactId>
   <version>42.7.3</version>
</dependency>
```

4. IntelliJ will prompt: "Maven projects need to be imported" → Click **Import Changes**
   (or right-click the `pom.xml` and click **Reload Maven Project**)

✅ **Option 2: If You're NOT Using Maven (Manual Jar Import)**

1. **Download PostgreSQL JDBC Driver**
   - Go to: https://jdbc.postgresql.org/download/
   - Download the latest `.jar` (e.g., `postgresql-42.7.3.jar`)

2. **In IntelliJ:**
   - Go to **File > Project Structure > Libraries**
   - Click ➕ → **JARs or directories**
   - Select the downloaded `.jar` file
   - Click **OK**, then **Apply**
   - The PostgreSQL driver is now part of your project classpath.

### 🗃️ 3. Configure Database

1. **Create a PostgreSQL database:**

```sql
CREATE DATABASE restaurant_db;
```

2. **Create tables:**

```sql
-- Create customer table
CREATE TABLE public.customer (
  id serial PRIMARY KEY,
  name text NOT NULL,
  phone text NOT NULL
);

-- Create menu table
CREATE TABLE public.menu (
  id serial PRIMARY KEY,
  name text NOT NULL,
  price numeric(10, 2) NOT NULL
);

-- Create table_booking table
CREATE TABLE public.table_booking (
  id serial PRIMARY KEY,
  customer_id int REFERENCES public.customer(id) ON DELETE CASCADE,
  booking_time timestamp NOT NULL,
  num_people int NOT NULL,
  is_active boolean DEFAULT true
);

-- Create orders table
CREATE TABLE public.orders (
  id serial PRIMARY KEY,
  table_booking_id int REFERENCES public.table_booking(id) ON DELETE CASCADE,
  waiter_name text NOT NULL,
  status text DEFAULT 'Pending',
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT orders_status_check CHECK (status IN ('Pending', 'Prepared', 'Paid'))
);

-- Create order_items table
CREATE TABLE public.order_items (
  id serial PRIMARY KEY,
  order_id int REFERENCES public.orders(id) ON DELETE CASCADE,
  menu_id int REFERENCES public.menu(id) ON DELETE CASCADE,
  quantity int NOT NULL CHECK (quantity > 0)
);

-- Create payments table
CREATE TABLE public.payments (
  id serial PRIMARY KEY,
  booking_id int REFERENCES public.table_booking(id) ON DELETE CASCADE,
  amount numeric,
  payment_method varchar(20),
  payment_time timestamp DEFAULT now()
);
```

### ⚙️ 4. Update DB Credentials

In `DatabaseConnection.java`:

```java
String url = "jdbc:postgresql://localhost:5432/restaurant_db";
String user = "your_username";
String password = "your_password";
```

### 🏃‍♂️ 5. Run the Application

1. Compile and run the main class
2. Follow the menu-driven interface to:
   - Book tables
   - Browse menu and place orders
   - Manage kitchen operations
   - Process payments
   - Admin functions

---

## 📁 Project Structure

```
RestaurantManagementService/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── [your package structure]
│   └── resources/
├── pom.xml (if using Maven)
└── README.md
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👤 Author

**kruthikab_Zeta**

- GitHub: [@kruthikab_Zeta](https://github.com/kruthikab_Zeta)

---

## 🙏 Acknowledgments

- Thanks to the PostgreSQL community for excellent documentation
- Java JDBC documentation and examples
