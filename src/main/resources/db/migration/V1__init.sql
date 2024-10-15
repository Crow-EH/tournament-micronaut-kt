CREATE TABLE player (
                       nickname varchar(100) NOT NULL UNIQUE,
                       score INTEGER NOT NULL DEFAULT 0,
                       CONSTRAINT pk_player PRIMARY KEY (nickname)
);
