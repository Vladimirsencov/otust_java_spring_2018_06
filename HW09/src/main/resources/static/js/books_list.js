let editBookDialog;
let confirmDeleteBookDialog;
let deleteBookForm;

$(document).ready(() => {
        deleteBookForm = $('#deleteBookForm');
        editBookDialog = makeDialog("#editBookDialog", 724, 350, {
                    "Сохранить": () => {
                        //$('#editBookDialog').dialog("close");
                        if (validateForm('editBookForm')) {
                            $('#editBookForm').submit();
                        } else {
                            alert("Заполнены не все поля формы!");
                        }
                    },
                    "Отмена": () => {
                        $('#editBookDialog').dialog("close");
                    }
                }, () => {
                    $('#bookId').html("");
                }
        );

        confirmDeleteBookDialog = makeDialog("#confirmDeleteBookDialog", 400, 190, {
                    "Удалить": () => {
                        $('#deleteBookForm').submit();
                    },
                    "Отмена": () => {
                        $('#confirmDeleteBookDialog').dialog("close");
                    }
                }, () => {
                    $('#bookId').html("");
                }
        );
});

function openEditBookDialog(id, name, genres, authors, pubYear, description) {
    $('#bookId').val(id);
    $('#bookName').val(name);
    $('#bookGenre').val(genres);
    $('#bookAuthor').val(authors);
    $('#bookPubYear').val(pubYear);
    $('#bookDescription').val(description);
    $('#bookDescription').html(description);
    editBookDialog.dialog("option", "title", 'Добавление/редактирование книги');
    editBookDialog.dialog("open");
}

function deleteBook(id) {
    $('#bookIdForDelete').val(id);
    confirmDeleteBookDialog.dialog("open");
}