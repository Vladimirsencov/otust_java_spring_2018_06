let editCommentDialog;
let confirmDeleteCommentDialog;
let deleteCommentForm;

$(document).ready(function() {
        deleteCommentForm = $('#deleteCommentForm');
        editCommentDialog = makeDialog("#editCommentDialog", 724, 350, {
                    "Сохранить": () => {
                        if (validateForm('editCommentForm')) {
                            $('#editCommentForm').submit();
                        } else {
                            alert("Заполнены не все поля формы!");
                        }
                    },
                    "Отмена": () => {
                        $('#editCommentDialog').dialog("close");
                    }
                }, () => {
                    $('#commentId').html("");
                }
        );

        confirmDeleteCommentDialog = makeDialog("#confirmDeleteCommentDialog", 500, 190, {
                    "Удалить": () => {
                        $('#deleteCommentForm').submit();
                    },
                    "Отмена": () => {
                        $('#confirmDeleteCommentDialog').dialog("close");
                    }
                }, () => {
                    $('#commentId').html("");
                }
        );
});

function openEditCommentDialog(bookId, commentId, author, comment) {
    $('#bookId').val(bookId);
    $('#commentId').val(commentId);
    $('#commentAuthor').val(author);
    $('#comment').val(comment);
    editCommentDialog.dialog("option", "title", 'Добавление/редактирование комментария');
    editCommentDialog.dialog("open");
}

function deleteComment(bookId, commentId) {
    $('#bookIdForDeleteComment').val(bookId);
    $('#commentIdForDelete').val(commentId);
    confirmDeleteCommentDialog.dialog("open");
}