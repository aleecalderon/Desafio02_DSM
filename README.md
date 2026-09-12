# ViajemoSV ✈️

ViajemoSV es una aplicación móvil desarrollada en Android Studio con Kotlin para una agencia de viajes.

La aplicación permite a los usuarios registrarse e iniciar sesión y también administrar un catálogo de destinos turísticos, donde se pueden agregar, consultar, editar y eliminar destinos.

## Integrante

**Alejandra Cristal Calderón Escobar**

## Tecnologías utilizadas

* Kotlin
* Android Studio
* Firebase Authentication
* Firebase Firestore
* Glide
* RecyclerView
* CardView
* ViewBinding
* Git y GitHub

## Funciones de la aplicación

### Inicio de sesión y registro

La aplicación permite crear una cuenta utilizando correo electrónico y contraseña.

El inicio de sesión se realiza mediante Firebase Authentication.

También cuenta con la opción de cerrar sesión desde el catálogo.

### Catálogo de destinos

En el catálogo se pueden visualizar los destinos registrados.

Cada destino contiene:

* Nombre
* País
* Precio
* Descripción
* Imagen

La información de los destinos se almacena en Firebase Firestore.

### CRUD de destinos

La aplicación permite realizar las siguientes operaciones:

* **Crear:** agregar nuevos destinos al catálogo.
* **Leer:** visualizar los destinos registrados.
* **Actualizar:** editar la información de un destino.
* **Eliminar:** borrar un destino después de confirmar la acción.

### Gestión de imágenes

Las imágenes de los destinos se seleccionan desde el dispositivo y se guardan localmente en la aplicación.

Para mostrar las imágenes dentro del catálogo se utilizó Glide.

## Validaciones

Se agregaron validaciones para evitar el ingreso de información incorrecta.

La aplicación verifica que:

* Los campos obligatorios no estén vacíos.
* Se seleccione un país.
* El precio sea mayor que 0.
* La descripción tenga al menos 20 caracteres.
* Se seleccione una imagen al crear un destino.
* Las contraseñas coincidan durante el registro.

## Diseño

Se utilizó una paleta de colores personalizada para la aplicación y un ícono propio para ViajemoSV.

Los textos principales de la aplicación se organizaron mediante `strings.xml`.

La interfaz utiliza tarjetas para mostrar los destinos y botones para realizar las acciones de editar y eliminar.

## Git y GitHub

El proyecto se trabajó utilizando Git y GitHub para llevar un control de los avances realizados durante el desarrollo.

Se realizaron diferentes commits para registrar las principales funcionalidades y cambios de la aplicación.

**Repositorio:**

https://github.com/aleecalderon/Desafio02_DSM.git

## APK

La versión de la aplicación se encuentra disponible en una Release de GitHub.

**Descargar APK:**

https://github.com/aleecalderon/Desafio02_DSM/releases/tag/v1.0.0

Dentro de la sección **Assets** se encuentra el archivo `app-debug.apk`.

## Video de demostración

En el video se muestra el funcionamiento de la aplicación, incluyendo el registro, inicio de sesión, catálogo, creación, edición y eliminación de destinos, selección de imágenes, validaciones y cierre de sesión.

**Video:**

https://drive.google.com/file/d/1OxEBsHdb36lwkh6FJtqjTrm8c-O28tbM/view?usp=sharing
