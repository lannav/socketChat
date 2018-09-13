var webSocket = null;
// var output = document.getElementById("output");
// var connectBtn = document.getElementById("connectBtn");
// var sendBtn = document.getElementById("sendBtn");
var availiableChats = [];
var activeChat = "";
var messages;
var username;

// function connect() {
    // // open the connection if one does not exist
    // if (webSocket !== undefined
    //     && webSocket.readyState !== WebSocket.CLOSED) {
    //     return;
    // }
    // // Create a websocket
    webSocket = new WebSocket("ws://localhost:60001/websocket");

    webSocket.onopen = function (event) {
    };

    webSocket.onmessage = function (event) {
        var notification = JSON.parse(event.data);
        switch (notification.command) {
            case "updateMessages" :
                updateMessages(notification);
                break;
            case "newAvailiableChat" :
                newAvailiableChat(notification);
                break;
            case "login" :
                login(notification);
                break;
            case "register" :
                register(notification);
                break;
            case "userIsExist" :
                alert("user is exist");
                break;
            case "chatIsExist" :
                alert("chat is exist");
                break;
            case "userNotFound" :
                alert("user not found");
                break;
        }
    };

    webSocket.onclose = function (event) {
    };
// }


function sendToServer(msg, command) {
    // if(webSocket == null)
    //     connect();

    var obj = {
        command : command,
        text : msg,
        room : activeChat
    };
    webSocket.send(JSON.stringify(obj));
}

function buttonHandler(button){
    var message = "";
    switch (button){
        case "send" : message = username + ": " + document.getElementById("msg").value.trim();
            if (message !== username + ": ") {
                sendToServer(message, "sendMessage");
                document.getElementById("msg").value = "";
            }
            break;
        case "login" : message = getDataRegLog();
            if (message !== "")
                sendToServer(message, "login");
            break;
        case "register" : message = getDataRegLog();
            if (message !== "")
                sendToServer(message, "register");
            break;
        case "ban" : message = getSelectedUser();
            if (message !== "")
                sendToServer(message, "ban");
            break;
        case "deban" : message = getSelectedUser();
            if (message !== "")
                sendToServer(message, "deban");
            break;
        case "delete" : message = getSelectedUser();
            if (message !== "")
                sendToServer(message, "delete");
            break;
        case "addUser" : message = getAddFieldText() + "," + activeChat;
            if (message !== "")
                sendToServer(message, "addUserToChat");
            break;
        case "addChat" : message = getAddFieldText();
            if (message !== "")
                sendToServer(message, "addChat");
            break;
    }
}

function getAddFieldText(){
    return document.getElementById("chatName_txt").value.trim();
}

function getSelectedUser(){
    return document.getElementById("adminField").value.trim();
}

function getDataRegLog(){
    var nameStr = document.getElementById("lgn_fld").value;
    var pass = document.getElementById("pswrd_fld").value;
    if(nameStr === "" || pass === "") {
        alert("error");
        return "";
    }

    return nameStr + "," + pass;
}
function closeSocket() {
    webSocket.close();
}


function newAvailiableChat(notification) {
    var ex = false;
    for(var i = 0; i < availiableChats.length; i++)
        if(availiableChats[i] === notification.text)
            ex = true;

    if(!ex) {
        availiableChats[availiableChats.length] = notification.text;
        addChatToTable(notification.text)
    }
}

function addChatToTable(chat){
    var tb = document.getElementById("ExstChats");
    var row = tb.insertRow(1);
    row.insertCell(0).innerHTML = "<button id = \"" + chat + "\" onclick=changeChat(\"" + chat + "\")>" + chat + "</button>";
}

function updateMessages(notification){
    checkForNewMessages(notification);
    messages = notification.messages;
    document.getElementById("history").value = messages[activeChat];
}

function checkForNewMessages(notification) {
    for(var i = 0; i < availiableChats.length; i++){
        if(messages !== undefined && messages[availiableChats[i]] !== notification.messages[availiableChats[i]]) {
            if(availiableChats[i] !== activeChat) {
                var tmp = document.getElementById(availiableChats[i]).innerHTML;
                document.getElementById(availiableChats[i]).innerHTML = tmp + "---NEW messages";
            }
        }
    }
}

function changeChat(chat){
    activeChat = chat;
    document.getElementById("history").value = messages[activeChat];
    document.getElementById(chat).innerHTML = chat;
}

function login(notification){
    var res = notification.text.split(",");
    if(res[0] === "ok"){
        reglog(notification, res);
        // clientModel.fireTableDataChanged();
        activeChat = res[2];
        updateMessages(notification);
        username = res[1];
        addChatToTable(res[2]);
    }else
        alert("error");
}

function register(notification) {
    var res = notification.text.split(",");
    reglog(notification, res);
    // clientModel.fireTableDataChanged();
    activeChat = res[2];
    username = res[1];
    addChatToTable(res[2]);
    updateMessages(notification);
}

function reglog(notification, res) {
    if(res[1].toLowerCase().trim() === "admin"){
        var temp = Object.keys(notification.messages);
        for(var i = 0; i < temp.length; i++) {
            availiableChats[availiableChats.length] = temp[i];
            addChatToTable(temp[i]);
        }
    }else {
        availiableChats[availiableChats.length] = res[2];
    }
}