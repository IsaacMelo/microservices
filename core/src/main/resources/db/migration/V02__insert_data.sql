INSERT INTO  tb_user  ( id ,  password ,  role ,  username ) VALUES
	(1, '$2a$12$Sth1lInrs.StiKFfcnH9zujTUQWTnND/hxFACTrzlxPVvt/4HDodi', '', 'admin'),
	(2, '$2a$12$Sth1lInrs.StiKFfcnH9zujTUQWTnND/hxFACTrzlxPVvt/4HDodi', '', 'user');

INSERT INTO  tb_group  ( id ,  name ) VALUES
	(1, 'admin'),
	(2, 'user');
	
INSERT INTO  tb_permission  ( id ,  name ) VALUES
	(1, 'ROLE_ADMIN'),
	(2, 'ROLE_USER');
	
INSERT INTO  tb_group_permission  ( id_group ,  id_permission ) VALUES
	(1, 1),
	(1, 2),
	(2, 2);
	
INSERT INTO  tb_user_group  ( id_group ,  id_user ) VALUES
	(1, 1),
	(2, 1),
	(2, 2);
	
INSERT INTO  tb_properties  ( id ,  application ,  label ,  profile ,  property ,  value ) VALUES
	(1, 'cache', 'latest', 'production', 'template.boleto.claro', 'template_boleto_claro.jasper'),
	(2, 'cache', 'latest', 'production', 'template.boleto.net', 'template_boleto_net_new.jasper'),
	(3, 'cache', 'latest', 'production', 'server.port', '8085');