@echo off
title OPPORTUNA - Student Opportunity Tracker

echo ==========================================
echo          OPPORTUNA
echo   Student Opportunity Tracker
echo ==========================================
echo.

echo [1/2] Compiling OPPORTUNA...
call mvn clean compile

if errorlevel 1 (
    echo.
    echo BUILD FAILED
    echo Please check the errors above.
    pause
    exit /b 1
)

echo.
echo [2/2] Starting OPPORTUNA...
echo.

call mvn exec:java

echo.
echo ==========================================
echo OPPORTUNA has ended.
echo ==========================================
pause
