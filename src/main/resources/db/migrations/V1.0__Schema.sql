CREATE SEQUENCE roles_sequence START 1;
CREATE SEQUENCE users_sequence START 1;
CREATE SEQUENCE access_sequence START 1;
CREATE SEQUENCE emails_sequence START 1;
CREATE SEQUENCE social_accounts_sequence START 1;
CREATE SEQUENCE recovery_access_sequence START 1;

/**
 * Roles.
 * Application roles.
 */
CREATE TABLE roles(
  id            BIGINT          NOT NULL,

  code          VARCHAR( 8 )    NOT NULL UNIQUE,
  title         VARCHAR( 32 )   NOT NULL,
  description   VARCHAR( 128 ),

  PRIMARY KEY( id )
);

/**
 * Users.
 */
CREATE TABLE users (
  id          BIGINT NOT NULL,

  first_name  VARCHAR( 16 ) NOT NULL,
  last_name   VARCHAR( 16 ),
  middle_name VARCHAR( 32 ),

  gender      BOOLEAN,

  locale      VARCHAR( 5 ) NOT NULL,

  created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY( id )
);


CREATE TABLE access (
  id            BIGINT      NOT NULL,

  id_user       BIGINT      NOT NULL,

  password      VARCHAR(80) NOT NULL,

  updated_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
  created_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY(id)
);

/**
 * Emails
 */
CREATE TABLE emails (
  id          BIGINT        NOT NULL,

  id_user     BIGINT        NOT NULL,

  address     VARCHAR( 80 ) NOT NULL UNIQUE,

  created_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY( id ),

  FOREIGN KEY(id_user) REFERENCES users(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

-- auto-generated definition
CREATE TABLE user_roles (
  id_user   BIGINT NOT NULL,
  id_role   BIGINT NOT NULL,

  unique (id_user, id_role)
);



-- auto-generated definition
CREATE TABLE social_accounts (
  id            BIGINT NOT NULL,

  id_user       BIGINT NOT NULL,
  social_id     BIGINT NOT NULL,

  access_token  VARCHAR( 255 ) NOT NULL,
  expires_in    INTEGER NOT NULL check (expires_in >= 100),
  social_name   VARCHAR(255),

  PRIMARY KEY( id ),

  FOREIGN KEY( id_user) REFERENCES users( id )
    ON UPDATE CASCADE
    ON DELETE RESTRICT
)
;

-- auto-generated definition
CREATE TABLE recovery_access(
  id          BIGINT NOT NULL,

  id_user     BIGINT NOT NULL UNIQUE,

  hash        VARCHAR( 256 ) NOT NULL,

  expired_at  TIMESTAMP NOT NULL,
  created_at  TIMESTAMP DEFAULT NOW() NOT NULL,

  PRIMARY KEY( id ),

  FOREIGN KEY( id_user ) REFERENCES users( id )
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);
