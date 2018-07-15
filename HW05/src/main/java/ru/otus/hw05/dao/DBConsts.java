package ru.otus.hw05.dao;

public class DBConsts {
    public static final String TBL_AUTHORS = "authors";
    public static final String TBL_GENRES = "genres";
    public static final String TBL_BOOKS = "books";
    public static final String TBL_BOOKS_AUTHORS = "books_authors";
    public static final String TBL_BOOKS_GENRES = "books_genres";

    public static final String F_ID = "id";
    public static final String F_NAME = "name";
    public static final String F_DESCRIPTION = "description";
    public static final String F_PUB_YEAR = "pub_year";
    public static final String F_BOOK_ID = "book_id";
    public static final String F_AUTHOR_ID = "author_id";
    public static final String F_GENRE_ID = "genre_id";

    public static final String TEMPLATE_INSERT_ID_NAME_REC_SQL = "insert into %1$s(%2$s) (select :name where not exists(select 1 from %1$s where %2$s = :name))";
    public static final String TEMPLATE_UPDATE_ID_NAME_REC_SQL = "update %s set %s = :name where %s = :id";

    public static final String TEMPLATE_REMOVE_BY_ID_SQL = "delete from %s where %s = :%s";
    public static final String TEMPLATE_SELECT_WITH_ONE_CONDITION_SQL = "select %s from %s where %s = :%s";
    public static final String TEMPLATE_INSERT_INTO_RELATIONS_TABLES_SQL = "insert into %s(%s, %s) values(?, ?)";

    public static final String TEMPLATE_SELECT_FROM_RELATIONS_TABLES_SQL     = "select src.%1$s, src.%2$s from %3$s rel left join %4$s src on rel.%5$s = src.%1$s where rel.%6$s = :%6$s";
    public static final String TEMPLATE_SELECT_ALL_FROM_RELATIONS_TABLES_SQL = "select rel.%1$s, src.%2$s, src.%3$s from %4$s rel left join %5$s src on rel.%6$s = src.%2$s order by rel.%1$s, src.%3$s";


}
