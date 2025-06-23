# Visor de resultados MAST
 Generador de visualizaciones de modelos de resultados MAST para usar en Eclipse.

 ## Preparación del IDE

 Para hacer uso de la herramienta primero es necesario instalar el software necesario en eclipse:
 * Eclipse Modeling Framework (EMF): https://download.eclipse.org/modeling/emf/emf/builds/release/latest
 * Epsilon, incluyendo Picto: https://download.eclipse.org/eclipse/updates/4.35
 * Flexmi

## Uso de la herramienta

Siempre que se empieza una sesión en el IDE es necesario registrar los Epackages de los metamodelos:
![imagen](https://github.com/user-attachments/assets/fc6d67bd-2e01-4c81-8df9-0df39f1e85f9)

A continuación hay que añadir el modelo de resultados a visualizar al directorio de modelos y crear un archivo Picto para conectarlo a picto este debe tener el mismo nombre del modelo con extensión y terminado en .picto, ejemplo:
![imagen](https://github.com/user-attachments/assets/929962ea-832b-4873-8d61-e9347b0f963d)

Para finalizar se abre el modelo de resultados en el IDE y se abre una vista de Picto que debería mostrar las vistas generadas de forma automática:
![imagen](https://github.com/user-attachments/assets/27b8efec-5935-4cb5-994d-71532a9a3840)

## Errores comunes y soluciones

### Failed to create the part's controls
Este error se produce al abrir Picto sin haber registrado los ePackages primero, suele ocurrir al abrir Eclipse tras un reinicio. Hay que registrar los ePackages tras lo que es necesario cerrar la ventana de Picto con el fallo y abrir una nueva.
![imagen](https://github.com/user-attachments/assets/105a5b84-dce8-44a0-b408-896128c21b07)


### Título no visible
Al tratar de visualizar la gráfica de resultados tras ser generada por primera vez el título de la gráfica no aparece. Esto se debe a que al generar por primera vez las visualizaciones el nombre del modelo no aparece. Refrescar la ventana de Picto regenerará las visualizaciones solucionando el problema.
