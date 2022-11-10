--insert into usuario values(3,'Admin',dbo.ENCRIPTA_PASS('PassAdmin'));
--select dbo.desencriptar_pass('pG5P4zz') from usuario;

use ProyectoG5;

-- EJECUTAR 1 por 1
create function ENCRIPTAR_PASS
(
	@clave varchar(50)
)
returns VarBinary(MAX)
as
	begin
		declare @pass as VarBinary(MAX)
		set @pass=ENCRYPTBYPASSPHRASE('pG5P4zz',@clave)
		return @pass
	end
-- EJECUTAR HASTA AQUÍ

-- EJECUTAR 1 por 1
create function DESENCRIPTAR_PASS
(
	@clave varbinary(MAX)
)
returns varchar(50)
as
	begin
		declare @pass as varchar(50)
		set @pass=DECRYPTBYPASSPHRASE('pG5P4zz',@clave)
		return @pass
	end
-- EJECUTAR HASTA AQUÍ