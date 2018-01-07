CREATE TABLE feature (
  id   BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  UNIQUE INDEX (name),
  PRIMARY KEY (id)
);

CREATE TABLE version_feature (
  id         BIGINT UNSIGNED AUTO_INCREMENT,
  version_id BIGINT UNSIGNED,
  feature_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (version_id) REFERENCES version (id),
  FOREIGN KEY (feature_id) REFERENCES feature (id),
  INDEX (version_id),
  UNIQUE INDEX (version_id, feature_id)
);
