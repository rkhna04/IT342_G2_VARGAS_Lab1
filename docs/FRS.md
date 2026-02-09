Functional Requirements Specification (FRS) - Lab 1 (Partial)

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

4) Web UI Screenshots
- Register page screenshot
- Login page screenshot
- Dashboard/Profile page screenshot
- Logout flow screenshot

5) API Endpoints
- Document POST `/api/auth/register`, POST `/api/auth/login`, GET `/api/user/me` including request/response examples and authentication notes

6) Database Configuration
- Connection string: `jdbc:mysql://127.0.0.1:3306/user_db?user=root`
- Password: `123456`

7) Build & Run Instructions (short)
- Backend: `mvn spring-boot:run` (backend folder)
- Frontend: `npm install && npm start` (web folder)

Notes
- Replace this markdown with a proper PDF named `FRS.pdf` in this folder before submission.
- Add exported diagrams and screenshots to the `docs/` folder for inclusion in the PDF.
