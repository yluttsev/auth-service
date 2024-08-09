--password structure: {login}password (example for user1: "user1password").

insert into app_user (id, login, password, email)
values ('dbb08c45-4789-4919-a765-54c30f54996a', 'user1', '$2a$10$168FzHCjx4ielw8H/YcdGerBB/hIE5gaGUBbuwat/Ow.VagS.E4yy', 'user1@inbox.ru'),
       ('339189f9-a9a7-4452-b374-b75a8ebbb363', 'user2', '$2a$10$irik4W70Eii4.zMIbGQLW.X3JCmoH8jaDmTrzL5LjzCCeqxEUylCW', 'user2@inbox.ru');