DROP TABLE IF EXISTS posts, tags, users CASCADE;

CREATE TABLE users
(
    user_id   SERIAL PRIMARY KEY NOT NULL,
    create_date INTEGER NOT NULL,
    email VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL
);

CREATE TABLE tags
(
    id   SERIAL PRIMARY KEY NOT NULL,
    created_date INTEGER NOT NULL,
    description VARCHAR(255) NOT NULL,
    tag_name VARCHAR(255) NOT NULL,
    user_user_id INTEGER NOT NULL,
    FOREIGN KEY (user_user_id) REFERENCES users(user_id) ON DELETE CASCADE

);

CREATE TABLE posts
(
    post_id   SERIAL PRIMARY KEY NOT NULL,
    create_date INTEGER NOT NULL,
    description VARCHAR(255) NOT NULL,
    post_name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    vote_count INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
