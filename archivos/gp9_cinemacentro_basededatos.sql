-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-11-2025 a las 20:33:36
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gp9_cinemacentro_basededatos`
--
CREATE DATABASE IF NOT EXISTS `gp9_cinemacentro_basededatos` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gp9_cinemacentro_basededatos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comprador`
--

DROP TABLE IF EXISTS `comprador`;
CREATE TABLE `comprador` (
  `Id_Comprador` int(11) NOT NULL,
  `DNI` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `password` int(11) NOT NULL,
  `medioPago` varchar(30) NOT NULL,
  `fechaNac` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `comprador`
--

INSERT INTO `comprador` (`Id_Comprador`, `DNI`, `nombre`, `password`, `medioPago`, `fechaNac`) VALUES
(10, 4, '4', 4, '4', '2025-11-04'),
(11, 2, 'd', 2, 'dd', '2025-11-09');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar`
--

DROP TABLE IF EXISTS `lugar`;
CREATE TABLE `lugar` (
  `Id_lugar` int(11) NOT NULL,
  `Id_proyeccion` int(11) NOT NULL,
  `fila` int(11) NOT NULL,
  `numero` int(11) NOT NULL,
  `estado` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pelicula`
--

DROP TABLE IF EXISTS `pelicula`;
CREATE TABLE `pelicula` (
  `id_Pelicula` int(11) NOT NULL,
  `titulo` varchar(30) NOT NULL,
  `director` varchar(30) NOT NULL,
  `actores` varchar(30) NOT NULL,
  `origen` varchar(30) NOT NULL,
  `genero` varchar(30) NOT NULL,
  `estreno` date NOT NULL,
  `enCartelera` int(11) NOT NULL,
  `activa` tinyint(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pelicula`
--

INSERT INTO `pelicula` (`id_Pelicula`, `titulo`, `director`, `actores`, `origen`, `genero`, `estreno`, `enCartelera`, `activa`) VALUES
(9, '', '', 'qqqqq', 'qqqq', 'qqqq', '2025-11-05', 1, 0),
(10, 'qqqqqq', 'qqqqqq', 'qqqqq', 'qqqq', 'qqqq', '2025-11-05', 0, 1),
(11, 'la era de hielo', 'qsy', 'andy', 'yankee', 'comedia', '2025-11-14', 1, 1),
(12, 'eeeeeeeeee', 'eeeeeeeee', 'eeeeeeee', 'eeeeeeee', 'eeeeeeee', '2025-11-30', 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyeccion`
--

DROP TABLE IF EXISTS `proyeccion`;
CREATE TABLE `proyeccion` (
  `Id_proyeccion` int(11) NOT NULL,
  `Id_pelicula` int(11) NOT NULL,
  `Id_sala` int(11) NOT NULL,
  `idioma` varchar(30) NOT NULL,
  `es3D` int(11) NOT NULL,
  `subtitulada` int(11) NOT NULL,
  `horaInicio` time NOT NULL,
  `horaFin` time NOT NULL,
  `precio` double NOT NULL,
  `activa` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proyeccion`
--

INSERT INTO `proyeccion` (`Id_proyeccion`, `Id_pelicula`, `Id_sala`, `idioma`, `es3D`, `subtitulada`, `horaInicio`, `horaFin`, `precio`, `activa`) VALUES
(14, 11, 14, 'español', 1, 1, '22:30:00', '23:00:00', 2000, 0),
(15, 10, 15, 'ingles', 0, 1, '20:00:00', '21:00:00', 10000, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sala`
--

DROP TABLE IF EXISTS `sala`;
CREATE TABLE `sala` (
  `Id_sala` int(11) NOT NULL,
  `nroSala` int(11) NOT NULL,
  `apta3D` int(11) NOT NULL,
  `capacidad` int(11) NOT NULL,
  `estado` tinyint(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `sala`
--

INSERT INTO `sala` (`Id_sala`, `nroSala`, `apta3D`, `capacidad`, `estado`) VALUES
(13, 30, 1, 200, 1),
(14, 4, 1, 100, 1),
(15, 6, 0, 7, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket`
--

DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `Id_ticket` int(11) NOT NULL,
  `Id_comprador` int(11) NOT NULL,
  `Id_lugar` int(11) NOT NULL,
  `fechaCompra` date NOT NULL,
  `fechaFuncion` date NOT NULL,
  `monto` double NOT NULL,
  `activo` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comprador`
--
ALTER TABLE `comprador`
  ADD PRIMARY KEY (`Id_Comprador`);

--
-- Indices de la tabla `lugar`
--
ALTER TABLE `lugar`
  ADD PRIMARY KEY (`Id_lugar`),
  ADD KEY `Id_proyeccion` (`Id_proyeccion`);

--
-- Indices de la tabla `pelicula`
--
ALTER TABLE `pelicula`
  ADD PRIMARY KEY (`id_Pelicula`);

--
-- Indices de la tabla `proyeccion`
--
ALTER TABLE `proyeccion`
  ADD PRIMARY KEY (`Id_proyeccion`),
  ADD KEY `Id_pelicula` (`Id_pelicula`),
  ADD KEY `Id_sala` (`Id_sala`);

--
-- Indices de la tabla `sala`
--
ALTER TABLE `sala`
  ADD PRIMARY KEY (`Id_sala`);

--
-- Indices de la tabla `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`Id_ticket`),
  ADD KEY `Id_comprador` (`Id_comprador`),
  ADD KEY `Id_lugar` (`Id_lugar`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comprador`
--
ALTER TABLE `comprador`
  MODIFY `Id_Comprador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `lugar`
--
ALTER TABLE `lugar`
  MODIFY `Id_lugar` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pelicula`
--
ALTER TABLE `pelicula`
  MODIFY `id_Pelicula` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `proyeccion`
--
ALTER TABLE `proyeccion`
  MODIFY `Id_proyeccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `sala`
--
ALTER TABLE `sala`
  MODIFY `Id_sala` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `ticket`
--
ALTER TABLE `ticket`
  MODIFY `Id_ticket` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `lugar`
--
ALTER TABLE `lugar`
  ADD CONSTRAINT `lugar_ibfk_1` FOREIGN KEY (`Id_proyeccion`) REFERENCES `proyeccion` (`Id_proyeccion`);

--
-- Filtros para la tabla `proyeccion`
--
ALTER TABLE `proyeccion`
  ADD CONSTRAINT `proyeccion_ibfk_1` FOREIGN KEY (`Id_pelicula`) REFERENCES `pelicula` (`id_Pelicula`),
  ADD CONSTRAINT `proyeccion_ibfk_2` FOREIGN KEY (`Id_sala`) REFERENCES `sala` (`Id_sala`);

--
-- Filtros para la tabla `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`Id_comprador`) REFERENCES `comprador` (`Id_Comprador`),
  ADD CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`Id_lugar`) REFERENCES `lugar` (`Id_lugar`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
