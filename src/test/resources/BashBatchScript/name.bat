@echo off
echo "Hello, please introduce yourself (Batch Script)"
set /p name="Your name: "
set /p lang="Favorite programming language: "
set /p year="Birth Year: "

set /a "age=2022-%year%"

echo Answers:
echo 1. Your name:%name%
echo 2. Your Favorite prog. lang. :%lang%
echo 3. Your age:%age%