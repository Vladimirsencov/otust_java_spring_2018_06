let editCommentDialog;
let confirmDeleteCommentDialog;
let deleteCommentForm;
let currentBookId;

$(document).ready(() => {
        deleteCommentForm = $('#deleteCommentForm');
        editCommentDialog = makeDialog("#editCommentDialog", 724, 350, {
                    "Сохранить": () => {
                        if (validateForm('editCommentForm')) {
                            let id = $('#commentId').val();
                            let author = $('#commentAuthor').val();
                            let comment = $('#comment').val();

                            saveComment(id, currentBookId, author, comment)
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
                        let id = $("#commentIdForDelete").val();
                        deleteComment(currentBookId, id);
                    },
                    "Отмена": () => {
                        confirmDeleteCommentDialog.dialog("close");
                    }
                }, () => {
                    $('#commentId').html("");
                }
        );

        loadCommentsList(currentBookId);
});

function createBooksTableRow(bookId, comment){
    return  `<tr>` +
                `<td>${comment.commentingTime}</td>` +
                `<td>${comment.author}</td>` +
                `<td>${comment.comment}</td>` +
                `<td>` +
                    `<button onclick="openEditCommentDialog(${bookId}, ${comment.id}, '${comment.author}', '${comment.comment}')" class="row-btn edit-row-btn"></button>` +
                    `<button onclick="openDeleteCommentDialog(${comment.id})" class="row-btn del-row-btn"></button>` +
                `</td>` +
            `</tr>`;
}

function loadCommentsList(bookId){
    sendData("comment/", "GET", `bookId=${bookId}`, (comments) => {
        $("#commentsTableBody").html("");
        comments.forEach((comment) => {
            $("#commentsTableBody").append(createBooksTableRow(bookId, comment));
        });
    });
}

function openEditCommentDialog(bookId, commentId, author, comment) {
    $('#commentId').val(commentId);
    $('#commentAuthor').val(author);
    $('#comment').val(comment);
    editCommentDialog.dialog("option", "title", 'Добавление/редактирование комментария');
    editCommentDialog.dialog("open");
}

function openDeleteCommentDialog(commentId) {
    $('#commentIdForDelete').val(commentId);
    confirmDeleteCommentDialog.dialog("open");
}

function deleteComment(bookId, commentId){
    sendData(`comment/${commentId}/`, "DELETE", "", (operation) => {
        if (operation.status === "ok") {
            loadCommentsList(bookId);
        }
        confirmDeleteCommentDialog.dialog("close");
    });
}

function saveComment(id, bookId, author, comment) {
    let body = `id=${id}&bookId=${bookId}&author=` + encodeURIComponent(author) + `&comment=` + encodeURIComponent(comment);

    sendData("comment/" + (id? id: "") + "/", id? "PUT": "POST", body, (operation) => {
        if (operation.status === "ok") {
            loadCommentsList(bookId);
        }
        editCommentDialog.dialog("close");
    });
}