delimiter $
create procedure Registro (in idPersona INT,in Nombre VARCHAR(45), in Correo VARCHAR(45),in telefono VARCHAR(45),in tipo INT)
begin
INSERT INTO `mydb`.`usuario`(`idUsuario`,`Nombre`,`Correo`,`Telefono`,`Puntos`,`Tipo`)
VALUES(idPersona,Nombre,Correo,Telefono,0,Tipo);
end $

delimiter $
create procedure VerPerfil (in idPersona INT)
begin
SELECT `usuario`.`Nombre`,`usuario`.`Correo`,`usuario`.`Telefono` FROM `mydb`.`usuario` where usuario.idUsuario=idPersona;
end $

delimiter $
create procedure GuardarPerfil (in idPersona INT,in Nombre VARCHAR(45),in telefono VARCHAR(45))
begin
UPDATE `mydb`.`usuario` SET `Nombre` = Nombre,`Telefono` = Telefono WHERE `idUsuario` = idPersona;
end $

delimiter $
create procedure BuscarAmigo (in idPersona int, in Nombre varchar(45))
begin
SELECT `usuario`.`idUsuario`,`usuario`.`Nombre`,`usuario`.`Telefono` FROM `mydb`.`usuario` where (INSTR(usuario.Nombre,Nombre) > 0) and usuario.idUsuario not in
(SELECT `amigos`.`Usuario_idUsuario1` FROM `mydb`.`amigos` where Usuario_idUsuario = idPersona);
end $




delimiter $
create procedure AgregarAmigos(in idPersona INT,in idAmigo INT)
begin
INSERT INTO `mydb`.`amigos`(`Usuario_idUsuario`,`Usuario_idUsuario1`)
VALUES(idPersona,idAmigo);
end $

delimiter $
create procedure ListaAmigos(in idPersona INT)
begin
SELECT amigos.Usuario_idUsuario1 FROM `mydb`.`amigos` inner join usuario on amigos.Usuario_idUsuario=usuario.idUsuario where  amigos.Usuario_idUsuario=idPersona;
end $



delimiter $
create procedure AgregarVehiculos(in Marca varchar(45),in Modelo varchar(45),in Capacidad int,in Placa varchar(20),in idPersona INT)
begin
INSERT INTO `mydb`.`vehiculo`(`Marca`,`Modelo`,`Capacidad`,`Placa`,`Usuario_idUsuario`)
VALUES(Marca,Modelo,Capacidad,Placa,idPersona );
end$


delimiter $
create procedure ListaVehiculos(in idPersona INT)
begin
SELECT `vehiculo`.`Modelo`,`vehiculo`.`Capacidad`,`vehiculo`.`Placa`FROM `mydb`.`vehiculo` inner join usuario on vehiculo.Usuario_idUsuario=usuario.idUsuario where usuario.idUsuario=idPersona
and `mydb`.`vehiculo`.`Habilitado` = 1;
end$

delimiter $
create procedure CrearViaje (in Parqueo int,in Placa varchar(45))
begin
INSERT INTO `mydb`.`viaje` (`Vehiculo_idVehiculo`)
VALUES((SELECT `vehiculo`.`idVehiculo`FROM `mydb`.`vehiculo` WHERE vehiculo.Placa=Placa));
INSERT INTO `mydb`.`parqueo`(`Parqueo`,`Viaje_idViaje`)
VALUES(Parqueo,(SELECT max(idViaje) from `mydb`.`viaje`));
SELECT max(idViaje) from `mydb`.`viaje`;
end $


delimiter $
create procedure AsignarViaje (in idViaje int,in idUsuario int)
begin
INSERT INTO `mydb`.`usuarioxviaje`(`Viaje_idViaje`,`Usuario_idUsuario`)
VALUES(idViaje,idUsuario);
end $

delimiter $
create procedure CrearCategoria (in Categoria varchar(45),in Viajes int,in puntos int, in puntosTotales int)
begin
INSERT INTO `mydb`.`categoria`(`Categoria`,`ViajesMin`,`PuntosMin`,`PuntosTotales`)
VALUES(Categoria,Viajes,Puntos,puntosTotales);
end $

delimiter $
create procedure AsignarPuntosViaje (in idViaje int,in Puntos int)
begin
UPDATE `mydb`.`viaje` SET `PuntosObtenidos` = Puntos  WHERE `viaje`.`idViaje` =idViaje;
end $

