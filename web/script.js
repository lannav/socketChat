function add_ChatName()
{
   let ChatName = document.getElementById('chatName_txt').value;

   var tbody = document.getElementById('ExstChats').getElementsByTagName('tbody')[0];

   var row = document.createElement('tr');
   tbody.appendChild(row);

   var td1 = document.createElement("td");

   row.appendChild(td1);

   td1.innerHTML = ChatName;
}

function send() {

}
