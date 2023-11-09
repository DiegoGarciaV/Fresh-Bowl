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