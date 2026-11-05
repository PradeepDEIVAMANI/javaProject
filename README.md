# 🏦 Enterprise Core Banking & Financial Ledger System

[![Live Demo](https://img.shields.io/badge/Live%20Demo-Online%20Simulator-brightgreen?style=for-the-badge&logo=google-chrome)](https://pradeepdeivamani.github.io/javaProject/)
[![Architecture](https://img.shields.io/badge/Architecture-Multi--Role%20RBAC-blue?style=for-the-badge&logo=java)](https://github.com/PradeepDEIVAMANI/javaProject)
[![Storage](https://img.shields.io/badge/Ledger-ACID%20Double--Entry-purple?style=for-the-badge)](https://github.com/PradeepDEIVAMANI/javaProject)

An enterprise core banking application engineered with role-based access control (RBAC), atomic transactional ledgers, and multi-tier operational workflows (Customer ATM, Branch Manager, and Front-Office Staff). 

Includes both the original **Java Swing + JDBC desktop suite** and an interactive, client-side **Fintech Web Deployment** hosted on GitHub Pages.

---

## 🚀 Live Interactive Demo

Test the live application directly in your browser:
👉 **[https://pradeepdeivamani.github.io/javaProject/](https://pradeepdeivamani.github.io/javaProject/)**

### 🔑 Instant Demo Credentials:
- **👤 Customer ATM Portal:** Account `1001` | PIN `1234`
- **🏢 Front Office Operations:** Staff ID `2001` | Password `staff123`
- **👔 Branch Manager Executive:** Manager ID `9001` | Password `admin123`

*(You can also use the one-click quick demo buttons directly in the top header).*

---

## 🌟 Architecture & Features

### 1. 💳 Customer ATM & Banking Portal (`Transaction.java`)
- **Balance Inquiries & Real-time Ledger:** Instant query of cleared balance and running ledger totals.
- **Cash Deposit (`Deposit.java`):** Instant credit with date/time audit timestamps and sequential ledger reconciliation.
- **Cash Withdrawal (`Widthdraw.java`):** Overdraft protection, balance verification, and atomic debiting.
- **External Funds Transfer (`Externaltransaction.java`):** Dual-entry wire transfer between registered account holders with recipient verification.
- **Mini-Statement & CSV Export (`Ministatement.java`):** Complete chronological statement with status indicators and 1-click CSV download.
- **Security PIN Change (`Passwordchange.java`):** Secure credential update with validation.

### 2. 👔 Branch Manager Dashboard (`Manager.java`)
- **Aggregate Deposit Sum (`Mdeposit.java`):** Real-time summation of all historical customer deposits across the bank.
- **Branch Liquidity & Solvency:** Real-time net liquidity and active capital reserves monitoring.
- **Customer Activities & Audit Trail (`Mactivities.java`):** System-wide chronological event stream capturing every credit, debit, and transfer.

### 3. 🏢 Front Office Portal (`Forntoff.java`)
- **Clients Directory (`ClientsList.java`):** Searchable customer database with KYC status and account types.
- **KYC Onboarding & Account Creation (`Signup.java` & `SignupTwo.java`):** Automated customer onboarding with dynamic application number generation, personal data verification, and PIN provisioning.

---

## 🛠️ Tech Stack

- **Java Desktop Suite:** Java SE, Swing (`JFrame`, `JTable`, `JTextField`), AWT, JDBC, MySQL.
- **Web Simulation Engine:** HTML5, CSS3 Modern Glassmorphic UI, Vanilla JavaScript (ES6+), Web Audio API (synthesized ATM haptics & transaction chimes), `localStorage` persistence.
- **Hosting:** GitHub Pages & Git CI/CD.

---

## 💻 Running the Java Desktop Suite Locally

### Prerequisites:
- Java JDK 17 or higher
- MySQL Server running locally on port 3306

### Database Configuration (`Conn.java`):
```sql
CREATE DATABASE BankmanagementSystem;
USE BankmanagementSystem;

-- Customer Login Table
CREATE TABLE login (Accountnumber VARCHAR(20), pin VARCHAR(10));

-- Front Office Staff Table
CREATE TABLE login2 (accno VARCHAR(20), pass VARCHAR(20));

-- Branch Manager Table
CREATE TABLE login3 (maccno VARCHAR(20), mpass VARCHAR(20));

-- Transaction Ledger Table
CREATE TABLE bank (pin VARCHAR(10), date VARCHAR(50), type VARCHAR(20), amount VARCHAR(20));
```

### Execution:
Open the project in Eclipse or compile directly:
```bash
cd BankinngSystem/src
javac bankingSystem/*.java
java bankingSystem.Login
```
