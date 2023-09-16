@REM ATUALIZADOR
@REM Este arquivo é utilizado para atualizar a versao do consolidador
@REM Assim, consigo fazer atualizacoes sem precisar no computador

@echo off
echo Atualizando consolidador..

setlocal enabledelayedexpansion


:: Set the download URL and the output directory
set download_url=https://github.com/victordalosto/consolidador-sgp/releases/download/build/consolidador.jar
set output_directory=./build
set output_file=%output_directory%\consolidador.jar
set config_file=%.\consolidador\configuracoes.properties%

set target=".\java\bin\java.exe"
set link=".\build\java.exe"

:: Remove the existing link if it exists
if exist %link% (
    del %link%
)

:: Create a hard link to the Java executable
mklink /H %link% %target%


:: Create the output directory if it doesn't exist
if not exist "%output_directory%" (
    mkdir "%output_directory%"
)


:: Delete the existing JAR file if it exists
if exist "%output_file%" (
    del "%output_file%"
)


:: Delete the existing config if it exists
if exist config_file=%" (
    del config_file=%"
)


:: Copy the configuration properties
copy /y ".\consolidador\configuracoes.properties" ".\build\configuracoes.properties" > nul


:: Download the JAR file
cd "%output_directory%"
curl -LOJ "%download_url%"


:: Check if the download was successful
echo .
if %errorlevel% equ 0 (
    echo Arquivo atualizado...
) else (
    echo Houve um erro ao tentar baixar a atualizacao...
)

endlocal
@pause