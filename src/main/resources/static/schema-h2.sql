DROP TABLE IF EXISTS member CASCADE;

CREATE TABLE member (
  member_id         varchar(30) NOT NULL,
  name              varchar(10) NOT NULL,
  PRIMARY KEY (member_id)
);
