INSERT INTO genres (name) VALUES('Фэнтези'), ('Юмористическое фэнтези'), ('Мистическая фантастика');

INSERT INTO authors (name) VALUES('Вадим Арчер'), ('Александр Дихнов'), ('Алексей Пехов'), ('Елена Бычкова'), ('Наталья Турчанинова');

INSERT INTO books (name, pub_year, description) VALUES('Выбравший бездну', 2010, 'В результате шалости одного из могущественных богов на Земле появился род человеческий. Однако люди стали развиваться совсем не так, как хотелось бы божественным силам, более того — они сами стремятся стать творцами и проникнуть в высшие сферы мироздания. Боги пытаются уничтожить людей всемирными катаклизмами, подчинить их своей воле, затормозить их духовное развитие — и лишь один из них, хулиган и бездельник Маг, которому люди обязаны своим появлением на свет, старается спасти своих подопечных от гнева старших богов. Однако люди видят в нем только злого насмешника и искусителя — Сатану…');
INSERT INTO books (name, pub_year, description) VALUES('Записки Черного Властелина', 2002, 'Когда над миром сгущается мрак, когда Черный Властелин начинает очередной раунд борьбы с силами Света, приверженцы Добра объединяются, чтобы любыми средствами ему противостоять. Но на сей раз Властелин Тьмы замыслил перехитрить всех. Жестокий бой ведется не по правилам, и финал его непредсказуем.');
INSERT INTO books (name, pub_year, description) VALUES('Киндрет', 2018, 'Они управляют миром с начала времен, втягивая человечество в бесконечные войны. Они едины лишь в одном - жажде власти и могущества. В древности им поклонялись как богам. Их кровь священна и проклята. И приносит бессмертие, а также - особый дар, который дает полную реализацию скрытым способностям человека… В современной Столице их существование считают мифом или страшной сказкой, но они продолжают жить среди нас. Их время - ночь. Кровные братья приходят, чтобы убивать, ненавидеть, мстить. Что люди смогут противопоставить им?');


INSERT INTO books_genres(book_id, genre_id) VALUES(1, 1), (2, 2), (3, 3);
INSERT INTO books_authors(book_id, author_id) VALUES(1, 1), (2, 2), (3, 3), (3, 4), (3, 5);

INSERT INTO books_comments(book_id, author, comment) VALUES(1, 'Саша', 'Очень хорошая книга'), (1, 'Игорь', 'Неожиданная точка зрения'),
                                                           (2, 'Петя', 'Смешная книга'), (2, 'Леша', 'Нестандартный сюжет'),
                                                           (3, 'Люба', 'Круто. Люблю про вампиров'), (3, 'Ира', 'Приятно написано. Интересно читать');

