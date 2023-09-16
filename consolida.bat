@REM Consolidador
@REM Este arquivo é utilizado para executar o consolidador

@echo off
setlocal

cd build

:: Set the paths to the Java executable and the JAR file
set java_exe=java.exe
set jar_file=consolidador.jar

:: Change directory to ./java/java

:: Execute the program using java -jar
%java_exe% -jar "%jar_file%"

:: Pause to allow you to see the output
pause

endlocal