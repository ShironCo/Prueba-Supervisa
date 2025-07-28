# Task Manager App Prueba técnica de Supervisa S.A📝

Aplicación móvil desarrollada en **Android con Kotlin** utilizando **Jetpack Compose** como framework principal para la creación de interfaces modernas y reactivas. Esta app permite gestionar tareas (crear, listar, filtrar y eliminar), con almacenamiento local persistente y arquitectura escalable.
---
##  DESCARGABLE
Recordar activar la opción de permitir descargar de fuentes desconocidas.
URL - https://drive.google.com/file/d/1cXsRkFfdtbeWogTZytrjj-WirWSyJKwx/view?usp=drive_link

##  Tecnologías utilizadas
- **Lenguaje:** Kotlin
- **UI:** Jetpack Compose
- **Persistencia local:** Room (SQLite)
- **Inyección de dependencias:** Dagger Hilt
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Coroutines + Flow:** Para operaciones asíncronas y reactivas

---

## ¿Cómo ejecutar el proyecto?
Clonar el repositorio
Copia y pega la siguiente URL en tu navegador o terminal:
https://github.com/ShironCo/Prueba-Supervisa.git

Abrir en Android Studio

Abre Android Studio

Haz clic en "Get from VCS"

Pega la URL del repositorio

Haz clic en "Clone"

Instalar dependencias
Espera a que Android Studio sincronice el proyecto e instale todas las dependencias automáticamente.

Configurar emulador (opcional)
Si no tienes un emulador configurado:

Ve a Tools > Device Manager

Crea un nuevo emulador y ejecútalo

Ejecutar la aplicación

Haz clic en el botón de Run (▶️)

Selecciona el emulador o dispositivo físico conectado

Usar un dispositivo físico (opcional)
Si no usas emulador:

Conecta tu celular vía USB.

Asegúrate de tener activada la Depuración por USB.

Selecciona tu dispositivo desde el desplegable de dispositivos en Android Studio.

Ejecuta la app como de costumbre

##  Estructura del proyecto 
app/
│
├── manifests/
│   └── AndroidManifest.xml
│
├── kotlin+java/
    └── com/
        └── example/
            └── pruebatecnicasupervisa/
                ├── data/
                │   ├── local/
                │   │   ├── dao/
                │   │   └── database/
                │   ├── model/
                │   └── repository/
                │
                ├── mapper/
                │
                ├── di/
                │
                ├── domain/
                │   ├── model/
                │   ├── repository/
                │   └── useCases/
                │
                ├── presentation/
                │   ├── screen/
                │   └── ui/
                │       ├── components/
                │       ├── navigation/
                │       └── theme/
                │
                ├── viewModel/
                │   ├── addTaskViewModel/
                │   ├── editTaskViewModel/
                │   └── taskViewModel/
                │
                ├── MainActivity.kt
                └── MyApp.kt

Descripción general

- `data/`: Contiene las fuentes de datos, como la base de datos Room, modelos de datos y repositorios locales.
- `mapper/`: Contiene las clases para mapear entre entidades de base de datos y modelos de dominio.
- `di/`: Módulos de Dagger Hilt para la inyección de dependencias.
- `domain/`: Lógica de negocio pura: modelos de dominio, interfaces de repositorio y casos de uso.
- `presentation/`: UI y navegación:
  - `screen/`: Pantallas principales.
  - `ui/components/`: Componentes reutilizables de Compose.
  - `ui/navigation/`: Lógica de navegación con Compose.
  - `ui/theme/`: Definición de colores, tipografía y temas.
- `viewModel/`: ViewModels de los diferentes Screens).
- `MainActivity.kt`: Actividad principal que carga la app.
- `MyApp.kt`: Clase de la aplicación con configuración global.


