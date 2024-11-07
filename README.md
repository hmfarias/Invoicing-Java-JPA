[![Status][statuss-shield]][statuss-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- PROJECT LOGO -->
<div align="center">
  <p align="center">
    BACKEND PARA FACTURACION
    <br />
    <a href="https://github.com/hmfarias/NotreDameJoyas">Ver repositorio</a>
    ·
    <a href="https://github.com/hmfarias/NotreDameJoyas/issues">Reportar un error</a>
    ·
    <a href="https://github.com/hmfarias/NotreDameJoyas/issues">Solicitar función</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->

<a name="top"></a>
### Tabla de contenidos

1. [Introducción](#introduccion)
3. [Construido con](#consturido)
4. [Modo de uso](#uso)
7. [Contribuyendo](#contribuyendo)
8. [Licencia](#licencia)
9. [Contacto](#contacto)

<hr>

<!-- ABOUT THE PROJECT -->

<a name="introduccion"></a>
## INTRODUCCION

El proyecto consiste en el desarrollo de una aplicación que  permita administrar las ventas de un comercio.
Para ello se han usado 3 actores, el cliente quién es el que compra los productos que son los elementos que forman las ventas del comercio.



[Volver al menú](#top)
<hr>

<a name="consturido"></a>
### CONSTRUIDO CON


![Static Badge](https://img.shields.io/badge/JAVA-blue?style=for-the-badge) como lenguaje de programación multiplataforma orientado a objetos.

![Static Badge](https://img.shields.io/badge/SPRING_BOOT-green?style=for-the-badge) como herramienta que acelera y simplifica el desarrollo de microservicios y aplicaciones web.

![Static Badge](https://img.shields.io/badge/MySQL-red?style=for-the-badge) como sistema de gestión de bases de datos .


[Volver al menú](#top)
<hr>


<a name="uso"></a>
## MODO DE USO 

### Instalación 

1. ##### Clonar el proyecto del repositorio

Cree una carpeta en un directorio local y desde la `terminal` dentro de la carpeta creada, inicialice git:

```
git init
```

- Clonar todo el proyecto:

```
git clone https://github.com/hmfarias/Invoicing-Java-JPA.git
```

Desde la carpeta clonada podrá seguir los siguientes pasos:

2. #### Levantar el servicio MySql mediante Docker Commposse: (Para ello, deberá tener instalado y corriendo Docker en su PC). 

En la carpeta raíz del sistema podrá encontrar el archivo "docker-compose.yml". 

Desde la terminal y ubicado en dicha carpeta, deberá correr dicho archivo:

```
docker-compose up -d
```

Desde la carpeta clonada con ejecutar InteliJ

3. #### Ejecutar el sistema:
  
Ejecutar "src/main/java/edu.coderhouse.invoicing/InvoicingApplication"

El sistema se iniciará y realizará una carga de datos para las pruebas.

4. #### Ejecutar Postman:

Para ello podrá encontrar a nivel de carpeta raiz, el archivo "INVOICING.postman_collection", el cual contiene las colección Postman para las pruebas.
Importe desde Postman este archivo.

Una vez importado el archivo en cuestión, podrá encontrar cuatro carpetas (CLIENTS, PRODUCTS, INVOICE, INVOICE-DETAIL), cada una con los diferentes métodos que prueban el sistema:

5. #### Camino de Prueba ejemplo:

Para probar la creación de una factura, las colecciones Postman están armadas para seguir el siguiente camino (aunque usted puede optar por realizar pruebas distintas cambiando el body del request de cada método):

  Agruegue un nuevo cliente usando el método POST de la carpeta CLIENTS. Esto agregará el cliente:

```
{
  "name": "Anderson2",
  "lastName": "Ocania",
  "docNumber": "27453890"
}   
```
  Puede probar el metoo PUT de CLIENTS para corregir el apellido del nuevo cliente:


  Puede probar el metoo PUT de CLIENTS para corregir el apellido del nuevo cliente:


```
{
  "name": "Anderson",
  "lastName": "Ocaña",
  "docNumber": "11223344"
}   
```

En este punto usted podrá tambien consultar la lista de todos los clientes (GET) o del cliente recien agregado (GET por Id)

Agregue una nueva factura usando el método POST de la carpeta INVOICES


```
{
    "client": {
        "id": 8
    },
    "invoiceDetails": [
        {
            "product": {
                "id": 1
            },
            "amount": 5
        }
    ]
} 
```

El sistema realizará las siguientes acciones antes de poder dar de alta una factura:

  - Verificación de la existencia del cliente
  - Verificación de la existencia del producto
  - Verificación de la existencia de Stock suficiente del producto seleccionado
  - Actualización del Stock (en caso de que haya superado el control anterior)
  - Asignación de la fecha y hora de creación de la factura (campo createdAt), utilizando para ello la API externa "TIME API".
  - Alta de la nueva factura


6. #### Modificación de una factura:

   Por regla contable de negocio, las facturas cargadas no pueden modificarse, aunque si pueden anularse.


[Volver al menú](#top)
<hr>



<a name="contribuyendo"></a>
## CONTRIBUYENDO

Las contribuciones son lo que hace que la comunidad de código abierto sea un lugar increíble para aprender, inspirar y crear. Cualquier contribución que haga es **muy apreciada**.

Si tiene una sugerencia para mejorar este proyecto, por favor haga un "fork" al repositorio y cree un "pull request". También puede simplemente abrir un "issue" con la etiqueta "mejora".
¡No olvide darle una estrella al proyecto! ¡Gracias de nuevo!

1. Fork al Proyecto
2. Cree una nueva rama para su característica (`git checkout -b feature/newFeature`)
3. Commit para los cambios (`git commit -m 'Add some newFeature'`)
4. Push a la nueva rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

[Volver al menú](#top)
<hr>



<!-- LICENSE -->
<a name="licencia"></a>
## LICENCIA

Distribuido bajo la licencia MIT. Consulte `LICENSE.txt` para obtener más información.

[Volver al menú](#top)
<hr>



<!-- CONTACT -->

<a name="contacto"></a>
## CONTACTO

Marcelo Farias - [+54 9 3512601888] - hmfarias7@gmail.com


[Volver al menú](#top)
<hr>



<!-- ACKNOWLEDGMENTS -->

<!-- MARKDOWN LINKS & IMAGES -->

<!-- [statuss-shield]: https://img.shields.io/badge/STATUS-Developing-green -->

[statuss-shield]: https://img.shields.io/badge/STATUSS-finished-green
[statuss-url]: https://https://github.com/hmfarias/NotreDameJoyas#readme
[forks-shield]: https://img.shields.io/github/forks/hmfarias/NotreDameJoyas
[forks-url]: https://github.com/hmfarias/NotreDameJoyas/network/members
[stars-shield]: https://img.shields.io/github/stars/hmfarias/NotreDameJoyas
[stars-url]: https://github.com/hmfarias/NotreDameJoyas/stargazers
[issues-shield]: https://img.shields.io/github/issues/hmfarias/NotreDameJoyas
[issues-url]: https://github.com/hmfarias/NotreDameJoyas/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg
[license-url]: https://github.com/hmfarias/NotreDameJoyas/blob/master/LICENSE.txt
[product-screenshot]: https://github.com/hmfarias/NotreDameJoyas/blob/main/assets/images/screenShot.webp
[product-screenshot-navbar]: https://github.com/hmfarias/NotreDameJoyas/blob/main/assets/images/navbar.webp
[others-url]: https://github.com/hmfarias/NotreDameJoyas
