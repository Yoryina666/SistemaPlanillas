USE [master]
GO
/****** Object:  Database [ProyectoG5]    Script Date: 7/11/2022 12:51:25 ******/
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
/****** Object:  Table [dbo].[CategoriaDeduccion]    Script Date: 7/11/2022 12:51:26 ******/
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
/****** Object:  Table [dbo].[CategoriaPago]    Script Date: 7/11/2022 12:51:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CategoriaPago](
	[nombre] [varchar](20) NOT NULL,
	[descripcion] [varchar](max) NOT NULL,
	[porcentaje] [tinyint] NOT NULL,
	[automatico] [bit] NOT NULL,
 CONSTRAINT [PK_CategoriaPago] PRIMARY KEY CLUSTERED 
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DetalleTransaccion]    Script Date: 7/11/2022 12:51:26 ******/
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
/****** Object:  Table [dbo].[Empleado]    Script Date: 7/11/2022 12:51:26 ******/
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
/****** Object:  Table [dbo].[Planilla]    Script Date: 7/11/2022 12:51:26 ******/
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
/****** Object:  Table [dbo].[Transaccion]    Script Date: 7/11/2022 12:51:26 ******/
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
/****** Object:  Table [dbo].[Usuario]    Script Date: 7/11/2022 12:51:26 ******/
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
USE [master]
GO
ALTER DATABASE [ProyectoG5] SET  READ_WRITE 
GO
select*from Usuario
use ProyectoG5
INSERT INTO Usuario(nombre,contrasena,tipo,vigenciaMaxima,
                    activo) VALUES(1234,123456,1,'12/12/2023',1);