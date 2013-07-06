CREATE  TABLE `musikdb`.`tableconfiguration` (

  `id` INT NOT NULL ,

  `columnName` VARCHAR(255) NULL ,

  `reflectionName` VARCHAR(255) NULL ,

  `table` VARCHAR(255) NULL ,

  PRIMARY KEY (`id`) );

INSERT INTO `musikdb`.`tableconfiguration` (`id`, `columnName`, `reflectionName`, `table`) VALUES (1, 'Interpret', 'interpret', 'song');

INSERT INTO `musikdb`.`tableconfiguration` (`id`, `columnName`, `reflectionName`) VALUES (2, 'Titel', 'title');

INSERT INTO `musikdb`.`tableconfiguration` (`id`, `columnName`, `reflectionName`) VALUES (3, 'Album', 'album');

INSERT INTO `musikdb`.`tableconfiguration` (`id`, `columnName`, `reflectionName`) VALUES (4, 'Tanzart', 'tanzart');

INSERT INTO `musikdb`.`tableconfiguration` (`id`, `columnName`, `reflectionName`) VALUES (5, 'User', 'user');

UPDATE `musikdb`.`tableconfiguration` SET `table`='song' WHERE `id`='2';

UPDATE `musikdb`.`tableconfiguration` SET `table`='song' WHERE `id`='3';

UPDATE `musikdb`.`tableconfiguration` SET `table`='song' WHERE `id`='4';

UPDATE `musikdb`.`tableconfiguration` SET `table`='song' WHERE `id`='5';
INSERT INTO `musikdb`.`tableconfiguration` (`id`, `columnName`, `reflectionName`, `table`) VALUES (6, 'Datum', 'date', 'song');


