#!/bin/bash
# Script para generar ejecutables para Linux
# Requisitos: Java 21+, jpackage, fakeroot (para .deb)
cd "$(dirname "$0")"
echo "====== Creador de Ejecutables - Atlix ======"
echo ""
# Crear .deb para Linux
echo "1. Creando paquete DEB para Linux..."
./gradlew lwjgl3:packageDeb
if [ $? -ne 0 ]; then
    echo "Error creando DEB"
    exit 1
fi
echo ""
echo "====== Proceso completado ======"
echo ""
echo "Archivo creado:"
ls -lh lwjgl3/build/jpackage/*.deb 2>/dev/null || echo "No se encontró archivo DEB"
echo ""
echo "Para instalar en Linux:"
echo "  sudo dpkg -i lwjgl3/build/jpackage/atlix_*.deb"
echo ""
echo "Luego ejecuta: atlix"
