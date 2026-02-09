@echo off
echo ========================================
echo   Spring Boot Backend Server
echo ========================================
echo.

REM Check common Java installation paths
set JAVA_EXE=

if exist "C:\Program Files\Java\jdk-21*\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Java\jdk-21*") do set JAVA_EXE=%%i\bin\java.exe
    goto :found
)
if exist "C:\Program Files\Java\jdk-17*\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Java\jdk-17*") do set JAVA_EXE=%%i\bin\java.exe
    goto :found
)
if exist "C:\Program Files\Java\jdk-11*\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Java\jdk-11*") do set JAVA_EXE=%%i\bin\java.exe
    goto :found
)
if exist "C:\Program Files\Java\jdk1.8*\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Java\jdk1.8*") do set JAVA_EXE=%%i\bin\java.exe
    goto :found
)
if exist "C:\Program Files\Eclipse Adoptium\jdk-*\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-*") do set JAVA_EXE=%%i\bin\java.exe
    goto :found
)
if exist "C:\Program Files\Microsoft\jdk-*\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Microsoft\jdk-*") do set JAVA_EXE=%%i\bin\java.exe
    goto :found
)

REM Try to use java from PATH
where java >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    set JAVA_EXE=java
    goto :found
)

echo ERROR: Java not found!
echo.
echo Please install Java 11 or higher
echo Or manually set JAVA_HOME environment variable
echo.
pause
exit /b 1

:found
echo Using Java: %JAVA_EXE%
echo.

REM Check MySQL connection (optional but helpful)
echo Checking MySQL connection...
echo Make sure MySQL is running on localhost:3306
echo Database: lab1_db
echo.

REM Run the backend
echo Starting backend server on http://localhost:8081
echo Press Ctrl+C to stop the server
echo.

cd backend
"%JAVA_EXE%" -jar target\lab1-backend-1.0.0.jar

pause
