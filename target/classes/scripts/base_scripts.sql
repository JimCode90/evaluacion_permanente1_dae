show tables;

CREATE DATABASE bd_phone_store;

/*Crear tabla*/
CREATE TABLE productos (

                           id int,
                           categoria varchar(150),
                           codigo varchar(255),
                           nombre varchar(150),
                           precio_venta decimal(11,2),
                           stock int,
                           descripcion varchar(255),
                           created_at timestamp
);
ALTER TABLE productos ADD PRIMARY KEY (id) ;
ALTER TABLE productos MODIFY COLUMN id int auto_increment NOT NULL;

/*INSERTAMOS VALORES*/
INSERT INTO productos (categoria,codigo,nombre,precio_venta,stock,descripcion,created_at)
VALUES ('Verduras','P001','Zapallo', 5.20, 20,'Esta es una descripcion del producto P001.','2018-02-23 18:18:00');

INSERT INTO productos (categoria,codigo,nombre,precio_venta,stock,descripcion,created_at)
VALUES ('Verduras','P002','Zanahorias', 3.20, 20,'Esta es una descripcion del producto P002.','2018-02-23 18:18:00');

INSERT INTO productos (categoria,codigo,nombre,precio_venta,stock,descripcion,created_at)
VALUES ('Bebidas','P003','Inka Cola', 3.20, 20,'Esta es una descripcion del producto P003.','2018-02-23 18:18:00');

INSERT INTO productos (categoria,codigo,nombre,precio_venta,stock,descripcion,created_at)
VALUES ('Bebidas','P004','Guarana', 2.50, 20,'Esta es una descripcion del producto P004.','2018-02-23 18:18:00');

SELECT COUNT(*) FROM productos;

/*Procedimiento almacenado para insertar nuevos productos*/
CREATE PROCEDURE sp_RegistrarProducto(IN p_categoria varchar(150),
                                    IN p_codigo varchar(255),
                                    IN p_nombre varchar(150),
                                    IN p_precio_venta decimal(11,2),
                                    IN p_stock int),
                                    IN p_descripcion varchar(255),
                                    IN p_created_at timestamp,
                                    OUT res INT)
BEGIN
INSERT INTO productos (categoria,codigo,nombre,precio_venta,stock,descripcion,created_at)
VALUES (p_categoria,p_codigo, p_nombre, p_precio_venta, p_stock, p_descripcion, p_created_at);

SELECT * INTO res FROM productos
END;
CALL sp_RegistrarProducto('Bebidas','P005','Coca Cola',3.20, 20, 'Esta es una descripcion del producto P005.','2018-02-23 18:18:00', @outResult);
SELECT @outResult;
SELECT COUNT(*) FROM productos;


/*Procedimiento almacenado para eliminar un registro*/
DELIMITER //
CREATE PROCEDURE sp_ActualizarProducto(
                            IN p_id INT,
                            INT p_precio_venta decimal(11,2),
                            OUT res INT)
BEGIN
UPDATE productos SET precio_venta = p_precio_venta WHERE id = 1;
END;
delimiter ;
CALL sp_ActualizarProducto(2.60);


SELECT * FROM CELULARES ORDER BY id DESC LIMIT 1;

/*ELIMINAMOS EL PRODUCTO CON ID 5*/
SELECT * FROM productos;
DELETE FROM productos WHERE id = 5;
SELECT * FROM productos;