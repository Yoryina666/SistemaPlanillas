USE [master]
GO
/****** Object:  Database [ProyectoG5]    Script Date: 8/12/2022 07:56:58 ******/
CREATE DATABASE [ProyectoG5]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ProyectoG5', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\ProyectoG5.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'ProyectoG5_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\ProyectoG5_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [ProyectoG5] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ProyectoG5].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ProyectoG5] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ProyectoG5] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ProyectoG5] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ProyectoG5] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ProyectoG5] SET ARITHABORT OFF 
GO
ALTER DATABASE [ProyectoG5] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ProyectoG5] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ProyectoG5] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ProyectoG5] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ProyectoG5] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ProyectoG5] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ProyectoG5] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ProyectoG5] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ProyectoG5] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ProyectoG5] SET  DISABLE_BROKER 
GO
ALTER DATABASE [ProyectoG5] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ProyectoG5] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ProyectoG5] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ProyectoG5] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ProyectoG5] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ProyectoG5] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ProyectoG5] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ProyectoG5] SET RECOVERY FULL 
GO
ALTER DATABASE [ProyectoG5] SET  MULTI_USER 
GO
ALTER DATABASE [ProyectoG5] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ProyectoG5] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ProyectoG5] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ProyectoG5] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [ProyectoG5] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [ProyectoG5] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'ProyectoG5', N'ON'
GO
ALTER DATABASE [ProyectoG5] SET QUERY_STORE = OFF
GO
USE [ProyectoG5]
GO
/****** Object:  UserDefinedFunction [dbo].[DESENCRIPTAR_PASS]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create function [dbo].[DESENCRIPTAR_PASS]
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
GO
/****** Object:  UserDefinedFunction [dbo].[ENCRIPTAR_PASS]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create function [dbo].[ENCRIPTAR_PASS]
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
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuario](
	[nombre] [varchar](20) NOT NULL,
	[contrasena] [varbinary](max) NOT NULL,
	[tipo] [tinyint] NOT NULL,
	[vigenciaMaxima] [date] NOT NULL,
	[activo] [bit] NOT NULL,
 CONSTRAINT [PK_Usuario] PRIMARY KEY CLUSTERED 
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[TRY_LOGIN]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE function [dbo].[TRY_LOGIN]
(
	@usuario varchar(20),
	@contrasena varchar(50)
)
returns table
as return SELECT nombre Nombre, tipo Tipo, vigenciaMaxima Vigencia, activo Activo
FROM Usuario U
WHERE U.activo = 1 AND U.nombre = @usuario AND @contrasena = dbo.DESENCRIPTAR_PASS(U.contrasena)
GO
/****** Object:  Table [dbo].[CategoriaDeduccion]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CategoriaDeduccion](
	[nombre] [varchar](20) NOT NULL,
	[descripcion] [varchar](max) NOT NULL,
	[encargado] [varchar](30) NOT NULL,
	[monto] [float] NOT NULL,
	[esPorcentual] [bit] NOT NULL,
	[automatico] [bit] NOT NULL,
 CONSTRAINT [PK_CategoriaDeduccion] PRIMARY KEY CLUSTERED 
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CategoriaPago]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CategoriaPago](
	[nombre] [varchar](20) NOT NULL,
	[descripcion] [varchar](max) NOT NULL,
	[porcentaje] [float] NOT NULL,
	[automatico] [bit] NOT NULL,
 CONSTRAINT [PK_CategoriaPago] PRIMARY KEY CLUSTERED 
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DetalleTransaccion]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DetalleTransaccion](
	[detalleID] [int] IDENTITY(100,1) NOT NULL,
	[transaccionID] [int] NOT NULL,
	[categoriaPagoID] [varchar](20) NULL,
	[categoriaDeduccionID] [varchar](20) NULL,
	[monto] [money] NOT NULL,
 CONSTRAINT [PK_DetalleTransaccion_1] PRIMARY KEY CLUSTERED 
(
	[detalleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Empleado]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Empleado](
	[cedula] [varchar](15) NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[apellido] [varchar](50) NOT NULL,
	[salarioBase] [money] NOT NULL,
	[horas] [smallint] NOT NULL,
	[jornada] [varchar](20) NOT NULL,
	[activo] [bit] NOT NULL,
 CONSTRAINT [PK_Empleado] PRIMARY KEY CLUSTERED 
(
	[cedula] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Planilla]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Planilla](
	[planillaID] [int] IDENTITY(1000,1) NOT NULL,
	[fechaInicio] [date] NOT NULL,
	[fechaFinal] [date] NOT NULL,
	[fechaPago] [date] NOT NULL,
	[jornada] [varchar](20) NOT NULL,
	[turno] [tinyint] NOT NULL,
	[cerrada] [bit] NOT NULL,
 CONSTRAINT [PK_Planilla] PRIMARY KEY CLUSTERED 
(
	[planillaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Transaccion]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Transaccion](
	[transaccionID] [int] IDENTITY(1000,1) NOT NULL,
	[empleadoID] [varchar](15) NOT NULL,
	[planillaID] [int] NOT NULL,
	[salarioBruto] [money] NOT NULL,
	[salarioNeto] [money] NOT NULL,
 CONSTRAINT [PK_Transaccion] PRIMARY KEY CLUSTERED 
(
	[transaccionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[CategoriaDeduccion] ([nombre], [descripcion], [encargado], [monto], [esPorcentual], [automatico]) VALUES (N'CCSS', N'Carga Obrera', N'Empleado', 8, 1, 1)
INSERT [dbo].[CategoriaDeduccion] ([nombre], [descripcion], [encargado], [monto], [esPorcentual], [automatico]) VALUES (N'Impuesto', N'Impuesto al Salario', N'Empleado', 7000, 0, 1)
INSERT [dbo].[CategoriaDeduccion] ([nombre], [descripcion], [encargado], [monto], [esPorcentual], [automatico]) VALUES (N'Parqueo', N'Derechos de Parqueo', N'Empleado', 1000, 0, 0)
INSERT [dbo].[CategoriaDeduccion] ([nombre], [descripcion], [encargado], [monto], [esPorcentual], [automatico]) VALUES (N'Prestamo', N'Prestamo de Cooperativa', N'Cooperativa', 7, 1, 0)
GO
INSERT [dbo].[CategoriaPago] ([nombre], [descripcion], [porcentaje], [automatico]) VALUES (N'Doble', N'Horas Dobles ', 200, 0)
INSERT [dbo].[CategoriaPago] ([nombre], [descripcion], [porcentaje], [automatico]) VALUES (N'Extra', N'Horas Extras ', 150, 0)
INSERT [dbo].[CategoriaPago] ([nombre], [descripcion], [porcentaje], [automatico]) VALUES (N'Incapacidad-CCSS', N'Incapacidades de CCSS', 50, 0)
INSERT [dbo].[CategoriaPago] ([nombre], [descripcion], [porcentaje], [automatico]) VALUES (N'Incapacidad-INS', N'Incapacidades de INS', 50, 0)
INSERT [dbo].[CategoriaPago] ([nombre], [descripcion], [porcentaje], [automatico]) VALUES (N'Maternidad', N'Licencias por Maternidad ', 50, 0)
INSERT [dbo].[CategoriaPago] ([nombre], [descripcion], [porcentaje], [automatico]) VALUES (N'Ordinarias', N'Horas Ordinarias', 100, 1)
INSERT [dbo].[CategoriaPago] ([nombre], [descripcion], [porcentaje], [automatico]) VALUES (N'Vacaciones', N'Vacaciones', 100, 0)
GO
INSERT [dbo].[Empleado] ([cedula], [nombre], [apellido], [salarioBase], [horas], [jornada], [activo]) VALUES (N'107660999', N'Charlie', N'Chaves', 600.0000, 42, N'Semanal', 1)
INSERT [dbo].[Empleado] ([cedula], [nombre], [apellido], [salarioBase], [horas], [jornada], [activo]) VALUES (N'109770123', N'Alfonso', N'Alvarado', 1000.0000, 30, N'Semanal', 1)
INSERT [dbo].[Empleado] ([cedula], [nombre], [apellido], [salarioBase], [horas], [jornada], [activo]) VALUES (N'204430766', N'David', N'Solano', 1200.0000, 40, N'Semanal', 1)
INSERT [dbo].[Empleado] ([cedula], [nombre], [apellido], [salarioBase], [horas], [jornada], [activo]) VALUES (N'307220132', N'Ryan', N'Scully', 4200.0000, 20, N'Semanal', 1)
INSERT [dbo].[Empleado] ([cedula], [nombre], [apellido], [salarioBase], [horas], [jornada], [activo]) VALUES (N'407660120', N'Bryan', N'Morales Segura', 7000.0000, 24, N'Quincenal', 1)
INSERT [dbo].[Empleado] ([cedula], [nombre], [apellido], [salarioBase], [horas], [jornada], [activo]) VALUES (N'507720532', N'Luis', N'Guillermo', 3500.0000, 17, N'Quincenal', 1)
INSERT [dbo].[Empleado] ([cedula], [nombre], [apellido], [salarioBase], [horas], [jornada], [activo]) VALUES (N'806720662', N'Danny', N'Schneider', 10500.0000, 40, N'Quincenal', 1)
GO
INSERT [dbo].[Usuario] ([nombre], [contrasena], [tipo], [vigenciaMaxima], [activo]) VALUES (N'admin', 0x020000003C8C2FC1A4168128186C9C11DA3011D096C74DC738175CAB3821C647798755DF, 0, CAST(N'2024-12-31' AS Date), 1)
INSERT [dbo].[Usuario] ([nombre], [contrasena], [tipo], [vigenciaMaxima], [activo]) VALUES (N'Aylan', 0x02000000CDE6A694A6C2D72B5D7239EA3F37777E73F76F58E30E57567AA7322FBEE4A968F3DEB04E12BDBADDAEE12BBF6CF52E05, 1, CAST(N'2023-03-30' AS Date), 1)
INSERT [dbo].[Usuario] ([nombre], [contrasena], [tipo], [vigenciaMaxima], [activo]) VALUES (N'Gerald', 0x020000000197D5438898229659D4400FB8AA2D374E61BD01DBBB48000563A89DD2540926, 2, CAST(N'2023-02-27' AS Date), 1)
INSERT [dbo].[Usuario] ([nombre], [contrasena], [tipo], [vigenciaMaxima], [activo]) VALUES (N'Yoryina', 0x02000000D59155710C24EC743D4E1A7AEBC57D60DC0FE4CA8EDF2F1BFC8DEF880179A15AEA5CD013222014D3C62819F62F76FAE1, 0, CAST(N'2023-02-10' AS Date), 1)
GO
ALTER TABLE [dbo].[DetalleTransaccion]  WITH CHECK ADD  CONSTRAINT [FK_DetalleTransaccion_CategoriaDeduccion] FOREIGN KEY([categoriaDeduccionID])
REFERENCES [dbo].[CategoriaDeduccion] ([nombre])
GO
ALTER TABLE [dbo].[DetalleTransaccion] CHECK CONSTRAINT [FK_DetalleTransaccion_CategoriaDeduccion]
GO
ALTER TABLE [dbo].[DetalleTransaccion]  WITH CHECK ADD  CONSTRAINT [FK_DetalleTransaccion_CategoriaPago] FOREIGN KEY([categoriaPagoID])
REFERENCES [dbo].[CategoriaPago] ([nombre])
GO
ALTER TABLE [dbo].[DetalleTransaccion] CHECK CONSTRAINT [FK_DetalleTransaccion_CategoriaPago]
GO
ALTER TABLE [dbo].[DetalleTransaccion]  WITH CHECK ADD  CONSTRAINT [FK_DetalleTransaccion_Transaccion] FOREIGN KEY([transaccionID])
REFERENCES [dbo].[Transaccion] ([transaccionID])
GO
ALTER TABLE [dbo].[DetalleTransaccion] CHECK CONSTRAINT [FK_DetalleTransaccion_Transaccion]
GO
ALTER TABLE [dbo].[Transaccion]  WITH CHECK ADD  CONSTRAINT [FK_Transaccion_Empleado] FOREIGN KEY([empleadoID])
REFERENCES [dbo].[Empleado] ([cedula])
GO
ALTER TABLE [dbo].[Transaccion] CHECK CONSTRAINT [FK_Transaccion_Empleado]
GO
ALTER TABLE [dbo].[Transaccion]  WITH CHECK ADD  CONSTRAINT [FK_Transaccion_Planilla] FOREIGN KEY([planillaID])
REFERENCES [dbo].[Planilla] ([planillaID])
GO
ALTER TABLE [dbo].[Transaccion] CHECK CONSTRAINT [FK_Transaccion_Planilla]
GO
/****** Object:  StoredProcedure [dbo].[CREATE_USER]    Script Date: 8/12/2022 07:56:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATE_USER] 
    @nombre varchar(20),   
    @contrasena varchar(12) ,
	@tipo tinyint
AS
    SET NOCOUNT ON;
	INSERT INTO Usuario VALUES (@nombre, dbo.ENCRIPTAR_PASS(@contrasena), @tipo, DATEADD(m, 3, GETDATE()), 1);
GO
USE [master]
GO
ALTER DATABASE [ProyectoG5] SET  READ_WRITE 
GO
