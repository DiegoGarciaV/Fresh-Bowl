# Fresh-Bowl
The Fresh Bowl es una compañia dedicada a la venta de ensaladas y productos frescos, en este repositorio se aloja la aplicacion web que gestiona el sitio

## Instrucciones de ejecución:

    * En la raiz del proyecto, ejecutar el siguiente comando: 

        - mvn clean install -DskipTests

    * Una vez compilado, definir un archivo de variables de entorno que contenga las siguientes variables:

        MYSQL_ROOT_PASSWORD=pass_for_root
        MYSQL_USER=your_user
        MYSQL_PASSWORD=pass_for_me
        MYSQL_DATABASE=fresh_bowl
        MYSQL_URL=jdbc:mysql://mysql-server:3306/fresh_bowl

    * Ejecutar el siguiente comando para levantar los contenedores e imagenes docker donde se estará ejecutado
      el aplicativo:

        - docker-compose up -d

    * El aplicativo ya se esta ejecutando, si la imagen que contiene el JAR no ha levantado correctamente el aplicativo, 
      solo es necesario reiniciar esa imagen, es posible que cuando se inicializo aun no estuviera lista la imagen de 
      la base de datos.

    * Con el aplicativo y la base  de datos funcionando, accedemos al cliente MySQL para ejecutar los querys almacenados en 
      el archivo 

        - src\main\resources\Querys.sql

    * Ya que esta lista la infraestructura, podemos acceder a la aplicacion en la siguiente URL:

        - http://localhost:8080
