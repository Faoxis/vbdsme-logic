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


--- seeds ----
INSERT INTO item(name, quantity, price) VALUES ('Белый друг Extra Super', 5, 500000);
INSERT INTO item(name, quantity, price) VALUES ('Черный друг Ultra Extra Super', 5, 700000);
INSERT INTO item(name, quantity, price) VALUES ('Сразу два друга Lux', 1, 1000000);

INSERT INTO item(name, quantity, price) VALUES ('Супер подружка-2000', 5, 500000);
INSERT INTO item(name, quantity, price) VALUES ('Фронтовая часть-8000', 3, 1200000);
INSERT INTO item(name, quantity, price) VALUES ('Бэковая часть-8000', 3, 1200000);

INSERT INTO item(name, quantity, price) VALUES ('Плеть унижения', 10, 200000);
INSERT INTO item(name, quantity, price) VALUES ('Кожаные штаны из плохого разработчика', 1, 5000000);


INSERT INTO category(name) VALUES ('Для нее');
INSERT INTO category(name) VALUES ('Для него');
INSERT INTO category(name) VALUES ('БДСМ для всех');


INSERT INTO item_category VALUES (1, 1);
INSERT INTO item_category VALUES (2, 1);
INSERT INTO item_category VALUES (3, 1);
INSERT INTO item_category VALUES (4, 2);
INSERT INTO item_category VALUES (5, 2);
INSERT INTO item_category VALUES (6, 2);
INSERT INTO item_category VALUES (7, 3);
INSERT INTO item_category VALUES (8, 3);




