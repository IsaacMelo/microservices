INSERT INTO  tb_user  ( id ,  password ,  role ,  username ) VALUES
	(3, '$2a$12$Sth1lInrs.StiKFfcnH9zujTUQWTnND/hxFACTrzlxPVvt/4HDodi', '', 'super');

INSERT INTO  tb_group  ( id ,  name ) VALUES
	(3, 'super');
	
INSERT INTO  tb_group_permission  ( id_group ,  id_permission ) VALUES
	(3, 2);
	
INSERT INTO  tb_user_group  ( id_group ,  id_user ) VALUES
	(3, 3);