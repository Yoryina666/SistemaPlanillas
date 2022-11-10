Go;
create table usuario
(
id_usuario INT primary key,
usuario varchar(20) not null,
pass varbinary(500) not null
)

go;
alter function ENCRIPTA_PASS
 (
 @clave varchar(50)
 )
 returns VarBinary(500)
 as
 begin
 declare @pass as VarBinary(500)
 set @pass=ENCRYPTBYPASSPHRASE('MiclaveSecreta',@clave)
 return @pass
 end


 go;
alter function desencriptar_pass
  (
  @clave varbinary(500)
  )
  returns varchar(50)
  as
  begin
  declare @pass as varchar(50)
  set @pass=DECRYPTBYPASSPHRASE('MiclaveSecreta',@clave)
  return @pass
  end

delete from usuario

insert into usuario values(1,'Geovanny Chacon',dbo.ENCRIPTA_PASS('PassSistema'))
insert into usuario values(2,'Pedro Castro',dbo.ENCRIPTA_PASS('123456'))
insert into usuario values(3,'Admin',dbo.ENCRIPTA_PASS('PassAdmin'))


Select * from usuario
select id_usuario,usuario,dbo.desencriptar_pass(pass) from usuario

Select * from usuario


SELECT c.Cod_Provincia, c.Cod_Canton, c.DSC_Canton, c.LOG_Activo from Canton c