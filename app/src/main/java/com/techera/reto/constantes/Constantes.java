package com.techera.reto.constantes;

public interface Constantes {
    String BDD = "techera.db";

    String TBL_USUARIO = "usuario";

    String TABLA_USUARIO = "CREATE TABLE usuario (\n" +
            "    id_usuario INTEGER       PRIMARY KEY AUTOINCREMENT\n" +
            "                             NOT NULL,\n" +
            "    dni        VARCHAR (8)   UNIQUE\n" +
            "                             NOT NULL,\n" +
            "    nombre     VARCHAR (100) NOT NULL,\n" +
            "    apellido   VARCHAR (100) NOT NULL,\n" +
            "    email      VARCHAR (100) NOT NULL,\n" +
            "    password   VARCHAR (100) NOT NULL\n" +
            ");";

    String TBL_CURSO = "curso";

    String TABLA_CURSO = "CREATE TABLE curso (\n" +
            "    id_curso      INTEGER       PRIMARY KEY AUTOINCREMENT\n" +
            "                                NOT NULL,\n" +
            "    id_tecnologia INTEGER       NOT NULL\n" +
            "                                REFERENCES tecnologia (id_tecnologia) ON DELETE CASCADE\n" +
            "                                                                      ON UPDATE CASCADE,\n" +
            "    nombre        VARCHAR (50)  NOT NULL,\n" +
            "    descripcion   VARCHAR (255) NOT NULL\n" +
            ");";

    String TBL_TECNOLOGIA = "tecnologia";

    String TABLA_TECNOLOGIA = "CREATE TABLE tecnologia (\n" +
            "    id_tecnologia INTEGER       PRIMARY KEY AUTOINCREMENT\n" +
            "                                NOT NULL,\n" +
            "    nombre        VARCHAR (50)  NOT NULL,\n" +
            "    descripcion   VARCHAR (255) NOT NULL\n" +
            ");";

    String REG_TECNOLOGIA = "INSERT INTO tecnologia (id_tecnologia, nombre, descripcion) VALUES (1, 'Cursos .NET', 'TechEra presenta sus cursos preparados por expertos programadores, y ahora la tecnología .NET está a tu alcance para que puedas adquirir los conocimientos necesarios para desplegar aplicaciones .NET.'),\n" +
            "(2, 'Cursos Java', 'Somos una empresa que desarrolla soluciones en Java, utilizamos diferentes IDEs, así como servidores de aplicaciones, esta experiencia nos ha permitido poder elaborar diferentes cursos.'),\n" +
            "(3, 'Cursos Móviles', 'Las Empresas requieren contar con aplicaciones en sus dispositivos móviles, sin limitaciones a sistema operativo o equipos, en muchos casos requieren reutilizar sus lógicas de negocio, nosotros hemos elaborado cursos acordes a tus necesidades.'),\n" +
            "(4, 'Cursos UX/UI', 'Hoy en día se requiere que las aplicaciones que desarrollamos cuenten con un alto valor profesional, el diseño de la Interfaz de Usuario debe permitirle al usuario interactuar de una manera fácil con el sistema.'),\n" +
            "(5, 'Análisis y Diseño', 'TechEra presenta sus cursos de Análisis y Diseño, estos cursos están orientados a personas que manejan los procesos de negocios en las empresas y a los profesionales de sistemas.'),\n" +
            "(6, 'PowerBuilder', 'Somos especialistas en PowerBuilder, por años hemos elaborado muchos cursos de PowerBuilder, contamos con ingenieros que constantemente están desarrollando y preparando material acerca de lo nuevo de PowerBuilder.'),\n" +
            "(7, 'Cursos Office', 'Las empresas hoy en dia requieren dominar las herramientas para poder gestionar toda la información de su negocio, Office brinda muchas herramientas para gestionar correos, proyectos, etc.');";

    int VERSION = 1;
}