delimiter $
create procedure ListaViaje (in idViaje int)
begin
SELECT `viaje`.`Fecha`,`viaje`.`PuntosObtenidos`,count(Usuario_idUsuario),parqueo.Parqueo
FROM `mydb`.`viaje` inner join usuarioxviaje on viaje.idViaje=usuarioxviaje.Viaje_idViaje
inner join parqueo on parqueo.Viaje_idViaje=idViaje;
end $

delimiter $
create procedure VerNotificacionNoVistas (in idPersona INT)
begin
SELECT `notificacion`.`idNotificacion`,`notificacion`.`Notificacion`,`notificacion`.`Fecha`,`notificacion`.`Tipo` FROM `mydb`.`notificacion` where notificacion.Dueño = idPersona;
UPDATE `mydb`.`notificacion` SET `Mostrado` = 1 WHERE notificacion.Dueño = idPersona and Mostrado=0;
end $

delimiter $
create procedure VerNotificacion (in idPersona INT)
begin
SELECT `notificacion`.`idNotificacion`,`notificacion`.`Notificacion`,`notificacion`.`Fecha`,`notificacion`.`Tipo` FROM `mydb`.`notificacion` where notificacion.Dueño=idPersona;
end $




delimiter $
create procedure CrearNotificacion (in idDueño INT,in idOrigen int,in Notificacion varchar(255),in tipo int)
begin
INSERT INTO `mydb`.`notificacion` (`Notificacion`,`Dueño`,`Tipo`,`Origen`)
VALUES(Notificacion,idDueño,Tipo,idOrigen);
end $

delimiter $
create procedure VerOrigenNotificacion (in idNotificacion INT)
begin
SELECT `notificacion`.`Origen` FROM `mydb`.`notificacion` WHERE notificacion.idNotificacion=idNotificacion;
end $

delimiter $
create procedure VerCapacidadVehiculo (in Placa varchar(45))
begin
SELECT `vehiculo`.`Capacidad` FROM `mydb`.`vehiculo` where vehiculo.Placa=Placa;
end $

delimiter $
create procedure VerCategoria(in idPersona INT)
begin
SELECT `categoria`.`Categoria`, `categoria`.`PuntosTotales` FROM `mydb`.`categoria` inner join usuario on usuario.Categoria_idCategoria=categoria.idCategoria where usuario.idUsuario=idPersona;
end $

delimiter $
create procedure AsignarCategoria (in idPersona INT,in Categoria varchar(45))
begin
UPDATE `mydb`.`usuario` 
SET `Categoria_idCategoria` = (SELECT `categoria`.`idCategoria` FROM `mydb`.`categoria` WHERE categoria.Categoria=Categoria)
WHERE `idUsuario` = idPersona;
end $

delimiter $
create procedure CrearPosibleViaje(in Capacidad int, in Placa varchar(45))
begin
INSERT INTO `mydb`.`posibleviaje`(`Capacidad`,`Placa`)
VALUES(Capacidad,Placa);
SELECT max(idPosibleViaje) from  `mydb`.`posibleviaje`;
end $

delimiter $
create procedure AsignarAcompañante(in idPViaje int,in idPersona int)
begin
INSERT INTO `mydb`.`acompañantes` (`PosibleViaje_idPosibleViaje`,`Usuario_idUsuario`)
VALUES (idPViaje,idPersona);
end $

delimiter $
create procedure ConfirAcompañante(in idPViaje int,in idPersona int)
begin
UPDATE `mydb`.`acompañantes`
SET `Aceptaron` = 1 WHERE acompañantes.PosibleViaje_idPosibleViaje=idPViaje and acompañantes.Usuario_idUsuario=idPersona;
end $

delimiter $
create procedure NumeroConfir(in idPViaje int)
begin
SELECT count(`acompañantes`.`Usuario_idUsuario`)
FROM `mydb`.`acompañantes`where acompañantes.Aceptaron=1 and acompañantes.PosibleViaje_idPosibleViaje=idPViaje;
end $

delimiter $
create procedure VerCapacidadPV(in idPViaje int)
begin
SELECT posibleviaje.Capacidad from  `mydb`.`posibleviaje` where posibleviaje.idPosibleViaje=idPViaje;
end $

delimiter $
create procedure VerPlacaPV(in idPViaje int)
begin
SELECT posibleviaje.Placa from  `mydb`.`posibleviaje` where posibleviaje.idPosibleViaje=idPViaje;
end $

