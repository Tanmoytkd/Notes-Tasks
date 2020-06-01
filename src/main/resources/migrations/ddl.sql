-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 05, 2020 at 05:42 PM
-- Server version: 5.7.30-0ubuntu0.18.04.1
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

-- --------------------------------------------------------

--
-- Table structure for table `connections`
--

CREATE TABLE `connections` (
  `user_connection_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `connection_requests`
--

CREATE TABLE `connection_requests` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `receiver_id` bigint(20) NOT NULL,
  `sender_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `text` text,
  `is_seen` bit(1) NOT NULL,
  `receiver_id` bigint(20) NOT NULL,
  `sender_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE `notes` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `text` text,
  `privacy` varchar(255) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `writer_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `note_accesses`
--

CREATE TABLE `note_accesses` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `note_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `note_access_levels`
--

CREATE TABLE `note_access_levels` (
  `note_access_id` bigint(20) NOT NULL,
  `access_level` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `note_comments`
--

CREATE TABLE `note_comments` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `writer_id` bigint(20) NOT NULL,
  `note_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `report_status` varchar(255) NOT NULL,
  `sender_id` bigint(20) NOT NULL,
  `target_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `report_comments`
--

CREATE TABLE `report_comments` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `writer_id` bigint(20) NOT NULL,
  `report_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `description` text,
  `title` text,
  `creator_id` bigint(20) NOT NULL,
  `is_complete` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `task_assignments`
--

CREATE TABLE `task_assignments` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `is_completed` bit(1) NOT NULL,
  `task_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `task_comments`
--

CREATE TABLE `task_comments` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `writer_id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `is_email_verified` bit(1) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `secret` varchar(255) NOT NULL,
  `about` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_connections`
--

CREATE TABLE `user_connections` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `deleted_on` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `connections`
--
ALTER TABLE `connections`
  ADD KEY `FKltpo1ymtaafd67hx5tls1db6u` (`user_id`),
  ADD KEY `FKaloxhe1lsa7nwkklqv6mw2lwn` (`user_connection_id`);

--
-- Indexes for table `connection_requests`
--
ALTER TABLE `connection_requests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrbqpgx91y8jhk8glwkv33mqxu` (`receiver_id`),
  ADD KEY `FKj291paj3vuf40hnbggyqc4fr5` (`sender_id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt05r0b6n0iis8u7dfna4xdh73` (`receiver_id`),
  ADD KEY `FK4ui4nnwntodh6wjvck53dbk9m` (`sender_id`);

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg0kf2fp9i5gujmwyq4v4eqpfd` (`writer_id`);

--
-- Indexes for table `note_accesses`
--
ALTER TABLE `note_accesses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5juav69i4mdetv37m6rh3lkdt` (`note_id`),
  ADD KEY `FKenbyldl8dq72q9updepib0xpv` (`user_id`);

--
-- Indexes for table `note_access_levels`
--
ALTER TABLE `note_access_levels`
  ADD KEY `FKo1vo9n2wu3n80epiyg55djuca` (`note_access_id`);

--
-- Indexes for table `note_comments`
--
ALTER TABLE `note_comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqknvd8yqr5fcwedhkyyo23qnm` (`writer_id`),
  ADD KEY `FKh1np85i44rw1bia3rml11a80r` (`note_id`);

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKaf5vwr2r9722vw8buhtjmf3do` (`sender_id`),
  ADD KEY `FKl4vla93dheuj69dxjs0kh3bjb` (`target_id`);

--
-- Indexes for table `report_comments`
--
ALTER TABLE `report_comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKhal0gh3qger4k0we5mqs0t05c` (`writer_id`),
  ADD KEY `FK1i80qi03utu4wxo5vjvs4n4y6` (`report_id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt1ph5sat39g9lpa4g5kl46tbv` (`creator_id`);

--
-- Indexes for table `task_assignments`
--
ALTER TABLE `task_assignments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKk36vhf9tt6t3woselwnkis6v6` (`task_id`),
  ADD KEY `FKovnod7lqp56uups16si7jh6uu` (`user_id`);

--
-- Indexes for table `task_comments`
--
ALTER TABLE `task_comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6eshlsdv6f1padgrx0a1xjvgp` (`writer_id`),
  ADD KEY `FK9517viwn2geh1gpivj6l9y64u` (`task_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indexes for table `user_connections`
--
ALTER TABLE `user_connections`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `connection_requests`
--
ALTER TABLE `connection_requests`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notes`
--
ALTER TABLE `notes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `note_accesses`
--
ALTER TABLE `note_accesses`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `note_comments`
--
ALTER TABLE `note_comments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `report_comments`
--
ALTER TABLE `report_comments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `task_assignments`
--
ALTER TABLE `task_assignments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `task_comments`
--
ALTER TABLE `task_comments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_connections`
--
ALTER TABLE `user_connections`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `connections`
--
ALTER TABLE `connections`
  ADD CONSTRAINT `FKaloxhe1lsa7nwkklqv6mw2lwn` FOREIGN KEY (`user_connection_id`) REFERENCES `user_connections` (`id`),
  ADD CONSTRAINT `FKltpo1ymtaafd67hx5tls1db6u` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `connection_requests`
--
ALTER TABLE `connection_requests`
  ADD CONSTRAINT `FKj291paj3vuf40hnbggyqc4fr5` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKrbqpgx91y8jhk8glwkv33mqxu` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `FK4ui4nnwntodh6wjvck53dbk9m` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKt05r0b6n0iis8u7dfna4xdh73` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `FKg0kf2fp9i5gujmwyq4v4eqpfd` FOREIGN KEY (`writer_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `note_accesses`
--
ALTER TABLE `note_accesses`
  ADD CONSTRAINT `FK5juav69i4mdetv37m6rh3lkdt` FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`),
  ADD CONSTRAINT `FKenbyldl8dq72q9updepib0xpv` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `note_access_levels`
--
ALTER TABLE `note_access_levels`
  ADD CONSTRAINT `FKo1vo9n2wu3n80epiyg55djuca` FOREIGN KEY (`note_access_id`) REFERENCES `note_accesses` (`id`);

--
-- Constraints for table `note_comments`
--
ALTER TABLE `note_comments`
  ADD CONSTRAINT `FKh1np85i44rw1bia3rml11a80r` FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`),
  ADD CONSTRAINT `FKqknvd8yqr5fcwedhkyyo23qnm` FOREIGN KEY (`writer_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `FKaf5vwr2r9722vw8buhtjmf3do` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKl4vla93dheuj69dxjs0kh3bjb` FOREIGN KEY (`target_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `report_comments`
--
ALTER TABLE `report_comments`
  ADD CONSTRAINT `FK1i80qi03utu4wxo5vjvs4n4y6` FOREIGN KEY (`report_id`) REFERENCES `reports` (`id`),
  ADD CONSTRAINT `FKhal0gh3qger4k0we5mqs0t05c` FOREIGN KEY (`writer_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `FKt1ph5sat39g9lpa4g5kl46tbv` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `task_assignments`
--
ALTER TABLE `task_assignments`
  ADD CONSTRAINT `FKk36vhf9tt6t3woselwnkis6v6` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  ADD CONSTRAINT `FKovnod7lqp56uups16si7jh6uu` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `task_comments`
--
ALTER TABLE `task_comments`
  ADD CONSTRAINT `FK6eshlsdv6f1padgrx0a1xjvgp` FOREIGN KEY (`writer_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK9517viwn2geh1gpivj6l9y64u` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
