Functional Requirements Specification (FRS) - Lab 1 (Partial)

https://docs.google.com/document/d/11-kREjkIxfU3frxlZvsAXud2vJRT4l6J/edit?usp=sharing&ouid=114271828034962477720&rtpof=true&sd=true

This file is a placeholder for the FRS PDF content required by the lab. Replace or expand this document and export as PDF into this folder as `FRS.pdf` when diagrams and screenshots are ready.

Sections to include in the final FRS PDF:

1) Project Overview
- Brief project summary and scope for Lab 1

2) ERD (Entity Relationship Diagram)
- Include ERD showing `users` table and its columns (id, firstName, lastName, email, password, created_at)
- Export as PNG/SVG and embed in the PDF

3) UML Diagrams
- Class diagrams for the backend controllers, models, and repositories
- Sequence diagram for the registration/login flows

4) Screenshots: Web and Mobile UI
- Web: Register, Login, Dashboard, Profile, Logout confirmation
- Mobile (Android): Register, Login, Dashboard, Profile, Logout confirmation
- Place all raw images in `docs/screenshots/` and embed them in the FRS
- If implementation differs from initial design, add a short "Diagram Revisions" note explaining changes

4) Web UI Screenshots
- Register page screenshot
- Login page screenshot
- Dashboard/Profile page screenshot
- Logout flow screenshot

5) API Endpoints
- Base URL (Web): `http://localhost:8081/api`
- Base URL (Android Emulator): `http://10.0.2.2:8081/api`
- Document POST `/api/auth/register`, POST `/api/auth/login`, POST `/api/auth/logout`, GET `/api/user/me`, PUT `/api/user/profile`
- Response format: `{ status, message, data }` wrapper; `data` contains payloads like `{ user, token }`

6) Database Configuration
- Connection: `jdbc:mysql://127.0.0.1:3306/lab1_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC`
- Username: `root`
- Password: `` (empty by default; set your local password if needed)

7) Build & Run Instructions (short)
- Backend: `mvn spring-boot:run` (from `backend/`)
- Web: `npm install && npm start` (from `web/`)
- Android: Open `mobile/` in Android Studio, run on emulator; ensure backend is running on port 8081

Notes
- Before submission, export this content as a PDF named `FRS.pdf` in this folder.
- Include a DOCX version and individual images of diagrams and screenshots.

References
- Security and password encryption guidance:
	https://docs.google.com/document/d/11-kREjkIxfU3frxlZvsAXud2vJRT4l6J/edit?usp=sharing&ouid=114271828034962477720&rtpof=true&sd=true
