/**
 * Tests.
 * Mocking data.
 */

/**
 * Roles.
 */
INSERT INTO roles(
  id,
  code,
  title,
  description
) VALUES
( 1001, 'ADMIN', 'Administrator', 'Admin' ),
( 1002, 'POET', 'Poet', 'Poet.' ),
( 1003, 'COMPOSER', 'Composer', 'Composer.' );

/**
 * Users.
 */
INSERT INTO users(
  id,
  first_name,
  last_name,
  middle_name,
  gender,
  locale,
  created_at
) VALUES
( 1001, 'Vitaliy', 'Tsutsman', 'Muroslavovych', 1, 'uk-UA', '2015-01-03 23:43:32' ),
( 1002, 'Test', 'Unit', 'Integration', 1, 'uk-UA', '2015-01-03 23:43:32' );

/**
 * Contacts :: e-mail.
 */
INSERT INTO emails(
  id,
  address,
  id_user
) VALUES
( 1001,  'user@virtuoso.com', 1001 ),
( 1002,  'unit@test.org', 1002 );

/**
 * Accesses.
 */
INSERT INTO access(
  id,
  id_user,
  password
) VALUES
( 0, 1001, '$2a$10$iuBMb/fKo8gla94Uu/oWY.wDPGLBiiWCGnbTNBRDiZtpQtZDg4Uiq' );

/**
 * Social accounts
 */
INSERT INTO social_accounts(
  id,
  id_user,
  access_token,
  expires_in,
  social_id,
  social_name
) VALUES
( 0, 1001, 'acces$token', 3600, 1, 'FACEBOOK' );

/**
 * Requests for recovering access.
 */
INSERT INTO recovery_access(
  id,
  id_user,
  hash,
  expired_at,
  created_at
) VALUES
( 0, 1001, '4aa46f256305e166c4c63d178dc883c45ec87812', '2032-02-08 23:59:59', NOW() );
