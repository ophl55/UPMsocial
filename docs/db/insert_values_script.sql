INSERT INTO `myUPMsocial`.`usuario` (`name`) VALUES ('u1');
INSERT INTO `myUPMsocial`.`usuario` (`name`) VALUES ('u2');
INSERT INTO `myUPMsocial`.`usuario` (`name`) VALUES ('u3');
INSERT INTO `myUPMsocial`.`usuario` (`name`) VALUES ('u4');

INSERT INTO `myUPMsocial`.`post` (`date`, `content`, `usuario_id`) VALUES ('2016-04-27', 'p1', '1');
INSERT INTO `myUPMsocial`.`post` (`date`, `content`, `usuario_id`) VALUES ('2016-04-28', 'p2', '1');
INSERT INTO `myUPMsocial`.`post` (`date`, `content`, `usuario_id`) VALUES ('2016-04-29', 'p3', '1');
INSERT INTO `myUPMsocial`.`post` (`date`, `content`, `usuario_id`) VALUES ('2016-04-30', 'p4', '2');

INSERT INTO `myUPMsocial`.`amigos` (`usuario1_id`, `usuario2_id`) VALUES ('1', '2');
INSERT INTO `myUPMsocial`.`amigos` (`usuario1_id`, `usuario2_id`) VALUES ('1', '3');
INSERT INTO `myUPMsocial`.`amigos` (`usuario1_id`, `usuario2_id`) VALUES ('1', '4');
INSERT INTO `myUPMsocial`.`amigos` (`usuario1_id`, `usuario2_id`) VALUES ('2', '3');
INSERT INTO `myUPMsocial`.`amigos` (`usuario1_id`, `usuario2_id`) VALUES ('3', '4');

