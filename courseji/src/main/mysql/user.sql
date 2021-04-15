CREATE TABLE IF NOT EXISTS 'user'
(
    'id'         INT UNSIGNED AUTO_INCREMENT NOT NULL,
    'date'       Date                        NOT NULL,
    'name'       VARCHAR(15)                 NOT NULL,
    'email'      VARCHAR(50) UNIQUE          NOT NULL,
    'password'   VARCHAR(20)                 NOT NULL,
    'phone'      VARCHAR(11) UNIQUE          NOT NULL,
    'is_teacher' BOOL                        NOT NULL,
    'avatar'     TEXT                        NOT NULL,
    'picture'    TEXT                        NOT NULL,
    PRIMARY KEY (id)
) CHARSET = utf8;