@echo off
REM Script para generar ejecutable .exe en Windows
REM Asegúrate de tener Java 21 o superior y jpackage disponible

cd /d %~dp0

echo Creando paquete DEB para Linux...
call gradlew.bat lwjgl3:packageDeb
if %ERRORLEVEL% NEQ 0 (
    echo Error creando DEB
    exit /b 1
)

echo.
echo Creando instalador EXE para Windows...
call gradlew.bat lwjgl3:packageExe
if %ERRORLEVEL% NEQ 0 (
    echo Error creando EXE
    exit /b 1
)

echo.
echo ====== Proceso completado ======
echo.
echo Archivos creados:
dir lwjgl3\build\jpackage\*.deb 2>nul || echo No se encontró archivo DEB
dir lwjgl3\build\jpackage\*.exe 2>nul || echo No se encontró archivo EXE
dir lwjgl3\build\jpackage\Atlix* 2>nul

pause

