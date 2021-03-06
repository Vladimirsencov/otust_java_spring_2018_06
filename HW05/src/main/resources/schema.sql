DROP TABLE IF EXISTS books_genres;
DROP TABLE IF EXISTS  books_authors;
DROP TABLE IF EXISTS  books;
DROP TABLE IF EXISTS  genres;
DROP TABLE IF EXISTS  authors;

CREATE TABLE authors(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255));
CREATE TABLE genres(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255));
CREATE TABLE books(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), description VARCHAR(255), pub_year INT);
CREATE TABLE books_authors(book_id BIGINT REFERENCES books(id) ON DELETE CASCADE, author_id BIGINT REFERENCES authors(id) ON DELETE CASCADE);
CREATE TABLE books_genres(book_id BIGINT REFERENCES books(id) ON DELETE CASCADE, genre_id BIGINT REFERENCES genres(id) ON DELETE CASCADE);