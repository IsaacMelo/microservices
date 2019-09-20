-- --------------------------------------------------------
-- Versão do servidor:      5.6.44 - MySQL Community Server (GPL)
-- OS do Servidor:        Linux
-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS tb_cache  (
  id  bigint(20) NOT NULL AUTO_INCREMENT,
  cpf_cnpj  varchar(255) NOT NULL,
 PRIMARY KEY ( id )
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS tb_properties  (
  id  bigint(20) NOT NULL AUTO_INCREMENT,
  application  varchar(255) NOT NULL,
  label  varchar(255) NOT NULL,
  profile  varchar(255) NOT NULL,
  property  varchar(255) NOT NULL,
  value  varchar(255) DEFAULT NULL,
 PRIMARY KEY ( id )
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS tb_user  (
  id  bigint(20) NOT NULL AUTO_INCREMENT,
  password  varchar(255) NOT NULL,
  role  varchar(255) NOT NULL,
  username  varchar(255) NOT NULL,
 PRIMARY KEY ( id )
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS tb_group  (
  id  bigint(20) NOT NULL AUTO_INCREMENT,
  name  varchar(255) DEFAULT NULL,
 PRIMARY KEY ( id )
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS tb_permission  (
  id  bigint(20) NOT NULL AUTO_INCREMENT,
  name  varchar(255) DEFAULT NULL,
 PRIMARY KEY ( id )
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS tb_group_permission  (
  id_group  bigint(20) NOT NULL,
  id_permission  bigint(20) NOT NULL,
 KEY  FKid_permission  ( id_permission ),
 KEY  FKid_group  ( id_group ),
 CONSTRAINT  FKtb_group_permission_id_group FOREIGN KEY ( id_group ) REFERENCES  tb_group  ( id ),
 CONSTRAINT  FKtb_group_permission_tb_permission FOREIGN KEY ( id_permission ) REFERENCES  tb_permission  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS tb_user_group  (
  id_group  bigint(20) NOT NULL,
  id_user  bigint(20) NOT NULL,
 PRIMARY KEY ( id_group , id_user ),
 KEY  FKid_user  ( id_user ),
 CONSTRAINT  FKtb_user_group_id_group  FOREIGN KEY ( id_group ) REFERENCES  tb_group  ( id ),
 CONSTRAINT  FKtb_user_group_id_user  FOREIGN KEY ( id_user ) REFERENCES  tb_user  ( id )
) ENGINE=InnoDB DEFAULT CHARSET=latin1;