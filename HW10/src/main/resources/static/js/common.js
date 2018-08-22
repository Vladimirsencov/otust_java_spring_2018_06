function sendData(url, method, sendingData, onSuccess) {
    $.ajax({
        type: method,
        dataType: "json",
        url: url,
        data: sendingData,
        success: onSuccess
    });
}

function makeDialog(dialogId, width, height, buttons, closeFun){
    dialog = $(dialogId).dialog({
        autoOpen: false,
        resizable: false,
        height: height,
        width: width,
        modal: true,
        buttons: buttons,
        close: closeFun
    });
    return dialog;
}

function validateForm(formId) {
    let fields = $('#' + formId + ' :input');
    let res = true;
    fields.each(function() {
        if ($(this).attr("required") === "required" && (!$(this).val() || $(this).val() === "")){
            res = false;
        }
    });
    return res;
}
