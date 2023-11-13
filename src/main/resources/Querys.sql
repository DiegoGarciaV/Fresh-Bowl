DROP TABLE IF EXISTS units;
CREATE TABLE    units(

    unit_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    unit_name VARCHAR(100) NOT NULL,
    unit_type BOOLEAN NOT NULL
);

INSERT INTO     units(unit_name,units_type)
VALUES          ("Kg",0),
                ("g",0),
                ("l",0),
                ("oz",0),
                ("pieza",1),
                ("paquete",1);


DROP TABLE IF EXISTS product_types;
CREATE TABLE    product_types(

    product_type_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    type_name VARCHAR(100) NOT NULL
);


INSERT INTO     product_types(type_name)
VALUES          ("Perecedero"),
                ("Contenedor"),
                ("Limpieza"),
                ("Otros");

DROP TABLE IF EXISTS inventory_item;
CREATE TABLE inventory_item (
    item_id INT(11) NOT NULL AUTO_INCREMENT,
    name  VARCHAR(200) NOT NULL,
    item_type INT(11) NOT NULL,
    quantity INT(11) NOT NULL DEFAULT '0',
    price DECIMAL(8,2) NOT NULL DEFAULT '0.00',
    img VARCHAR(200) NOT NULL DEFAULT 'imgs/ingredients/',
    unit INT(11) NOT NULL,
    acquisition_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    EXPIRY_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    DESCRIPTION VARCHAR(200) NOT NULL DEFAULT '',
    comments VARCHAR(200) NOT NULL DEFAULT '',
    PROVIDER VARCHAR(200) NOT NULL DEFAULT '',
    PRIMARY KEY (item_id),
    KEY unit_fk (unit),
    KEY type_fk (item_type),
    CONSTRAINT type_fk FOREIGN KEY (item_type) REFERENCES product_types (product_type_id),
    CONSTRAINT unit_fk FOREIGN KEY (unit) REFERENCES units (unit_id)
);

CREATE VIEW inventory_desc  AS
    
    SELECT          i.*,
                    t.type_name     AS product_type,
                    u.unit_name     AS unit_name,
                    u.units_type    AS units_type

    FROM            inventory_item  AS i
                    INNER JOIN
                    product_types   AS t
    ON              i.item_type = t.product_type_id
                    INNER JOIN
                    units           AS u
    ON              i.unit = u.unit_id;


INSERT INTO         inventory_item
VALUES              (
                        1,
                        "Higo",
                        1,
                        2.0,
                        60.0,
                        'imgs/ingredients/higo.png',
                        1,
                        "2023-11-01",
                        "2023-11-10",
                        'Higo fresco',
                        'Higo fresco',
                        ""
);


INSERT INTO             inventory_item
                        (name , item_type , quantity , price , img , unit , description ,comments) 
VALUES                  ('Lechuga ',1,20,20.5,' imgs/ingredients/letuce.png ',1,' ',' '),
                        ('Jitomate ',1,60,14.9,' imgs/ingredients/tomato.png ',1,' ',' '),
                        ('Tomate ',1,30,9.9,' imgs/ingredients/tomato.png ',1,' ',' '),
                        ('Mango ',1,20,35.5,' imgs/ingredients/mango.png ',1,' ',' '),
                        ('Queso de cabra ',1,2,450,' imgs/ingredients/goat-cheese.png ',1,' ',' '),
                        ('Pollo ',1,5,198,' imgs/ingredients/chicken-strips.png ',1,' ',' '),
                        ('Jitomate ',1,60,14.5,' imgs/ingredients/tomato.png ',1,' Jitomate de bola ',' Jitomate de bola perfeto para rebanar '),
                        ('Langosta ',1,5,850,' imgs/ingredients/lobster-tail.png ',5,' Cola de Langosta ',' Cola de langosta fresca, calidad para ensalada Langosta Delux '),
                        ('Higo ',1,3,86,' imgs/ingredients/fig.png ',1,' Higo de temporada ',' Excelente Higo '),
                        ('Piña ',1,4,56,' imgs/ingredients/pinapple.png ',1,' Piña miel ',' PiÃ±a miel extra dulce, perfecta para la ensalada tropical '),
                        ('Guayaba ',1,20,38,' imgs/ingredients/ ',1,' Guayaba rosa ',' Exquisita Guayaba rosa ideal para una combinacion de citricos o dulcura tropical '),
                        ('Queso fetta ',1,5,430,' imgs/ingredients/feta-cheese.png ',1,' Queso fetta ',' Delicioso queso fetta ideal para ensaladas mediterraneas '),
                        ('Hielos ',4,4,60,' imgs/ingredients/ ',6,' Hielo en cubos ',' Hielo en cubo para refrescar bebidas o preparar bebidas frapeadas '),
                        ('Bowl grande ',2,6,50,' imgs/ingredients/ ',6,' Bowl deshechable grande ',' Bowl deshechable biodegradable para empacar alimentos '),
                        ('Aceite de oliva ',1,4,299,' imgs/ingredients/ ',5,' Botella de aceite de oliva de 750ml ',' Aceite de oliva extra virgen extraido en frio de muy alta calidad '),
                        ('Queso panela ',1,4,90,' imgs/ingredients/ ',1,' Queso panela fresco ',' Queso fresco ideal para ensaladas frescas ');


DROP TABLE IF EXISTS    user_role;

CREATE TABLE            user_role(

    role_id             INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    role_name           VARCHAR(40) NOT NULL,
    role_description    VARCHAR(255)
);

DROP TABLE IF EXISTS    user;

CREATE TABLE            user(

    user_id             INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username            VARCHAR(40) NOT NULL UNIQUE,
    first_name          VARCHAR(40),
    last_name           VARCHAR(40),
    role_id             INT NOT NULL,
    img                 VARCHAR(100),
    last_login          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT          role_fk FOREIGN KEY (role_id) REFERENCES user_role(role_id)
);


