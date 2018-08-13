package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.interfaces.i18n.MessageSourceWrapper;
import ru.otus.homework.interfaces.services.DataStorageService;
import ru.otus.homework.models.BookComment;

import java.util.Date;
import java.util.List;

import static ru.otus.homework.shell.ShellConst.*;

@ShellComponent
public class BookCommentCommands {
    private static final String ERR_COMMENT_NOT_FOUND = "err.comment.not.found";
    private static final String CAPTION_COMMENTS_FOR_BOOK_PATTERN = "caption.comments.for.book.pattern";
    private static final String COMMENT_OUTPUT_PATTERN = "%s  [%s] %s\n";

    private final DataStorageService dataStorageService;
    private final MessageSourceWrapper messageSourceWrapper;

    @Autowired
    public BookCommentCommands(DataStorageService dataStorageService, MessageSourceWrapper messageSourceWrapper) {
        this.dataStorageService = dataStorageService;
        this.messageSourceWrapper = messageSourceWrapper;
    }

    @ShellMethod("AddComment")
    public void addComment(@ShellOption String bookId, @ShellOption String comment, @ShellOption(defaultValue = "anonimus") String author) {
        dataStorageService.getBookBriefById(bookId).map(b -> dataStorageService.saveBookComment(new BookComment(null, new Date(), author, comment, b))).orElseGet(() -> {
            System.err.println(messageSourceWrapper.getMsg(ERR_BOOK_NOT_FOUND));
            return null;
        });
    }

    @ShellMethod("UpdateComment")
    public void updateComment(@ShellOption String commentId, @ShellOption String comment, @ShellOption(defaultValue = "") String author) {
        BookComment bookComment = dataStorageService.getBookCommentById(commentId).map(c -> {
            return dataStorageService.saveBookComment(new BookComment(commentId, new Date(), author.isEmpty()? c.getAuthor(): author, comment, c.getBookBrief()));
        }).orElseGet(() ->{
            System.err.println(messageSourceWrapper.getMsg(ERR_COMMENT_NOT_FOUND));
            return null;
        });
    }

    @ShellMethod("RemoveComment")
    public void removeComment(@ShellOption String commentId){
        dataStorageService.removeBookComment(commentId);
    }

    @ShellMethod("ListComments")
    public String listComments(@ShellOption String bookId) {
        StringBuilder builder = new StringBuilder();

        dataStorageService.getBookBriefById(bookId).ifPresent(book -> {
            List<BookComment> comments = dataStorageService.getAllBookCommentsByBookId(bookId);
            builder.append(messageSourceWrapper.getMsg(CAPTION_COMMENTS_FOR_BOOK_PATTERN, book.getName()));
            builder.append(CONTENT_DELIMITER);
            comments.forEach(comment -> builder.append(String.format(COMMENT_OUTPUT_PATTERN, comment.getCommentingTime(), comment.getAuthor(), comment.getComment())));
        });
        return builder.toString();

    }

}
