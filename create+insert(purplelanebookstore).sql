-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 28, 2023 at 02:48 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `purplelanebookstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `BookID` int(11) NOT NULL,
  `BookName` varchar(70) NOT NULL,
  `BookStock` int(11) NOT NULL,
  `BookAuthor` varchar(70) NOT NULL,
  `BookPrice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`BookID`, `BookName`, `BookStock`, `BookAuthor`, `BookPrice`) VALUES
(1, 'In Search of Lost Time', 50, 'Marcel Proust', 25000),
(2, 'Ulysses', 40, 'James Joyce', 30000),
(3, 'Don Quixote', 45, 'Miguel de Cervantes', 28500),
(4, 'The Great Gatsby', 45, 'F. Scott Fitzgerald', 21500),
(5, 'War and Peace', 40, 'Leo Tolstoy', 20500),
(6, 'Hamlet', 36, 'William Shakespeare', 22500),
(7, 'The Odyssey', 31, 'Homer', 29500),
(8, 'Madame Bovary', 33, 'Gustave Flaubert', 28500),
(9, 'The Divine Comedy', 48, 'Dante Alighieri', 25600),
(10, 'Lolita', 27, 'Vladimir Nabokov', 30100),
(11, 'The Brothers Karamazov', 39, 'Fyodor Dostoyevsky', 31200),
(12, 'Crime and Punishment', 33, 'Fyodor Dostoyevsky', 25340),
(13, 'Wuhtering Weights', 37, 'Emily Bronte', 29850),
(14, 'Pride and Prejudice', 41, 'Jane Austen', 31900),
(15, 'The Iliad', 41, 'Homer', 22500),
(16, 'To The Lighthouse', 35, 'Virginia Woolf', 24500),
(17, 'Catch-22', 40, 'Joseph Heller', 23500),
(18, 'Heart of Darkness', 39, 'Joseph Conrad', 22500),
(19, 'The Sound and the Fury', 50, 'William Faulkner', 29000),
(20, 'Invisible Man', 34, 'Ralph Ellison', 23500);

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `CartID` int(11) NOT NULL,
  `BookID` int(11) NOT NULL,
  `BookName` varchar(70) NOT NULL,
  `BookQty` int(11) NOT NULL,
  `BookAuthor` varchar(70) NOT NULL,
  `BookPrice` int(11) NOT NULL,
  `SubTotal` int(11) NOT NULL,
  `user_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `promo`
--

CREATE TABLE `promo` (
  `PromoID` int(11) NOT NULL,
  `PromoCode` varchar(30) NOT NULL,
  `PromoDiscount` int(11) NOT NULL,
  `PromoNotes` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `promo`
--

INSERT INTO `promo` (`PromoID`, `PromoCode`, `PromoDiscount`, `PromoNotes`) VALUES
(1, 'PU125', 20000, 'Welcome Time'),
(2, 'PU133', 25000, 'Annual Promo'),
(3, 'PU153', 30000, 'Summer Promo'),
(4, 'PU192', 45000, 'Special Promo'),
(5, 'PU201', 50000, 'Big Time');

-- --------------------------------------------------------

--
-- Table structure for table `trdetail`
--

CREATE TABLE `trdetail` (
  `thID` int(11) NOT NULL,
  `bookID` int(11) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `trheader`
--

CREATE TABLE `trheader` (
  `thID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `trDate` datetime NOT NULL,
  `totalItem` int(11) NOT NULL,
  `PaymentType` varchar(50) NOT NULL,
  `CardNumber` varchar(50) NOT NULL,
  `PromoCode` int(11) NOT NULL,
  `GrandTotal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role` varchar(15) DEFAULT 'customer'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `name`, `email`, `username`, `password`, `role`) VALUES
(1, 'Wilbert', 'wilbert@gmail.com', 'wilbert123', 'wilbert123', 'customer'),
(2, 'Admin', 'admin@gmail.com', 'admin', 'admin123', 'admin'),
(3, 'Manager', 'manager@gmail.com', 'manager', 'manager123', 'manager'),
(4, 'Promotion', 'promotion@gmail.com', 'promotion', 'promotion123', 'promotion'),
(15, 'Aandi', 'andi@gmail.com', 'andi123', 'Andi123', 'customer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`BookID`);

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`CartID`),
  ADD KEY `user_ID` (`user_ID`);

--
-- Indexes for table `promo`
--
ALTER TABLE `promo`
  ADD PRIMARY KEY (`PromoID`);

--
-- Indexes for table `trdetail`
--
ALTER TABLE `trdetail`
  ADD PRIMARY KEY (`thID`,`bookID`),
  ADD KEY `bookID` (`bookID`);

--
-- Indexes for table `trheader`
--
ALTER TABLE `trheader`
  ADD PRIMARY KEY (`thID`),
  ADD KEY `userID` (`userID`),
  ADD KEY `PromoCode` (`PromoCode`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `BookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `carts`
--
ALTER TABLE `carts`
  MODIFY `CartID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `promo`
--
ALTER TABLE `promo`
  MODIFY `PromoID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `trheader`
--
ALTER TABLE `trheader`
  MODIFY `thID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`user_ID`) REFERENCES `users` (`ID`);

--
-- Constraints for table `trdetail`
--
ALTER TABLE `trdetail`
  ADD CONSTRAINT `trdetail_ibfk_1` FOREIGN KEY (`thID`) REFERENCES `trheader` (`thID`),
  ADD CONSTRAINT `trdetail_ibfk_2` FOREIGN KEY (`bookID`) REFERENCES `book` (`BookID`);

--
-- Constraints for table `trheader`
--
ALTER TABLE `trheader`
  ADD CONSTRAINT `trheader_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`ID`),
  ADD CONSTRAINT `trheader_ibfk_2` FOREIGN KEY (`PromoCode`) REFERENCES `promo` (`PromoID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
