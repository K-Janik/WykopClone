INSERT INTO users (email,create_date,enabled, password, username) VALUES
('email 1',2002,true,'pass 1','user 1'),
('email 2',2002,true,'pass 2','user 2'),
('email 3',2002,true,'pass 3','user 3');

INSERT INTO tags (description,created_date, tag_name,user_user_id) VALUES
('dsc 1',2001,'tag 1',1),
('dsc 2',2001,'tag 2',2),
('dsc 3',2001,'tag 3',3);

INSERT INTO posts (description,create_date, post_name, url, vote_count, tag_id, user_id) VALUES
('dsc 1',2000,'post 1','url 1',0,1,1),
('dsc 2',2000,'post 2','url 2',0,2,2),
('dsc 3',2000,'post 3','url 3',0,3,3),
('dsc 4',2000,'post 4','url 4',0,2,1),
('dsc 5',2000,'post 5','url 5',0,1,3),
('dsc 6',2000,'post 6','url 6',0,3,3);


