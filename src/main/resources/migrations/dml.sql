-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 04, 2020 at 06:53 AM
-- Server version: 5.7.29-0ubuntu0.18.04.1
-- PHP Version: 7.2.24-0ubuntu0.18.04.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `notestasks`
--

--
-- Dumping data for table `connections`
--

INSERT INTO `connections` (`user_connection_id`, `user_id`) VALUES
(5, 1),
(5, 2);

--
-- Dumping data for table `connection_requests`
--

INSERT INTO `connection_requests` (`id`, `created_on`, `deleted_on`, `is_deleted`, `updated_on`, `receiver_id`, `sender_id`) VALUES
(7, '2020-05-04 12:32:02.277000', '2020-05-04 12:32:06.357000', b'1', '2020-05-04 12:32:06.359000', 1, 2),
(8, '2020-05-04 12:32:10.108000', '2020-05-04 12:32:13.437000', b'1', '2020-05-04 12:32:13.438000', 1, 2),
(9, '2020-05-04 12:32:17.114000', '2020-05-04 12:32:39.016000', b'1', '2020-05-04 12:32:39.018000', 1, 2),
(10, '2020-05-04 12:32:43.509000', '2020-05-04 12:40:20.053000', b'1', '2020-05-04 12:40:20.053000', 2, 1),
(11, '2020-05-04 12:40:59.841000', '2020-05-04 12:41:49.559000', b'1', '2020-05-04 12:41:49.560000', 1, 2);

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `created_on`, `deleted_on`, `is_deleted`, `updated_on`, `email`, `is_email_verified`, `name`, `password`, `phone`, `role`, `secret`, `about`) VALUES
(1, NULL, NULL, b'0', '2020-05-04 03:52:02.470000', 'tkd@gmail.com', b'0', 'Tanmoy Das', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', NULL, 'BASIC_USER', 'tbYquZyjly2S3PkygJKY', 'Funny Coder'),
(2, '2020-05-03 23:44:20.181000', NULL, b'0', '2020-05-03 23:44:20.181000', 'tanmoykrishnadas@gmail.com', b'0', 'Tanmoy Krishna Das', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', NULL, 'BASIC_USER', 'W02ublz0R8g1M9UAESqo', NULL);

--
-- Dumping data for table `user_connections`
--

INSERT INTO `user_connections` (`id`, `created_on`, `deleted_on`, `is_deleted`, `updated_on`) VALUES
(5, '2020-05-04 12:40:36.161000', '2020-05-04 12:40:55.511000', b'1', '2020-05-04 12:40:55.512000');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
