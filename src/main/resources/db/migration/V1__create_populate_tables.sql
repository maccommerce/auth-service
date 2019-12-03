CREATE TABLE roles (
    id SERIAL NOT NULL,
    role VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id SERIAL NOT NULL,
    username VARCHAR(255) NOT NULL,
    password TEXT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username)
);

CREATE TABLE users_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO roles (id, role) VALUES (1001,'ADMINISTRADOR');
INSERT INTO roles (id, role) VALUES (1002,'VENDEDOR');
INSERT INTO roles (id, role) VALUES (1003,'CLIENTE');
