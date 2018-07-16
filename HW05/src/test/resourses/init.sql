DROP TABLE books_genres;
DROP TABLE books_authors;
DROP TABLE books;
DROP TABLE genres;
DROP TABLE authors;

CREATE TABLE authors(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255));
CREATE TABLE genres(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255));
CREATE TABLE books(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), description VARCHAR(255), pub_year INT);
CREATE TABLE books_authors(book_id BIGINT REFERENCES books(id), author_id BIGINT REFERENCES authors(id));
CREATE TABLE books_genres(book_id BIGINT REFERENCES books(id), genre_id BIGINT REFERENCES genres(id));