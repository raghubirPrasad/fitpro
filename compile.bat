@echo off
echo Compiling Wellness App...
if not exist bin mkdir bin
javac -d bin -encoding UTF-8 src\wellnessapp\exceptions\*.java src\wellnessapp\models\*.java src\wellnessapp\utils\*.java src\wellnessapp\gui\*.java src\wellnessapp\main\*.java
if %errorlevel% == 0 (
    echo Compilation successful!
    echo Run the app with: java -cp bin wellnessapp.main.Main
) else (
    echo Compilation failed!
)
pause

