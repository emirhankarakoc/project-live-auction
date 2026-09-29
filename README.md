# Live Auction Project

A Spring Boot and React auction demo. Users can sign in, view items, place bids, and see live updates through Socket.IO. Admin pages manage auction data. MySQL stores the records.

I started this as a software engineering course project in 2024.

## Code

- `backend/` has the API, bidding logic, and socket server.
- `frontend/` has the React app.

## Run locally

You need Java, MySQL 8, and Node.js. Set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` for your MySQL database. Images need `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY`, and `CLOUDINARY_API_SECRET`. Email needs `MAIL_USERNAME` and `MAIL_PASSWORD`.

```bash
cd backend
./mvnw spring-boot:run
```

In a second terminal, run `npm install` and `npm start` from `frontend/`. Set the API and socket URLs for your local ports.


[Project video](https://youtu.be/_L2Rda-7xJU)
