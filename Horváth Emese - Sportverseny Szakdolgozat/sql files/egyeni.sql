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
-- Table structure for table `egyeni`
--

CREATE TABLE `egyeni` (
  `id` int(4) NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `date` date NOT NULL,
  `1st` int(4) NOT NULL,
  `2nd` int(4) NOT NULL,
  `3rd` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- Dumping data for table `egyeni`
--

INSERT INTO `egyeni` (`id`, `name`, `date`, `1st`, `2nd`, `3rd`) VALUES
(4, 'Evezés', '2020-01-02', 1, 9, 4),
(5, 'Sízés', '2020-01-05', 10, 2, 5),
(6, 'Íjászat', '2020-01-06', 6, 10, 4),
(8, 'Hódeszka', '2020-03-13', 8, 1, 7);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `egyeni`
--
ALTER TABLE `egyeni`
  ADD PRIMARY KEY (`id`),
  ADD KEY `1st` (`1st`),
  ADD KEY `2nd` (`2nd`),
  ADD KEY `3rd` (`3rd`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `egyeni`
--
ALTER TABLE `egyeni`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `egyeni`
--
ALTER TABLE `egyeni`
  ADD CONSTRAINT `egyeni_ibfk_1` FOREIGN KEY (`1st`) REFERENCES `versenyzok` (`id`),
  ADD CONSTRAINT `egyeni_ibfk_2` FOREIGN KEY (`2nd`) REFERENCES `versenyzok` (`id`),
  ADD CONSTRAINT `egyeni_ibfk_3` FOREIGN KEY (`3rd`) REFERENCES `versenyzok` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
