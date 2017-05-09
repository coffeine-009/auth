-- SEQUENCES
CREATE SEQUENCE roles_sequence START 1;
CREATE SEQUENCE users_sequence START 1;
CREATE SEQUENCE access_sequence START 1;
CREATE SEQUENCE emails_sequence START 1;
CREATE SEQUENCE social_accounts_sequence START 1;
CREATE SEQUENCE recovery_access_sequence START 1;

-- TABLES
/**
 * Roles.
 * Application roles.
 */
CREATE TABLE roles(
  id            BIGINT          DEFAULT nextval('roles_sequence') NOT NULL,

  code          VARCHAR( 8 )    NOT NULL UNIQUE,
  title         VARCHAR( 32 )   NOT NULL,
  description   VARCHAR( 128 ),

  PRIMARY KEY( id )
);

/**
 * Users.
 * Application users.
 */
CREATE TABLE users (
  id          BIGINT        DEFAULT nextval('users_sequence') NOT NULL,

  first_name  VARCHAR( 16 ) NOT NULL,
  last_name   VARCHAR( 16 ),
  middle_name VARCHAR( 32 ),

  gender      BOOLEAN,

  locale      VARCHAR( 5 ) NOT NULL,

  created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY( id )
);

/**
 * Access.
 * Access params for each user.
 */
CREATE TABLE access (
  id            BIGINT      DEFAULT nextval('access_sequence') NOT NULL,

  id_user       BIGINT      NOT NULL,

  password      VARCHAR(80) NOT NULL,

  updated_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
  created_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY( id ),

  FOREIGN KEY( id_user ) REFERENCES users( id )
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

/**
 * Emails.
 * List of e-mails for each user.
 */
CREATE TABLE emails (
  id          BIGINT        DEFAULT nextval('emails_sequence') NOT NULL,

  id_user     BIGINT        NOT NULL,

  address     VARCHAR( 80 ) NOT NULL UNIQUE,

  created_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY( id ),

  FOREIGN KEY( id_user ) REFERENCES users( id )
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

/**
 * UserRoles.
 * Mapping users with roles.
 */
CREATE TABLE user_roles (
  id_user   BIGINT NOT NULL,
  id_role   BIGINT NOT NULL,

  UNIQUE( id_user, id_role ),

  FOREIGN KEY( id_user ) REFERENCES users( id )
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

  FOREIGN KEY( id_role ) REFERENCES roles( id )
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

/**
 * SocialAccounts.
 * Information about access to social accounts.
 */
CREATE TABLE social_accounts (
  id            BIGINT          DEFAULT nextval('social_accounts_sequence') NOT NULL,

  id_user       BIGINT          NOT NULL,
  social_id     BIGINT          NOT NULL,

  access_token  VARCHAR( 255 )  NOT NULL,
  expires_in    INTEGER         NOT NULL CHECK (expires_in >= 100),
  social_name   VARCHAR(255),

  PRIMARY KEY( id ),

  FOREIGN KEY( id_user ) REFERENCES users( id )
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

/**
 * RecoveryAccess.
 * Data for recovering access.
 */
CREATE TABLE recovery_access(
  id          BIGINT    DEFAULT nextval('recovery_access_sequence') NOT NULL,

  id_user     BIGINT NOT NULL UNIQUE,

  hash        VARCHAR( 256 ) NOT NULL,

  expired_at  TIMESTAMP NOT NULL,
  created_at  TIMESTAMP DEFAULT NOW() NOT NULL,

  PRIMARY KEY( id ),

  FOREIGN KEY( id_user ) REFERENCES users( id )
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

/**
 * Add owner to sequence.
 */
ALTER SEQUENCE roles_sequence OWNED BY roles.id;
ALTER SEQUENCE users_sequence OWNED BY users.id;
ALTER SEQUENCE access_sequence OWNED BY access.id;
ALTER SEQUENCE emails_sequence OWNED BY emails.id;
ALTER SEQUENCE social_accounts_sequence OWNED BY social_accounts.id;
ALTER SEQUENCE recovery_access_sequence OWNED BY recovery_access.id;
