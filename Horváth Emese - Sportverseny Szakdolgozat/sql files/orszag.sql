-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 04, 2020 at 09:58 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sport`
--

-- --------------------------------------------------------

--
-- Table structure for table `orszag`
--

CREATE TABLE `orszag` (
  `id` int(4) NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `letszam` int(1) DEFAULT 0,
  `pont` int(10) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- Dumping data for table `orszag`
--

INSERT INTO `orszag` (`id`, `name`, `letszam`, `pont`) VALUES
(1, 'Ausztria', 1, 3),
(2, 'Belgium', 0, NULL),
(3, 'Bulgária', 0, NULL),
(4, 'Ciprus', 1, 1),
(8, 'Finnország', 1, 1),
(9, 'Franciaország', 0, NULL),
(10, 'Görögország', 0, NULL),
(11, 'Hollandia', 1, 3),
(13, 'Írország', 2, 8),
(14, 'Lengyelország', 1, 3),
(16, 'Litvánia', 0, 0),
(17, 'Luxemburg', 0, NULL),
(18, 'Magyarország', 2, 7),
(37, 'Csehország', 0, NULL),
(40, 'Dánia', 0, NULL),
(42, 'Kína', 1, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `orszag`
--
ALTER TABLE `orszag`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `orszag`
--
ALTER TABLE `orszag`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
