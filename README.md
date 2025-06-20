# 🎨 Integración de Tailwind CSS + DaisyUI en JSF 4.1

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JSF](https://img.shields.io/badge/JSF-4.1-important)
![Tailwind](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)
![DaisyUI](https://img.shields.io/badge/DaisyUI-5A0EF8?style=for-the-badge)

Proyecto demostrativo que integra modernos frameworks CSS (**Tailwind CSS** + **DaisyUI**) con **JavaServer Faces (JSF) 4.1**, rompiendo el mito de que JSF solo funciona con estilos tradicionales.

## ✨ Características Clave

- ✅ **Integración perfecta** de Tailwind CSS v4.1 en JSF 4.1
- 🎁 Componentes listos con DaisyUI (versión 5.0.4)
- 🛠 Configuración optimizada para **producción**
- 📦 Ejemplos prácticos de componentes JSF estilizados:
  - `h:dataTable` con clases utilitarias
  - `p:dialog` con temas de DaisyUI
  - Formularios accesibles
  - Ajax

## 🚀 Cómo Empezar

### Requisitos
- Java 21+
- Servidor compatible con JSF 4.1 (Payara/GlassFish 7+)
- Node.js (solo para compilación CSS)
- 

### Instalación
```bash
# Clonar repositorio
git clone https://github.com/tu-usuario/tu-repo.git

# Instalar dependencias CSS (opcional, si usas build personalizado)
npm install -D tailwindcss daisyui @tailwindcss/forms

# Construir proyecto
mvn clean package
```
## 🎨 Ejemplo de Código

```bash
xhtml
<!-- Ejemplo de botón con Tailwind + DaisyUI -->
<h:commandButton value="Guardar" 
                 styleClass="btn btn-primary bg-blue-600 hover:bg-blue-700" />
```
