@REM Consolidador
@REM Este arquivo é utilizado para executar o consolidador

@echo off
setlocal

set config_file=%.\consolidador\configuracoes.properties%


:: Delete the existing config if it exists
if exist "%config_file=%" (
    del config_file=%"
)

:: Copy the configuration properties
copy /y ".\consolidador\configuracoes.properties" ".\build\configuracoes.properties" > nul



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