let editBookDialog;
let confirmDeleteBookDialog;
let deleteBookForm;

$(document).ready(() => {
        deleteBookForm = $('#deleteBookForm');
        editBookDialog = makeDialog("#editBookDialog", 724, 350, {
                    "Сохранить": () => {
                        if (validateForm('editBookForm')) {
                            let id = $('#bookId').val();
                            let name = $('#bookName').val();
                            let description = $('#bookDescription').val();
                            let pubYear = $('#bookPubYear').val();
                            let authors = $('#bookAuthor').val();
                            let genres = $('#bookGenre').val();
                            saveBook(id, name, description, pubYear, authors, genres);
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
                            let id = $('#bookIdForDelete').val();
                            deleteBook(id);
                    },
                    "Отмена": () => {
                        $('#confirmDeleteBookDialog').dialog("close");
                    }
                }, () => {
                    $('#bookId').html("");
                }
        );

        loadBooksList();
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

function createBooksTableRow(book){
    return  `<tr>` +
                `<td class="books-list-name-cell"><a href="/book_details?id=${book.id}">${book.name}</a></td>` +
                `<td class="books-list-genres-cell">${book.genres}</td>` +
                `<td class="books-list-authors-cell">${book.authors}</td>` +
                `<td>${book.shortDescription}</td>` +
                `<td>` +
                    `<button onclick="openEditBookDialog(${book.id}, '${book.name}', '${book.genres}', '${book.authors}', ${book.pubYear}, '${book.description}')" class="row-btn edit-row-btn"></button>` +
                    `<button onclick="openDeleteBookDialog(${book.id})" class="row-btn del-row-btn"></button>` +
                `</td>` +
            `</tr>`;
}

function loadBooksList(){
    sendData("book/", "GET", "", (books) => {
        $("#booksTableBody").html("");
        books.forEach((book) => {
            $("#booksTableBody").append(createBooksTableRow(book));
        });
    });
}

function openDeleteBookDialog(id) {
    $('#bookIdForDelete').val(id);
    confirmDeleteBookDialog.dialog("open");
}

function deleteBook(id) {
    sendData(`book/${id}/`, "DELETE", "", (operation) => {
        if (operation.status === "ok") {
            loadBooksList();
        }
        confirmDeleteBookDialog.dialog("close");
    });
}

function saveBook(id, name, description, pubYear, authors, genres) {
    let body = `id=${id}&name=` + encodeURIComponent(name) + `&description=` + encodeURIComponent(description) + `&pubYear=${pubYear}` +
               `&authors=` + encodeURIComponent(authors) + `&genres=` + encodeURIComponent(genres);

    sendData("book/" + (id? id: "") + "/", id? "PUT": "POST", body, (operation) => {
        if (operation.status === "ok") {
            loadBooksList();
        }
        editBookDialog.dialog("close");
    });
}