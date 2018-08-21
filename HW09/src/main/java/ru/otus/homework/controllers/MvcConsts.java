package ru.otus.homework.controllers;

class MvcConsts {
    public static final String VIEW_NAME_BOOKS_LIST = "books_list.html";
    public static final String VIEW_NAME_BOOK_DETAILS = "book_details.html";
    public static final String VIEW_NAME_REDIRECT_TO_CONTEXT_PATH = "redirect:/";
    public static final String VIEW_NAME_REDIRECT_TO_DETAILS_PAGE = VIEW_NAME_REDIRECT_TO_CONTEXT_PATH + "book_details?id=";

    public static final String REQUEST_BOOK_DETAILS = "/book_details";
    public static final String REQUEST_BOOK_DELETE = "/book/delete";
    public static final String REQUEST_BOOK_SAVE = "/book/save";
    public static final String REQUEST_COMMENT_SAVE = "/comment/save";
    public static final String REQUEST_COMMENT_DELETE = "/comment/delete";

    public static final String MODEL_ATTR_BOOKS = "books";
    public static final String MODEL_ATTR_BOOK = "book";
    public static final String MODEL_ATTR_COMMENTS = "comments";





}
