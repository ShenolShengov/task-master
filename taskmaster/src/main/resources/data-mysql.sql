INSERT INTO pictures (original_name, public_id, url)
VALUES ('default-profile-picture', 'users/profile-pictures/default-profile-picture',
        'https://res.cloudinary.com/dgmkjzht1/image/upload/v1/users/profile-pictures/default-profile-picture');

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO users (username, full_name, email, age, password, profile_picture_id)
VALUES ('Shenol10', 'Shenol Shengov', 'shenolshengov41@gmail.com', 17,
        '06f08ffab7c803d533af8a1d9f5ee152b59819bec5bb29f35c07ea3ea9d7e8dabbb6b1ac1885d204630206c47c5cbe94', 1);
INSERT INTO users_roles (user_id, roles_id)
VALUES (1, 1),
       (1, 2);
#         password - asdasd
