INSERT INTO `myUPMsocial`.`usuario` (`id`, `name`) VALUES ('0', 'u1');
INSERT INTO `myUPMsocial`.`usuario` (`id`, `name`) VALUES ('1', 'u2');
INSERT INTO `myUPMsocial`.`usuario` (`id`, `name`) VALUES ('2', 'u3');
INSERT INTO `myUPMsocial`.`usuario` (`id`, `name`) VALUES ('3', 'u4');

INSERT INTO `myUPMsocial`.`amigos` (`usuario1_id`, `usuario2_id`) VALUES ('0', '1');
INSERT INTO `myUPMsocial`.`amigos` (`usuario1_id`, `usuario2_id`) VALUES ('0', '2');
INSERT INTO `myUPMsocial`.`amigos` (`usuario1_id`, `usuario2_id`) VALUES ('0', '3');
INSERT INTO `myUPMsocial`.`amigos` (`usuario1_id`, `usuario2_id`) VALUES ('1', '2');
INSERT INTO `myUPMsocial`.`amigos` (`usuario1_id`, `usuario2_id`) VALUES ('2', '3');

INSERT INTO `myUPMsocial`.`post` (`id`, `date`, `content`, `usuario_id`) VALUES ('0', '2016-04-27', 'p1', '0');
INSERT INTO `myUPMsocial`.`post` (`id`, `date`, `content`, `usuario_id`) VALUES ('0', '2016-04-28', 'p2', '0');
INSERT INTO `myUPMsocial`.`post` (`id`, `date`, `content`, `usuario_id`) VALUES ('0', '2016-04-29', 'p3', '0');
INSERT INTO `myUPMsocial`.`post` (`id`, `date`, `content`, `usuario_id`) VALUES ('0', '2016-04-30', 'p4', '1');