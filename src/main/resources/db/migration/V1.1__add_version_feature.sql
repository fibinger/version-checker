CREATE TABLE feature (
  id   BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  UNIQUE INDEX (name),
  PRIMARY KEY (id)
);

CREATE TABLE version_configuration (
  id         BIGINT UNSIGNED AUTO_INCREMENT,
  version_id BIGINT UNSIGNED NOT NULL,
  user_id    BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (version_id) REFERENCES version (id),
  FOREIGN KEY (user_id) REFERENCES user (id),
  UNIQUE INDEX (user_id, version_id)
);

CREATE TABLE version_configuration_feature (
  id         BIGINT UNSIGNED AUTO_INCREMENT,
  version_configuration_id BIGINT UNSIGNED NOT NULL,
  feature_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (version_configuration_id) REFERENCES version_configuration (id),
  FOREIGN KEY (feature_id) REFERENCES feature (id),
  UNIQUE INDEX (version_configuration_id, feature_id)
);
