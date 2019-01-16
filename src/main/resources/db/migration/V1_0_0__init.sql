CREATE TABLE IF NOT EXISTS item (
  id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR (100) NOT NULL,
  price BIGINT NOT NULL,
  quantity INTEGER NOT NULL,

  created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS category (
  id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR (100) NOT NULL,

  created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS item_category (
  item_id     INTEGER REFERENCES item (id),
  category_id INTEGER REFERENCES category (id)
);
