@echo off
echo Starting Spring Boot Backend Server...
echo.

REM Check common Java installation paths
set JAVA_EXE=

if exist "C:\Program Files\Java\jdk-21\bin\java.exe" (
    set JAVA_EXE=C:\Program Files\Java\jdk-21\bin\java.exe
) else if exist "C:\Program Files\Java\jdk-17\bin\java.exe" (
    set JAVA_EXE=C:\Program Files\Java\jdk-17\bin\java.exe
) else if exist "C:\Program Files\Java\jdk-11\bin\java.exe" (
    set JAVA_EXE=C:\Program Files\Java\jdk-11\bin\java.exe
) else if exist "C:\Program Files\Java\jdk1.8.0_*\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Java\jdk1.8.0_*") do set JAVA_EXE=%%i\bin\java.exe
) else if exist "C:\Program Files\Eclipse Adoptium\jdk-21.0*\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-21*") do set JAVA_EXE=%%i\bin\java.exe
) else if exist "C:\Program Files\Eclipse Adoptium\jdk-17*\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-17*") do set JAVA_EXE=%%i\bin\java.exe
) else if exist "C:\Program Files\Eclipse Adoptium\jdk-11*\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-11*") do set JAVA_EXE=%%i\bin\java.exe
) else (
    REM Try to use java from PATH
    where java >nul 2>&1
    if %ERRORLEVEL% EQU 0 (
        set JAVA_EXE=java
    ) else (
        echo ERROR: Java not found!
        echo Please install Java 11 or higher and try again.
        echo Or set JAVA_HOME environment variable.
        pause
        exit /b 1
    )
)

echo Using Java: %JAVA_EXE%
echo.

REM Navigate to backend directory and run the JAR
cd backend
"%JAVA_EXE%" -jar target\lab1-backend-1.0.0.jar

pause
