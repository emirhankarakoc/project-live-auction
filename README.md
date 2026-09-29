# Live Auction Project

A Spring Boot and React auction demo. Users can sign in, view items, place bids, and see live updates through Socket.IO. Admin pages manage auction data. MySQL stores the records.

I started this as a software engineering course project in 2024.

## Code

- `backend/` has the API, bidding logic, and socket server.
- `frontend/` has the React app.

## Run locally

You need Java, MySQL 8, and Node.js. Set your local database details and `DB_PASSWORD`. Images and email need your own Cloudinary and SMTP settings: `CLOUDINARY_API_KEY`, `CLOUDINARY_API_SECRET`, and `MAIL_PASSWORD`.

```bash
cd backend
./mvnw spring-boot:run
```

In `frontend/`, install packages and use the start script in its `package.json`. Set the API and socket URLs for your local ports.

This is a demo, not a live auction service. The repo still has some generated Eclipse and Java files. Do not use the sample admin account on a real server. Replace old service values from Git history if they were ever used.

[Project video](https://youtu.be/_L2Rda-7xJU)
