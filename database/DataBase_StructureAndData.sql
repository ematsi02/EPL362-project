-- phpMyAdmin SQL Dump
-- version 3.5.8.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 10, 2017 at 11:41 AM
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
-- Table structure for table `Comments`
--

CREATE TABLE IF NOT EXISTS `Comments` (
  `CommentID` int(11) NOT NULL AUTO_INCREMENT,
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `StaffID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `Subject` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT 'Comment Subject',
  `Comment` varchar(300) COLLATE utf8_bin NOT NULL DEFAULT 'Comment, Notes',
  PRIMARY KEY (`CommentID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=7 ;

--
-- RELATIONS FOR TABLE `Comments`:
--   `PatientID`
--       `Patient` -> `PatientID`
--   `StaffID`
--       `Staff` -> `StaffID`
--

--
-- Dumping data for table `Comments`
--

INSERT INTO `Comments` (`CommentID`, `PatientID`, `StaffID`, `Subject`, `Comment`) VALUES
(2, 'amatsi03', 'sg', 'sub', 'comment'),
(3, 'un', 'sg', 'fbrfb', 'fbfrbfrb'),
(6, 'un', 'sg', 'fbfdb', 'fbdbbbbbbbbbbbbbbbbb'),
(5, 'un', 'sg', 'rbrtb', 'btgrb');

-- --------------------------------------------------------

--
-- Table structure for table `Consultation`
--

CREATE TABLE IF NOT EXISTS `Consultation` (
  `ConsultationID` int(11) NOT NULL AUTO_INCREMENT,
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `StaffID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `Subject` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Subject',
  `DateBooked` date NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `Attended` tinyint(1) NOT NULL DEFAULT '0',
  `MedicalRecordUpdated` tinyint(1) NOT NULL DEFAULT '0',
  `TreatmentID` int(11) NOT NULL COMMENT 'Foreign Key',
  PRIMARY KEY (`ConsultationID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=14 ;

--
-- RELATIONS FOR TABLE `Consultation`:
--   `PatientID`
--       `Patient` -> `PatientID`
--   `StaffID`
--       `Staff` -> `StaffID`
--   `TreatmentID`
--       `Treatment` -> `TreatmentID`
--

--
-- Dumping data for table `Consultation`
--

INSERT INTO `Consultation` (`ConsultationID`, `PatientID`, `StaffID`, `Subject`, `DateBooked`, `Date`, `Time`, `Attended`, `MedicalRecordUpdated`, `TreatmentID`) VALUES
(2, 'Patient', 'Doctor', 'Meeting about self harm risk of this patient', '2017-05-04', '2017-05-05', '14:12:39', 1, 1, 2),
(3, 'Patient', 'erasmia', 'Second appointment, Subject: Depression', '2017-05-07', '2017-05-14', '10:00:00', 1, 0, 0),
(4, 'Suicide', 'Doctor', 'First Consultation Appointment', '2017-02-06', '2017-02-13', '10:00:00', 1, 1, 0),
(5, 'Suicide', 'Doctor', 'Second Consultation Appointment', '2017-02-13', '2017-05-14', '11:00:00', 1, 0, 0),
(6, 'Suicide', 'erasmia', 'Second Consultation Appointment', '2017-05-07', '2017-02-23', '09:00:00', 1, 0, 0),
(7, 'amatsi03', 'sg', 'check', '2017-05-08', '2017-05-19', '09:30:52', 0, 1, 2),
(9, 'un', 'sg', 'rdgvrgv', '2017-05-18', '2017-05-19', '12:13:00', 0, 1, 2),
(11, 'un', 'sg', 'ebfrg', '2017-05-13', '2017-05-20', '12:30:00', 0, 0, 2),
(12, 'un', 'sg', 'regrb', '2017-05-20', '2017-05-27', '12:34:00', 0, 0, 4),
(13, 'un', 'sg', 'esfeg', '2017-05-04', '2017-05-27', '12:34:00', 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Incident`
--

CREATE TABLE IF NOT EXISTS `Incident` (
  `IncidentID` int(11) NOT NULL AUTO_INCREMENT,
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `IncidentType` enum('Accidental Treatment Incident','Deliberate Incident','Threat') COLLATE utf8_bin NOT NULL DEFAULT 'Accidental Treatment Incident',
  `ShortDescription` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Short Description',
  `Description` varchar(500) COLLATE utf8_bin NOT NULL DEFAULT 'Description',
  `Date` date NOT NULL,
  PRIMARY KEY (`IncidentID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=12 ;

--
-- RELATIONS FOR TABLE `Incident`:
--   `PatientID`
--       `Patient` -> `PatientID`
--

--
-- Dumping data for table `Incident`
--

INSERT INTO `Incident` (`IncidentID`, `PatientID`, `IncidentType`, `ShortDescription`, `Description`, `Date`) VALUES
(3, 'a2', 'Deliberate Incident', 'Tried to Suicide', 'This patient tried to kill himself by jumping from the first floor of the building where he lives', '2017-05-03'),
(7, 'un', 'Accidental Treatment Incident', 'dscsd', 'dsvsd', '2017-05-20'),
(8, 'un', 'Accidental Treatment Incident', 'fdvdf', 'fvfd', '2017-05-12'),
(5, 'Dead', 'Threat', 'Suicide Threat', 'He threatened to suicide', '2017-04-18'),
(6, 'Dead', 'Deliberate Incident', 'Suicide', 'He killed himself ', '2017-04-27'),
(9, 'BadPatient', 'Accidental Treatment Incident', 'dfvfd', 'fbf', '2017-05-13'),
(10, 'un', 'Accidental Treatment Incident', 'dgdfd', 'rgvrgr', '2017-05-12'),
(11, 'un', 'Threat', 'rfgrgreg', 'rgrg', '2017-05-05');

-- --------------------------------------------------------

--
-- Table structure for table `InformRelatives`
--

CREATE TABLE IF NOT EXISTS `InformRelatives` (
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `Subject` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Subject',
  `Message` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT 'Message',
  `Informed` tinyint(1) NOT NULL DEFAULT '0',
  `StaffID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- RELATIONS FOR TABLE `InformRelatives`:
--   `PatientID`
--       `Patient` -> `PatientID`
--   `StaffID`
--       `Staff` -> `StaffID`
--

--
-- Dumping data for table `InformRelatives`
--

INSERT INTO `InformRelatives` (`PatientID`, `Subject`, `Message`, `Informed`, `StaffID`) VALUES
('BadPatient', 'Warning about a very serious matter', 'This matter is a very serious matter', 1, 'erasmia'),
('amatsi03', 'Warning about this matter', 'This matter is a very serious matter', 1, 'sg'),
('BadPatient', 'Subject', 'Message', 1, 'Doctor'),
('un', 'Subject about un', 'Message about un warning', 1, 'Doctor'),
('un', 'rdfgreg', 'rgregggggggggggggggg', 1, 'sg');

-- --------------------------------------------------------

--
-- Table structure for table `Medication`
--

CREATE TABLE IF NOT EXISTS `Medication` (
  `MedicationID` int(11) NOT NULL AUTO_INCREMENT,
  `Brand` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT 'Brand Name',
  `Name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT 'Medication 1',
  `Description` varchar(300) COLLATE utf8_bin NOT NULL DEFAULT 'Medication Description',
  `KnownSideEffects` varchar(150) COLLATE utf8_bin NOT NULL,
  `MaxDose` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MedicationID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=6 ;

--
-- Dumping data for table `Medication`
--

INSERT INTO `Medication` (`MedicationID`, `Brand`, `Name`, `Description`, `KnownSideEffects`, `MaxDose`) VALUES
(2, 'BlueHealth', 'Medication 1', 'Medication about depression', 'Dizziness and other...', 2),
(3, 'NUROFEN', 'Nurofen Cold and Flu', 'gia ti gripi', 'anoigei ti miti', 2),
(4, 'Panadol', 'Panadol', 'Panadol', 'anakoufizei apo ton pono', 2),
(5, 'dvdxvd', 'vdvdv', 'dvdvdvvvvvvvvvvvvvvvvvvvvvvvvvvv', 'htgg', 6);

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

--
-- Dumping data for table `MedicationReaction`
--

INSERT INTO `MedicationReaction` (`PatientID`, `MedicationID`, `ReactionType`, `Description`) VALUES
('Patient', 2, 'Skin allergy', 'Medical Reaction for this patient is skin allergy'),
('un', 2, 'dvlkvk', 'dfbdb'),
('un', 2, 'fbf', 'bfb');

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
  `SelfHarmRisk` tinyint(1) NOT NULL DEFAULT '0',
  `OthersHarmRisk` tinyint(1) NOT NULL DEFAULT '0',
  `RiskStatus` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT 'Risk Status Description and Notes',
  `ChangedByPatient` tinyint(1) NOT NULL DEFAULT '0',
  `DeadReadOnly` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`PatientID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `Patient`
--

INSERT INTO `Patient` (`PatientID`, `Password`, `Name`, `Surname`, `Phone`, `Email`, `Address`, `NumOfIncidents`, `SelfHarmRisk`, `OthersHarmRisk`, `RiskStatus`, `ChangedByPatient`, `DeadReadOnly`) VALUES
('un', '123456', 'un', 'un', 12345678, 'un@hotmail.com', 'ldkvfdk', 2, 1, 1, 'risk status', 0, 1),
('a2', '123456', 'asthenis2', 'gkjrfglkj', 12345678, 'a2@cs.com', 'psixiatreio', 0, 0, 0, 'Risk Status Description and Notes', 0, 0),
('s', '1', 'sotia', 'gregoriou', 123, 'ftrt', 'rthrt', 0, 0, 0, 'Risk Status Description and Notes', 0, 0),
('rgrg', 'rgreg', 'erg', 'rgreg', 12132, 'regerge', 'ergreg', 0, 0, 0, 'Risk Status Description and Notes', 0, 0),
('Patient', '123456', 'Patient', 'Harmful', 99558844, 'Email@bla.com', 'Patient''s Address', 0, 1, 1, 'This patient has risk of self-harm and risk of others harm.', 0, 0),
('amatsi03', '123456', 'Antriana', 'Matsi', 99251098, 'antriana3011@gmail.com', 'Palaichori', 0, 0, 0, 'Risk Status Description and Notes', 1, 0),
('BadPatient', '123456', 'BadPatient', 'NotAttending', 1234456, 'email@email.com', 'My address', 2, 0, 0, 'Risk status', 0, 0),
('Suicide', '123456', 'Suicide', 'Patient', 1235464, 'Email@c.com', 'my address', 2, 1, 0, 'High risk of self harm. See incidents', 0, 0),
('Dead', '123456', 'Dead', 'Patient', 1235464, 'Email@c.com', 'my address', 2, 1, 0, 'High risk of self harm. See incidents', 0, 1);

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=9 ;

--
-- RELATIONS FOR TABLE `Relative`:
--   `PatientID`
--       `Patient` -> `PatientID`
--

--
-- Dumping data for table `Relative`
--

INSERT INTO `Relative` (`RelativeID`, `PatientID`, `Name`, `Surname`, `Phone`, `Email`, `Address`, `Relationship`) VALUES
(2, 'amatsi03', 'Yiota', 'Matsi', 99886742, 'yioma68@gmail.com', 'palaichori', 'Mum'),
(3, 'Dead', 'rel', 'lala', 0, 'Email@email.com', 'Address', 'Brother'),
(4, 'Dead', 'rel2', 'lala', 0, 'Email2@email.com', 'Address', 'Sister'),
(6, 'Patient', 'Relativename', 'Surname', 0, 'Email', 'Address', 'Daughter'),
(7, 'a2', 'Giorgos', 'Gewrgiou', 99009900, 'gg@cs.com', 'lefkosia', 'Son'),
(8, 'a1', 'Maria', 'Kyriakou', 99887766, 'mk@cs.com', 'lakatameia', 'Mother');

-- --------------------------------------------------------

--
-- Table structure for table `RenewTreatment`
--

CREATE TABLE IF NOT EXISTS `RenewTreatment` (
  `TreatmentID` int(11) NOT NULL COMMENT 'Foreign Key',
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL,
  `Notes` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT 'Notes',
  `StaffID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- RELATIONS FOR TABLE `RenewTreatment`:
--   `PatientID`
--       `Patient` -> `PatientID`
--   `StaffID`
--       `Staff` -> `StaffID`
--   `TreatmentID`
--       `Treatment` -> `TreatmentID`
--

--
-- Dumping data for table `RenewTreatment`
--

INSERT INTO `RenewTreatment` (`TreatmentID`, `PatientID`, `StartDate`, `EndDate`, `Notes`, `StaffID`) VALUES
(2, 'Patient', '2017-05-14', '2017-05-20', 'Renewed treatment', 'Reception'),
(3, 'Suicide', '2017-02-17', '2017-02-23', 'Renew prescription for Sxizofreneiaaaaa', 'erasmia'),
(1, 'un', '2017-05-04', '2017-05-11', 'fbfgbfgnfgn g', 'sg');

-- --------------------------------------------------------

--
-- Table structure for table `Staff`
--

CREATE TABLE IF NOT EXISTS `Staff` (
  `StaffID` varchar(50) COLLATE utf8_bin NOT NULL,
  `Password` varchar(8) COLLATE utf8_bin NOT NULL DEFAULT 'Password',
  `StaffType` enum('Receptionist','Medical Records','Management','Doctor','Nurse','Health Visitor') COLLATE utf8_bin NOT NULL DEFAULT 'Receptionist',
  `Name` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Name',
  `Surname` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'Surname',
  `Phone` int(11) NOT NULL DEFAULT '0',
  `Email` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Email',
  `Address` varchar(120) COLLATE utf8_bin NOT NULL DEFAULT 'Address',
  PRIMARY KEY (`StaffID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `Staff`
--

INSERT INTO `Staff` (`StaffID`, `Password`, `StaffType`, `Name`, `Surname`, `Phone`, `Email`, `Address`) VALUES
('r', '1', 'Receptionist', 'lala', 'lala', 12345678, 'fghfgh@rfgmf.com', 'gdgdfb'),
('sotia', '123456', 'Medical Records', 'lfbdkm', 'lkfbmfdkbm', 123456, 'dgfghlfk@lhkl.com', 'fhlgk'),
('sg', '1', 'Doctor', 's', 'g', 1268, 'sg@hotmail.com', 'dfvfdbgf'),
('hihi', '654321', 'Health Visitor', 'hihi', 'hihi', 12345678, 'elkmdg@fkbhm.com', 'fldm'),
('gr', '654321', 'Nurse', 'rfgnk', 'sdvf', 123456, 'grd@fbdfg.com', 'dsfg'),
('erasmia', 'erasmia', 'Doctor', 'erasmia', 'erasmia', 2343090, 'werti@erg.com', 'sdg sfg '),
('ematsi', '1234', 'Nurse', 'Elena', 'Matsi', 99999999, 'elena@cs.com', 'oeiufcbds'),
('para', '123456', 'Receptionist', 'Paraskevi', 'Paraskeva', 99, 'para@cs.com', 'ijf'),
('mioan', '123456', 'Health Visitor', 'maria', 'ioannou', 22010203, 'mian@cs.com', 'dksjf'),
('kostas', '123456', 'Doctor', 'kostas', 'kosta', 9999, 'kufds@duyuw', 'fdsg'),
('sgrego02', '123456', 'Management', 'sotia', 'gregoriou', 12345678, 'sg@cs.com', 'sdgdfg'),
('mr', '1', 'Medical Records', 'medical', 'records', 2359586, 'mr@mr.com', 'this is my address'),
('manager', '123456', 'Management', 'Manager', 'Manager', 1354666, 'Email@la.com', 'Address'),
('Reception', '123456', 'Receptionist', 'Reception', 'Reception', 46436, 'Email', 'Address'),
('Health', '123456', 'Health Visitor', 'Health', 'Visitor', 0, 'Email', 'Address'),
('Nurse', '123456', 'Nurse', 'Name', 'Surname', 0, 'Email', 'Address'),
('Doctor', '123456', 'Doctor', 'Doc', 'Surname', 0, 'Email', 'Address'),
('s', 's', 'Doctor', 's', 's', 1, 's', 's');

-- --------------------------------------------------------

--
-- Table structure for table `Treatment`
--

CREATE TABLE IF NOT EXISTS `Treatment` (
  `TreatmentID` int(11) NOT NULL AUTO_INCREMENT,
  `PatientID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  `StartDate` date NOT NULL,
  `EndDate` date DEFAULT NULL,
  `Diagnosis` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT 'Depression',
  `Description` varchar(300) COLLATE utf8_bin NOT NULL DEFAULT 'Treatment Description',
  `StaffID` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Foreign Key',
  PRIMARY KEY (`TreatmentID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=34 ;

--
-- RELATIONS FOR TABLE `Treatment`:
--   `PatientID`
--       `Patient` -> `PatientID`
--   `StaffID`
--       `Staff` -> `StaffID`
--

--
-- Dumping data for table `Treatment`
--

INSERT INTO `Treatment` (`TreatmentID`, `PatientID`, `StartDate`, `EndDate`, `Diagnosis`, `Description`, `StaffID`) VALUES
(1, 'un', '2017-05-04', '2017-05-12', 'diagnosis', 'Description', 'sg'),
(2, 'Patient', '2017-05-06', '2017-05-13', 'Depression', 'This patient has Depression', 'Doctor'),
(3, 'Suicide', '2017-02-13', '2017-02-17', 'Schizophrenia', 'Sxizofreneiaaaaa', 'Doctor'),
(4, 'amatsi03', '2017-05-10', '2017-05-25', 'pellara', 'i kopella den ine kala', 'ematsi'),
(5, 'un', '2017-05-13', '2017-05-26', 'Depression', 'fbfb', 'sg'),
(6, 'un', '2017-05-19', '2017-05-27', 'rthrt', 'thtrh', 'sg'),
(7, 'un', '2017-05-17', '2017-05-26', 'rtrth', 'trhrt', 'sg'),
(8, 'un', '2017-05-12', '2017-05-27', 'sfdg', 'drgrgr', 'sg'),
(9, 'un', '2017-05-11', '2017-05-19', 'rthbrth', 'trhrth', 'sg'),
(10, 'un', '2017-05-12', '2017-05-20', 'reger', 'gergr', 'sg'),
(11, 'un', '2017-05-13', '2017-05-20', 'rg', 'grfgr', 'sg'),
(12, 'un', '2017-05-19', '2017-05-27', 'rfgrg', 'rgrgr', 'sg'),
(13, 'un', '2017-05-13', '2017-05-20', 'dfgfg', 'rgrgr', 'sg'),
(14, 'un', '2017-05-13', '2017-05-27', 'fbf', 'gfgfg', 'sg'),
(15, 'un', '2017-05-05', '2017-05-27', 'frgrg', 'rfgfrg', 'sg'),
(16, 'un', '2017-05-05', '2017-05-20', 'ssfdf', 'dfdfd', 'sg'),
(17, 'un', '2017-05-12', '2017-05-20', 'fdgdf', 'gfdgd', 'sg'),
(18, 'un', '2017-05-11', '2017-05-24', 'sfs', 'sdfd', 's'),
(19, 'un', '2017-05-05', '2017-05-13', 'sergfd', 'dffb', 'sg'),
(20, 'dfvfdv', '2017-05-06', '2017-05-12', 'dfvfdv', 'dfvdf', 'sg'),
(21, 'BadPatient', '2017-05-13', '2017-05-20', 'Depression', 'dfvdrrrrrrrrrrgggggggggggggggggggggggggg', 'sg'),
(22, 'un', '2017-05-12', '2017-05-27', 'dfvdfv', 'dfvdfvfd', 'sg'),
(23, 'un', '2017-05-19', '2017-05-19', 'rfgfgr', 'rgrgrtgr', 'sg'),
(24, 'un', '2017-05-17', '2017-05-19', 'trhtrh', 'ththt', 'sg'),
(25, 'un', '2017-05-11', '2017-05-12', 'efg', 'regreg', 'sg'),
(26, 'un', '2017-05-18', '2017-05-20', 'fgbfgb', 'fbfbfg', 'sg'),
(27, 'un', '2017-05-04', '2017-05-12', 'thth', 'thtrh', 'sg'),
(28, 'un', '2017-05-05', '2017-05-13', 'htgrhbrt', 'hrthrth', 'sg'),
(29, 'un', '2017-05-12', '2017-05-27', 'frdgrd', 'grgr', 'sg'),
(30, 'un', '2017-05-16', '2017-05-27', 'rfgrg', 'ergergr', 'sg'),
(31, 'un', '2017-05-19', '2017-05-23', 'gvfdgrf', 'regregr', 'sg'),
(32, 'un', '2017-05-02', '2017-05-13', 'rgrg', 'regregr', 'sg'),
(33, 'un', '2017-05-10', '2017-05-12', 'rfgregre', 'regregre', 'sg');

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
  `WarningMessage` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT 'None'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- RELATIONS FOR TABLE `TreatmentMedication`:
--   `MedicationID`
--       `Medication` -> `MedicationID`
--   `TreatmentID`
--       `Treatment` -> `TreatmentID`
--

--
-- Dumping data for table `TreatmentMedication`
--

INSERT INTO `TreatmentMedication` (`TreatmentID`, `MedicationID`, `Dose`, `DoseDescription`, `OverruledWarning`, `WarningMessage`) VALUES
(2, 2, 3, '3 pills per day, in the morning', 1, 'Prescription is overdosed!'),
(13, 2, 67, 'ghg', 0, 'None'),
(14, 4, 67, 'ccc', 0, 'None'),
(21, 4, 57, 'fdbdfbvfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff', 0, 'None'),
(23, 2, 4, 'dsfsdsv', 0, 'None'),
(21, 2, 50, 'Dose Description', 0, 'None'),
(24, 2, 3, 'gbgbgb', 0, 'None'),
(25, 2, 4, 'rfbrbrb', 0, 'Prescription is overdosed!'),
(26, 2, 3, 'fgbfgbgf', 0, 'Prescription is overdosed!'),
(27, 2, 2, 'gthth', 0, 'Prescription is overdosed!'),
(28, 2, 3, 'rgrg', 0, 'Prescription is overdosed!'),
(29, 2, 2, 'vedferf', 0, 'None'),
(29, 3, 3, 'ferf', 1, 'Prescription is overdosed!'),
(33, 4, 2, 'fgfgfg', 0, 'None');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
