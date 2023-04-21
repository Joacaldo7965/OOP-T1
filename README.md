# OOP-T1
## Tarea
Esta tarea busca practicar la orientación a objeto en un sistema de alarma domiciliaria.

## Comandos
### Stage 1
- di (o|c) : Abre (o) ó cierra (c) la puerta (d) número i.
- wi (o|c) : Abre (o) ó cierra (c) la ventana (w) número i.
- x        : Termina la ejecución del programa.

### Stage 2
Los comandos anteriores más:
- k (a|d) : Arma (a) o desarma (d) la central.
- t       : Muestra el estado de la central dividido por zonas. (Comando para DEBUG)

### Stage 3
Los comandos anteriores más:
- pi (↑ | → | ↓ | ←) : Mueve la persona (p) número i 0.5 [m] en la dirección de la flecha.
- c x y : Crea una persona en la posición (x, y).

### Stage 4
Los comandos anteriores más la siguiente modificación:
- k (a | p |d) : Arma (a) o desarma (d) la central. También está la opción de armar la central en modo nocturno (Sólo puertas y ventanas, es decir, zona 0 y zona 1).


## Aclaraciones
- Para la clase Person, el comando del movimiento utilizado está dado por los parámetros: '↑', '→', '↓' y '←' con los códigos ascii 8592, 8593, 8594 y 8595, respectivamente.

## Compilación y ejecución
Primero se accede a la sección correspondiente: ('stage1', 'stage2', 'stage3' o 'stage4')
```sh
# Ejemplo para 'stage1'
cd stage1/ 
```
Para sólo compilar la sección:
```sh
make
```
Para compilar y ejecutar la sección:
```sh
make run
```
Para limpiar los archivos .class:
```sh
make clean
```

## Info sistema
Se probó en Ubuntu 22.04.1 LTS. Con WSL 2 en Windows 11.

Versión de java utilizada es: 
```sh
openjdk version "19.0.2" 2023-01-17
OpenJDK Runtime Environment (build 19.0.2+7-Ubuntu-0ubuntu322.04)
OpenJDK 64-Bit Server VM (build 19.0.2+7-Ubuntu-0ubuntu322.04, mixed mode, sharing)
```
Versión de javac utilizada:
```sh
javac 19.0.2
```