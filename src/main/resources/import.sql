
-- USUARIO 'Piero', ROL 'Administrador', CLAVE: piero123
-- USUARIO 'Hawell', ROL 'Cajero', CLAVE: hawell123
-- USUARIO 'Stefano', ROL 'Camarero', CLAVE: stefano123
INSERT INTO db_restaurante.usuarios (nombre, apellido, correo, clave, rol, activo) VALUES ('Piero', 'Juarez', 'piero@gmail.com', '$2a$12$i9pdKKWCX60c4NKqSs/sS.75.8TLJXbVJbnm0ZJfRq9OLZQL9NR/u', 'ADMINISTRADOR', true);
INSERT INTO db_restaurante.usuarios (nombre, apellido, correo, clave, rol, activo) VALUES ('Hawell', 'Urbina', 'hawell@gmail.com', '$2a$12$evLO6/YQfEcgawZtNt.skudwNfgQuBLdKpiqEBAQVcSP12X0SFBzO', 'CAJERO', true);
INSERT INTO db_restaurante.usuarios (nombre, apellido, correo, clave, rol, activo) VALUES ('Stefano', 'Gonzales', 'stefano@gmail.com', '$2a$12$/AFzfjhvwOniF2Q/Wb1E6.kItsoyB7nDj1uO.G.iF0y/IcBeQcPpK', 'CAMARERO', true);

INSERT INTO db_restaurante.categorias_items (activo, descripcion, nombre, precio_minimo) VALUES (true, 'Delicosas hamburguesas jugosas', 'Hamburguesas',10.0);
INSERT INTO db_restaurante.categorias_items (activo, descripcion, nombre, precio_minimo) VALUES (true, 'Refrescantes bebidas gaseosas de siempre', 'Gaseosas',3.0);
INSERT INTO db_restaurante.categorias_items (activo, descripcion, nombre, precio_minimo) VALUES (true, 'Platillos del norte que te harán babear', 'Comida Norteña',10.0);
INSERT INTO db_restaurante.categorias_items (activo, descripcion, nombre, precio_minimo) VALUES (true, 'Platos tipicos de la selva', 'Comida de la Selva',15.0);
INSERT INTO db_restaurante.categorias_items (activo, descripcion, nombre, precio_minimo) VALUES (true, 'Aperitivos', 'Entradas',8.0);
INSERT INTO db_restaurante.categorias_items (activo, descripcion, nombre, precio_minimo) VALUES (true, 'Platillos abundantes de fideos', 'Pastas',15.0);
INSERT INTO db_restaurante.categorias_items (activo, descripcion, nombre, precio_minimo) VALUES (true, 'Dulces tradicionales para endulzar el paladar', 'Postres',10.0);

INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 1, 14, 'Hamburguesa triple parrillera ahumada al carbón', 'Hamburguesa Parrillera', 'https://i.ibb.co/Pzt1Zb2V/3-DLCu5-Wt2-Nmyqbc-E5-1080-x.webp', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 2, 3.5, 'Gaseosa con sabor a hierba luisa', 'Inka Kola 1L', 'https://i.ibb.co/VckFj98Z/20111231.webp', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 2, 6, 'Gaseosa con sabor a hierba luisa', 'Inka Kola 2.5L', 'https://i.ibb.co/Y76CTBcH/20256774.webp', 'DESHABILITADO');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 3, 26, 'Descripción de comida deliciosa', 'Arroz con pollo y crema huancaina', 'https://i.ibb.co/Wv7pK51f/hq720.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 3, 34, 'Descripción de comida deliciosa', 'Frijoles con pollo a la olla', 'https://i.ibb.co/ns6stpNq/sddefault.jpg', 'DESHABILITADO');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 3, 23, 'Descripción de comida deliciosa', 'Seco de pollo con pure de papa', 'https://i.ibb.co/pF5ntFt/maxresdefault.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 1, 18, 'Hamburguesa con huevo frito y plátano frito al estilo peruano', 'Hamburguesa Criolla', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756146355/tqtaxvd5lhzvglhgqn8v.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 1, 20, 'Hamburguesa con ají amarillo y salsa huancaína', 'Hamburguesa Huancaína', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756146538/k15djglpwf4rbqlsrh9c.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 2, 4, 'Bebida de sabor a cola, clásica en Perú', 'Coca Cola 500ml', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756146765/nuvzcsjjb254xhma4o1r.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 2, 4, 'Bebida de sabor a cola, clásica en Perú', 'Coca Cola 3L', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756151250/zydbzt6dyyilvbasfww4.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 3, 28, 'Ceviche norteño fresco con camote y choclo', 'Ceviche de Pescado', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756147059/yn8ll694rn8msgqqfikq.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 3, 30, 'Arroz con cabrito tierno y jugoso, típico del norte', 'Arroz con Cabrito', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756147287/l246oazbdhytoka1ubs2.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 4, 22, 'Juane tradicional envuelto en hojas de bijao', 'Juane de Gallina', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756148112/q8pdujvpt8gq6ue3ekln.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 4, 25, 'Tacacho con cecina y chorizo amazónico', 'Tacacho con Cecina', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756148205/srshzhoxxskhtkyn0mpb.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 5, 12, 'Papa bañada en salsa huancaína con huevo y aceituna', 'Papa a la Huancaína', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756148300/gnesdlj3terkuxtq5rnp.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 5, 14, 'Causa limeña con pollo y palta fresca', 'Causa Limeña de Pollo', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756148376/vbi0tlaaz7izcms1yucs.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 6, 22, 'Tallarin verde al pesto peruano con bistec apanado', 'Tallarín Verde con Bistec', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756148469/von63srfdto8jplrrhco.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 6, 24, 'Tallarin rojo criollo con pollo guisado', 'Tallarín Rojo con Pollo', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756150619/wlm8rqkfk3dz2dx0pus1.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 7, 10, 'Postre limeño de manjar y merengue', 'Suspiro a la Limeña', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756150853/eq193w5m4ktjx6ztawli.png', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 7, 9, 'Postre a base de maíz morado con frutas', 'Mazamorra Morada', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756150944/tmrj1slqeyfiovjfpfx7.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 7, 15, 'Arroz con leche con canela y pasas', 'Arroz con Leche', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756151026/fxg2osvlewwuiykkbgfp.jpg', 'DISPONIBLE');
INSERT INTO db_restaurante.items_menu (activo, categoria_id, precio, descripcion, nombre, enlace_imagen, estado) VALUES (true, 7, 12, 'Dulce tradicional peruano de yuca rallada', 'Picarones con Miel', 'https://res.cloudinary.com/dmqzrq0w7/image/upload/v1756151111/o54oauwvczdkpqqoqzus.jpg', 'DISPONIBLE');

INSERT INTO db_restaurante.mesas (capacidad, numero, estado) VALUES (4, '1', 'LIBRE');
INSERT INTO db_restaurante.mesas (capacidad, numero, estado) VALUES (4, '2', 'LIBRE');
INSERT INTO db_restaurante.mesas (capacidad, numero, estado) VALUES (5, '3', 'LIBRE');
INSERT INTO db_restaurante.mesas (capacidad, numero, estado) VALUES (10, '4', 'LIBRE');
INSERT INTO db_restaurante.mesas (capacidad, numero, estado) VALUES (2, '5', 'LIBRE');
INSERT INTO db_restaurante.mesas (capacidad, numero, estado) VALUES (4, '6', 'LIBRE');
INSERT INTO db_restaurante.mesas (capacidad, numero, estado) VALUES (5, '7', 'LIBRE');
INSERT INTO db_restaurante.mesas (capacidad, numero, estado) VALUES (10, '8', 'LIBRE');
