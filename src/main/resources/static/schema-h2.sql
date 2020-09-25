DROP TABLE IF EXISTS MEMBER CASCADE;

CREATE TABLE MEMBER (
  ID                varchar(255) NOT NULL,
  NAME              varchar(255),
  AGE               INTEGER,
  PRIMARY KEY (ID)
);