delimiter $
create procedure HacerViaje(in idPViaje int)
begin
SELECT `acompañantes`.`Usuario_idUsuario`
FROM `mydb`.`acompañantes`where acompañantes.Aceptaron=1 and acompañantes.PosibleViaje_idPosibleViaje=idPViaje;
#DELETE FROM `mydb`.`posibleviaje` WHERE posibleviaje.idPosibleViaje=idPViaje;
#DELETE FROM `mydb`.`acompañantes`WHERE acompañantes.PosibleViaje_idPosibleViaje=idPViaje;
end $

delimiter $
create procedure AsignarCodigo(in codigo varchar(45),in idUsuario int,in idViaje int)
begin
INSERT INTO `mydb`.`codigo` (`Codigo`,`Usuario_idUsuario`,`Viaje_idViaje`)
VALUES(codigo,idUsuario,idViaje);
end $

delimiter $
create procedure ViajesSemana (in idPersona INT,in Semana INT, in Mes INT)
begin
SELECT count(`usuarioxviaje`.`Viaje_idViaje`)
FROM `mydb`.`usuarioxviaje` inner join viaje on usuarioxviaje.Viaje_idViaje=viaje.idViaje
Where usuarioxviaje.Usuario_idUsuario=idPersona and day(viaje.Fecha)>((Semana-1)*8) and day(viaje.Fecha)<=(((Semana-1)*8)+8) and month(viaje.Fecha)=Mes;
end $

delimiter $
create procedure ListaViajeUsuario (in idUsuario int)
begin
select usuarioxviaje.Viaje_idViaje From usuarioxviaje where usuarioxviaje.Usuario_idUsuario=idUsuario;
end $

delimiter $
create procedure Chofer (in idUsuario int, in Semana INT, in Mes INT)
begin
select count(notificacion.Origen) From notificacion where notificacion.Origen=idUsuario and notificacion.Tipo='9'
and not(notificacion.Origen=notificacion.Dueño) and day(Fecha)>((Semana-1)*8) 
and day(Fecha)<=(((Semana-1)*8)+8) and month(Fecha)=Mes;
end $

delimiter $
create procedure Pasajero (in idUsuario int, in Semana INT, in Mes INT)
begin
select count(notificacion.Dueño) From notificacion where notificacion.Dueño=idUsuario and notificacion.Tipo='9'
and not(notificacion.Origen=notificacion.Dueño) and day(Fecha)>((Semana-1)*8) 
and day(Fecha)<=(((Semana-1)*8)+8) and month(Fecha)=Mes;
end $

delimiter $
create procedure VerPuntosUsuario (in idUsuario int)
begin
SELECT `usuario`.`Puntos` FROM `mydb`.`usuario`where usuario.idUsuario=idUsuario;
end $

delimiter $
create procedure CambiarPuntosUsuario (in idUsuario int,in puntos int)
begin
UPDATE `mydb`.`usuario`
SET `Puntos` = Puntos WHERE `idUsuario` =idUsuario;
end $

delimiter $
create procedure esAdministrador(in idUsuario int)
begin
SELECT (CASE Tipo WHEN 1 THEN 1 ELSE 0 END) as Administrador FROM usuario;
end $

delimiter $
create procedure CrearCorreos(in Correo varchar(45))
begin
INSERT INTO `mydb`.`correos`(`Correo`)
VALUES(Correo);
end $

delimiter $
create procedure EliminarCorreos(in Correo varchar(45))
begin
DELETE FROM `mydb`.`correos`
WHERE correos.Correo=correo;
end $

delimiter $
create procedure ObtenerCorreos()
begin
SELECT `mydb`.`correos`.`Correo` FROM `mydb`.`correos`;
end $


delimiter $
create procedure VerCategorias ()
begin
SELECT * FROM `mydb`.`categoria`;
end $


delimiter $
create procedure EliminarVehiculo (in Placa varchar(45))
begin
UPDATE `mydb`.`vehiculo`
SET `Habilitado` = 0 WHERE `Placa` = placa;
end $

delimiter $
create procedure EliminarPerfil (in idPersona INT)
begin
UPDATE `mydb`.`usuario`
SET `Habilitado` = 0 WHERE `idUsuario` = idPersona;
end $

delimiter $
create procedure Habilitado (in idUsuario int)
begin
SELECT `usuario`.`Habilitado` FROM `mydb`.`usuario` where usuario.idUsuario=idUsuario;
end $









