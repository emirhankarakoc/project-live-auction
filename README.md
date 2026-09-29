# Real-Time Auction Platform

A full-stack auction prototype with a Java/Spring Boot backend, React frontend, MySQL storage, and Socket.IO updates for connected clients.

## What it demonstrates
- Account registration and authentication
- Item and auction management
- Bidding and live highest-bid updates
- Admin-facing management flows
- A backend and frontend that communicate through HTTP and sockets

The project began as a software engineering course project in 2024. The introduction video is available at https://youtu.be/_L2Rda-7xJU.

## Local setup
Install Java, MySQL 8, and Node.js. Set your local datasource URL/user and provide `DB_PASSWORD`. Optional image and email flows need your own Cloudinary and mail configuration plus `CLOUDINARY_API_KEY`, `CLOUDINARY_API_SECRET`, and `MAIL_PASSWORD`. Configure the frontend's API and socket URLs for your local ports.

```bash
cd backend
./mvnw spring-boot:run
```

Run the frontend from `frontend/` using the package scripts defined there.

## Status and security
This is a demonstration, not a production auction service. Do not reuse the sample admin login in a deployment. The repository still contains generated Eclipse/Java build output; that is cleanup work. Previously committed service values remain in Git history and need rotation if used on live accounts.
