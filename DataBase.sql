-- phpMyAdmin SQL Dump
-- version 3.5.8.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 25, 2017 at 12:00 PM
-- Server version: 5.1.73
-- PHP Version: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `EPL362project`
--

-- --------------------------------------------------------

--
-- Table structure for table `ClinicalStaff`
--

CREATE TABLE IF NOT EXISTS `ClinicalStaff` (
  `ClinicalStaffID` varchar(50) COLLATE utf8_bin NOT NULL,
  `Password` varchar(8) COLLATE utf8_bin NOT NULL DEFAULT 'Password',
  `StaffType` enum('Doctor','Nurse','Health Visitor') COLLATE utf8_bin NOT NULL DEFAULT 'Nurse',
  `Name` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Name',
  `Surname` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Surname',
  `Phone` int(11) NOT NULL DEFAULT '0',
  `Email` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Email',
  `Address` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Address',
  PRIMARY KEY (`ClinicalStaffID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `Consultation`
--

CREATE TABLE IF NOT EXISTS `Consultation` (
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `ClinicalStaffID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `Subject` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Subject',
  `Date` date NOT NULL,
  `Attended` tinyint(1) NOT NULL DEFAULT '0',
  `MedicalRecordUpdated` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- RELATIONS FOR TABLE `Consultation`:
--   `ClinicalStaffID`
--       `ClinicalStaff` -> `ClinicalStaffID`
--   `PatientID`
--       `Patient` -> `PatientID`
--

-- --------------------------------------------------------

--
-- Table structure for table `Incident`
--

CREATE TABLE IF NOT EXISTS `Incident` (
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `IncidentType` enum('Accidental Treatment Incident','Deliberate Incident','Threat') COLLATE utf8_bin NOT NULL DEFAULT 'Accidental Treatment Incident',
  `ShortDescription` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Short Description',
  `Description` varchar(500) COLLATE utf8_bin NOT NULL DEFAULT 'Description',
  `Date` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- RELATIONS FOR TABLE `Incident`:
--   `PatientID`
--       `Patient` -> `PatientID`
--

-- --------------------------------------------------------

--
-- Table structure for table `InformRelatives`
--

CREATE TABLE IF NOT EXISTS `InformRelatives` (
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `Subject` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Subject',
  `Message` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT 'Message',
  `Informed` tinyint(1) NOT NULL DEFAULT '0',
  `ClinicalStaffID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- RELATIONS FOR TABLE `InformRelatives`:
--   `ClinicalStaffID`
--       `ClinicalStaff` -> `ClinicalStaffID`
--   `PatientID`
--       `Patient` -> `PatientID`
--

-- --------------------------------------------------------

--
-- Table structure for table `Medication`
--

CREATE TABLE IF NOT EXISTS `Medication` (
  `MedicationID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT 'Medication Name',
  `Description` varchar(300) COLLATE utf8_bin NOT NULL DEFAULT 'Medication Description',
  PRIMARY KEY (`MedicationID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `MedicationReaction`
--

CREATE TABLE IF NOT EXISTS `MedicationReaction` (
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `MedicationID` int(11) NOT NULL COMMENT 'Foreign Key',
  `ReactionType` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Allergy',
  `Description` varchar(300) COLLATE utf8_bin NOT NULL DEFAULT 'Medical Reaction Description'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- RELATIONS FOR TABLE `MedicationReaction`:
--   `MedicationID`
--       `Medication` -> `MedicationID`
--   `PatientID`
--       `Patient` -> `PatientID`
--

-- --------------------------------------------------------

--
-- Table structure for table `Patient`
--

CREATE TABLE IF NOT EXISTS `Patient` (
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL,
  `Password` varchar(8) COLLATE utf8_bin NOT NULL DEFAULT 'Password',
  `Name` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Name',
  `Surname` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Surname',
  `Phone` int(11) NOT NULL DEFAULT '0',
  `Email` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Email',
  `Address` varchar(120) COLLATE utf8_bin DEFAULT 'Address',
  `NumOfIncidents` int(11) NOT NULL DEFAULT '0',
  `SelfHarmPossibility` tinyint(1) NOT NULL DEFAULT '0',
  `ChangedByPatient` tinyint(1) NOT NULL DEFAULT '0',
  `DeadReadOnly` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`PatientID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `Relative`
--

CREATE TABLE IF NOT EXISTS `Relative` (
  `RelativeID` int(11) NOT NULL AUTO_INCREMENT,
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `Name` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Name',
  `Surname` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Surname',
  `Phone` int(11) NOT NULL DEFAULT '0',
  `Email` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Email',
  `Address` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Address',
  `Relationship` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Relationship',
  PRIMARY KEY (`RelativeID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- RELATIONS FOR TABLE `Relative`:
--   `PatientID`
--       `Patient` -> `PatientID`
--

-- --------------------------------------------------------

--
-- Table structure for table `SimpleStaff`
--

CREATE TABLE IF NOT EXISTS `SimpleStaff` (
  `SimpleStaffID` varchar(50) COLLATE utf8_bin NOT NULL,
  `Password` varchar(8) COLLATE utf8_bin NOT NULL DEFAULT 'Password',
  `StaffType` enum('Receptionist','Medical Records Staff') COLLATE utf8_bin NOT NULL,
  `Name` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Name',
  `Surname` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Surname',
  `Phone` int(11) NOT NULL DEFAULT '0',
  `Email` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Email',
  `Address` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Address',
  PRIMARY KEY (`SimpleStaffID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `Treatment`
--

CREATE TABLE IF NOT EXISTS `Treatment` (
  `TreatmentID` int(11) NOT NULL AUTO_INCREMENT,
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `StartDate` date NOT NULL,
  `EndDate` date DEFAULT NULL,
  `Description` varchar(300) COLLATE utf8_bin NOT NULL DEFAULT 'Treatment Description',
  `ClinicalStaffID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  PRIMARY KEY (`TreatmentID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- RELATIONS FOR TABLE `Treatment`:
--   `ClinicalStaffID`
--       `ClinicalStaff` -> `ClinicalStaffID`
--   `PatientID`
--       `Patient` -> `PatientID`
--

-- --------------------------------------------------------

--
-- Table structure for table `TreatmentMedication`
--

CREATE TABLE IF NOT EXISTS `TreatmentMedication` (
  `TreatmentID` int(11) NOT NULL COMMENT 'Foreign Key',
  `MedicationID` int(11) NOT NULL COMMENT 'Foreign Key',
  `Dose` int(11) NOT NULL DEFAULT '0',
  `DoseDescription` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Dose Description',
  `OverruledWarning` tinyint(1) NOT NULL DEFAULT '0',
  `WarningMessage` varchar(150) COLLATE utf8_bin NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- RELATIONS FOR TABLE `TreatmentMedication`:
--   `MedicationID`
--       `Medication` -> `MedicationID`
--   `TreatmentID`
--       `Treatment` -> `TreatmentID`
--

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
