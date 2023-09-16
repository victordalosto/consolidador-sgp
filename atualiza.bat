@REM ATUALIZADOR
@REM Este arquivo é utilizado para atualizar a versao do consolidador
@REM Assim, consigo fazer atualizacoes sem precisar no computador

@echo off
echo  Atualizando consolidador..

setlocal enabledelayedexpansion


:: Set the download URL and the output directory
set download_url=https://github.com/victordalosto/consolidador-sgp/releases/download/build/consolidador.jar
set output_directory=./build
set output_file=%output_directory%\consolidador.jar

set target=".\java\bin\java.exe"
set link=".\build\java.exe"



echo  Criando diretorio: %output_directory%
if not exist "%output_directory%" (
    mkdir "%output_directory%"
)



echo  Criando executavel JAVA
if exist %link% (
    del %link%
)
mklink /H %link% %target%



echo  Deletando executavel antigo
if exist "%output_file%" (
    del "%output_file%"
)


:: Download the JAR file
echo  Baixando executavel novo
cd "%output_directory%"
curl -LOJ "%download_url%"


:: Check if the download was successful
if %errorlevel% equ 0 (
    echo  Arquivos atualizados...
) else (
    echo  Houve um erro ao tentar baixar a atualizacao...
)

endlocal
@pause