# SilentSignals — Panic Alert & Silent SOS API

**SilentSignals** is a secure and real-time panic alert system that allows users to silently trigger SOS signals during emergencies. The alerts are instantly delivered to trusted contacts via WebSocket. If contacts are offline, fallback notifications are sent via SMS and Email. Built with Redis, PostgreSQL, and JWT-secured APIs, the system ensures reliable delivery, rate limiting, and detailed logging for each alert.

---

## 🎯 Learning Objectives

1. Implement real-time communication using WebSockets in a Spring Boot backend  
2. Utilize Redis for time-limited SOS session storage and quick access  
3. Build fallback notification flows using Email or SMS services  
4. Apply JWT-based authentication with role-based access control  
5. Schedule delayed actions with Quartz Scheduler  
6. Store and retrieve geographical coordinates (user location)  
7. Handle mission-critical alert delivery with reliable failover logic  

---

## ✅ Must-Have Features

### Panic Trigger Endpoint
- Users can silently trigger a panic/SOS signal via `POST /api/sos/send`.
- The backend broadcasts the alert to all trusted contacts via WebSocket if they are online.
- The SOS session is stored in Redis with a 3-minute expiration (TTL).
- If no contact responds within 3 minutes, the alert is automatically escalated.

### Real-Time Alert Delivery via WebSocket
- Trusted contacts receive instant alerts as soon as the SOS is triggered.
- Each WebSocket message contains the user's ID and last known location.

### Fallback Notifications via SMS/Email
- If no contact responds within 3 minutes, fallback alerts are sent via Email or SMS (Twilio/Spring Mail).

### Trusted Contact Management
- Users can manage a list of trusted contacts (add, remove, view).
- Contacts include: name, email, phone number.

### SOS History Logs
- Users can view all SOS signals they have sent.
- Each log includes:
  - Date and time of alert
  - Status: resolved or escalated
  - Contacts notified and method (WebSocket, Email)

### JWT-Based Authentication with Roles
- Endpoints are protected with JWT authentication.
- Two roles: `USER` (regular user) and `CONTACT` (trusted contact).
- Each role has restricted access to only what is necessary.

---

## ⚙️ Technical Requirements

### Redis
- Temporary SOS session storage with 3-minute TTL and real-time expiry detection.

### PostgreSQL
- Stores user accounts, contacts, SOS logs, and notification metadata.

### WebSocket (STOMP)
- Delivers instant alerts to trusted contacts over a persistent connection.

### Quartz Scheduler
- Checks unresolved SOS signals after 3 minutes and triggers fallback notifications automatically.

### Twilio API / Spring Mail
- Sends SMS and Email alerts to contacts when fallback is required.

---

## 📦 Technologies Used
- Spring Boot  
- Spring WebSocket + STOMP  
- Spring Data JPA + PostgreSQL  
- Redis  
- JWT Authentication  
- Quartz Scheduler  
- Spring Mail / Twilio API  

---

## 🔧 Setup Instructions

1. Clone the repository:
```bash
git clone https://github.com/<username>/SilentSignals.git
