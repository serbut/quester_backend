CREATE TABLE users (
  id SERIAL NOT NULL PRIMARY KEY,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(60) NOT NULL,
  firstname VARCHAR(50) NOT NULL,
  lastname VARCHAR(50) NOT NULL,
  token VARCHAR(20) NOT NULL,
  about TEXT);
CREATE UNIQUE INDEX idx_email ON users (email);

CREATE TABLE quest (
  id SERIAL NOT NULL PRIMARY KEY,
  uuid UUID DEFAULT uuid_generate_v4() UNIQUE,
  version INT NOT NULL DEFAULT 0,
  user_id INT REFERENCES users(id) NOT NULL,
  title VARCHAR(100) NOT NULL
);

CREATE TABLE point (
  id SERIAL NOT NULL PRIMARY KEY,
  uuid UUID DEFAULT uuid_generate_v4() UNIQUE,
  quest_id INT REFERENCES quest(id) NOT NULL,
  x REAL NOT NULL,
  y REAL NOT NULL
);

CREATE TABLE user_quest (
  id SERIAL NOT NULL PRIMARY KEY,
  user_id INT REFERENCES users(id) NOT NULL,
  point_id INT REFERENCES point(id) NOT NULL
);
