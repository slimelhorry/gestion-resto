-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Jeu 15 Avril 2021 à 20:11
-- Version du serveur: 5.5.16
-- Version de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `resto_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `tb_aliment`
--

CREATE TABLE IF NOT EXISTS `tb_aliment` (
  `code_aliment` varchar(50) NOT NULL,
  `nom_aliment` varchar(100) NOT NULL,
  `prix_aliment` decimal(9,2) NOT NULL,
  PRIMARY KEY (`code_aliment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `tb_aliment`
--

INSERT INTO `tb_aliment` (`code_aliment`, `nom_aliment`, `prix_aliment`) VALUES
('CARPE_RIZ', 'Soupe de carpe au riz', '2000.00'),
('EAU_GB', 'Eau minérale 1.5 cl', '500.00'),
('EAU_PB', 'Eau minérale 50cl', '250.00'),
('FRUIT_JUS', 'Jus de fruit', '500.00'),
('POULET_B', 'Poulet braisé', '3000.00'),
('SHANDWICH_O', 'Shandwich omellette', '500.00'),
('SHANDWICH_V', 'Shandwich de viande hachée', '500.00'),
('SUCRERIE_G', 'Sucrerie grande bouteille', '1250.00'),
('SUCRERIE_P', 'sucrerie petite bouteille', '300.00');

-- --------------------------------------------------------

--
-- Structure de la table `tb_commande`
--

CREATE TABLE IF NOT EXISTS `tb_commande` (
  `id_commande` int(11) NOT NULL AUTO_INCREMENT,
  `aliment` varchar(50) NOT NULL,
  `qte_aliment` smallint(6) NOT NULL,
  `tab_num` smallint(6) NOT NULL,
  PRIMARY KEY (`id_commande`),
  KEY `fk` (`aliment`),
  KEY `fk2` (`tab_num`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- Contenu de la table `tb_commande`
--

INSERT INTO `tb_commande` (`id_commande`, `aliment`, `qte_aliment`, `tab_num`) VALUES
(1, 'FRUIT_JUS', 2, 3),
(2, 'EAU_PB', 3, 3),
(3, 'SHANDWICH_O', 2, 3),
(4, 'FRUIT_JUS', 1, 2),
(5, 'SHANDWICH_V', 1, 2),
(6, 'EAU_PB', 1, 2),
(13, 'EAU_GB', 1, 1),
(14, 'POULET_B', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `tb_historique`
--

CREATE TABLE IF NOT EXISTS `tb_historique` (
  `id_commande` int(11) NOT NULL AUTO_INCREMENT,
  `aliment` varchar(50) NOT NULL,
  `qte_aliment` smallint(6) NOT NULL,
  `tab_num` smallint(6) NOT NULL,
  `date_commande` datetime NOT NULL,
  PRIMARY KEY (`id_commande`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- Contenu de la table `tb_historique`
--

INSERT INTO `tb_historique` (`id_commande`, `aliment`, `qte_aliment`, `tab_num`, `date_commande`) VALUES
(1, 'FRUIT_JUS', 2, 3, '2021-04-15 21:03:52'),
(2, 'EAU_PB', 3, 3, '2021-04-15 21:04:19'),
(3, 'SHANDWICH_O', 2, 3, '2021-04-15 21:05:32'),
(4, 'FRUIT_JUS', 1, 2, '2021-04-15 21:05:55'),
(5, 'SHANDWICH_V', 1, 2, '2021-04-15 21:06:07'),
(6, 'EAU_PB', 1, 2, '2021-04-15 21:06:25'),
(7, 'SHANDWICH_V', 2, 1, '2021-04-15 21:06:54'),
(8, 'EAU_GB', 1, 1, '2021-04-15 21:07:13'),
(13, 'EAU_GB', 1, 1, '2021-04-15 21:57:39'),
(14, 'POULET_B', 1, 1, '2021-04-15 21:57:57');

-- --------------------------------------------------------

--
-- Structure de la table `tb_table`
--

CREATE TABLE IF NOT EXISTS `tb_table` (
  `num_tab` smallint(6) NOT NULL,
  `nb_place` smallint(6) NOT NULL,
  `etat` varchar(10) NOT NULL,
  PRIMARY KEY (`num_tab`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `tb_table`
--

INSERT INTO `tb_table` (`num_tab`, `nb_place`, `etat`) VALUES
(1, 1, 'occupee'),
(2, 1, 'occupee'),
(3, 2, 'occupee'),
(4, 2, 'libre'),
(5, 4, 'occupee'),
(6, 4, 'libre'),
(7, 6, 'libre'),
(8, 6, 'libre'),
(9, 4, 'libre');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `tb_commande`
--
ALTER TABLE `tb_commande`
  ADD CONSTRAINT `fk2` FOREIGN KEY (`tab_num`) REFERENCES `tb_table` (`num_tab`),
  ADD CONSTRAINT `fk` FOREIGN KEY (`aliment`) REFERENCES `tb_aliment` (`code_aliment`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
