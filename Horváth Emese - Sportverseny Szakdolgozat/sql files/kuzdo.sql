-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 04, 2020 at 09:57 PM
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
-- Table structure for table `kuzdo`
--

CREATE TABLE `kuzdo` (
  `id` int(4) NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `date` date NOT NULL,
  `win` int(4) NOT NULL,
  `lose` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- Dumping data for table `kuzdo`
--

INSERT INTO `kuzdo` (`id`, `name`, `date`, `win`, `lose`) VALUES
(1, 'Birkózás', '2020-01-04', 4, 10),
(2, 'Kungfu', '2020-01-05', 7, 20),
(3, 'Sakk', '2020-02-12', 10, 1),
(6, 'Ju-jitsu', '2020-03-10', 20, 7);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kuzdo`
--
ALTER TABLE `kuzdo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `win` (`win`),
  ADD KEY `lose` (`lose`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kuzdo`
--
ALTER TABLE `kuzdo`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `kuzdo`
--
ALTER TABLE `kuzdo`
  ADD CONSTRAINT `kuzdo_ibfk_1` FOREIGN KEY (`win`) REFERENCES `versenyzok` (`id`),
  ADD CONSTRAINT `kuzdo_ibfk_2` FOREIGN KEY (`lose`) REFERENCES `versenyzok` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
