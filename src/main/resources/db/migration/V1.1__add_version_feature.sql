CREATE TABLE feature (
  id   BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  UNIQUE INDEX (name),
  PRIMARY KEY (id)
);

CREATE TABLE version_face (
  id         BIGINT UNSIGNED AUTO_INCREMENT,
  version_id BIGINT UNSIGNED NOT NULL,
  user_id    BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (version_id) REFERENCES version (id),
  FOREIGN KEY (user_id) REFERENCES user (id),
  UNIQUE INDEX (user_id, version_id)
);

CREATE TABLE version_face_feature (
  id         BIGINT UNSIGNED AUTO_INCREMENT,
  version_face_id BIGINT UNSIGNED NOT NULL,
  feature_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (version_face_id) REFERENCES version_face (id),
  FOREIGN KEY (feature_id) REFERENCES feature (id),
  UNIQUE INDEX (version_face_id, feature_id)
);
