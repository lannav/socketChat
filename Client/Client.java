package Client;

import javax.swing.*;

public class Client extends JFrame {
    private JTextArea messagesArea;
    private JTextField messageField;
    private JTextField name;
    private JTextField password;
    private JPanel activePanel;
    private Listeners listeners;
    private JTextField adminField;
    private JTextField addField;
    public JTable tb;


    public Client(String server, int port){
        ClientModel clientModel = new ClientModel();
        Connector connector = new Connector(server, port, this, clientModel);
        listeners = new Listeners(connector, clientModel, this);

        setLayout(null);
        setBounds(100, 50, 615, 520);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        messagesArea = new JTextArea();
        messagesArea.setEditable(false);
        messagesArea.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(messagesArea);
        jsp.setBounds(270, 10, 320, 430);
        add(jsp);

        tb = new JTable(clientModel);
        tb.getSelectionModel().addListSelectionListener(listeners);
        JScrollPane sp = new JScrollPane(tb);
        sp.setBounds(10, 10, 250, 280);
        add(sp);

        JButton addChat = new JButton("add chat");
        addChat.setBounds(10, 300, 100, 20);
        addChat.addActionListener(listeners);
        addChat.setActionCommand("addChat");
        add(addChat);

        addField = new JTextField();
        addField.setBounds(120,315,120,20);
        add(addField);

        JButton addUser = new JButton("add user");
        addUser.setBounds(10, 330, 100, 20);
        addUser.addActionListener(listeners);
        addUser.setActionCommand("addUser");
        add(addUser);

        setAuthPanel();
        add(activePanel);

        JButton sendMessage = new JButton("Отправить");
        sendMessage.setBounds(270,450,110,20);
        sendMessage.addActionListener(listeners);
        sendMessage.setActionCommand("send");
        add(sendMessage);
        messageField = new JTextField("Введите ваше сообщение: ");
        messageField.setBounds(390,450,200,20);
        add(messageField);

        addWindowListener(listeners);
        setVisible(true);
    }

    public void setAuthPanel() {
        JPanel authPanel = new JPanel();
        authPanel.setLayout(null);
        authPanel.setBounds(10, 360, 250, 110);
        add(authPanel);
        name = new JTextField("username");
        name.setBounds(40,10,170,20);
        authPanel.add(name);
        password = new JTextField("password");
        password.setBounds(40,45,170,20);
        authPanel.add(password);
        JButton login = new JButton("login");
        login.setBounds(40,90,80,20);
        login.addActionListener(listeners);
        login.setActionCommand("login");
        authPanel.add(login);
        JButton register = new JButton("register");
        register.setBounds(130,90,80,20);
        register.addActionListener(listeners);
        register.setActionCommand("register");
        authPanel.add(register);
        activePanel = authPanel;
        super.repaint();
    }

    public void setUserPanel(String username){
        JPanel userPanel = new JPanel();
        userPanel.setLayout(null);
        userPanel.setBounds(10, 360, 250, 110);
        JLabel nameLabel = new JLabel(username);
        nameLabel.setBounds(20,40,210,20);
        userPanel.add(nameLabel);
        remove(activePanel);
        activePanel = userPanel;
        add(activePanel);
        super.repaint();
    }

    public void setAdminPanel(){
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(null);
        adminPanel.setBounds(10, 360, 250, 110);
        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setBounds(70,10,110,20);
        adminPanel.add(adminLabel);
        JButton banButton = new JButton("Ban");
        banButton.setActionCommand("ban");
        banButton.addActionListener(listeners);
        banButton.setBounds(20,30,70,20);
        adminPanel.add(banButton);
//        JTextField banField = new JTextField();
//        banField.setBounds(80,30,150,20);
//        adminPanel.add(banField);
        JButton debanButton = new JButton("deBan");
        debanButton.setActionCommand("deban");
        debanButton.addActionListener(listeners);
        debanButton.setBounds(20,60,70,20);
        adminPanel.add(debanButton);
        adminField = new JTextField();
        adminField.setBounds(100,60,130,20);
        adminPanel.add(adminField);
        JButton delButton = new JButton("Delete");
        delButton.setActionCommand("delete");
        delButton.addActionListener(listeners);
        delButton.setBounds(20,90,70,20);
        adminPanel.add(delButton);
//        JTextField delField = new JTextField();
//        delField.setBounds(80,90,150,20);
//        adminPanel.add(delField);
        remove(activePanel);
        activePanel = adminPanel;
        add(activePanel);
        super.repaint();
    }

    public String getMessageText(){
        String result = messageField.getText().trim();
        if(result.equals(""))
            return "";
        else
            return name.getText() + ": " + result;
    }

    public void setMessageText(String s) {
        messageField.setText(s);
    }

    public void setMessages(String msg){
        messagesArea.setText(msg);
    }
    public String getDataRegLog(){
        String nameStr = name.getText();
        String pass = password.getText();
        if(nameStr.equals("") || pass.equals("")) {
            JOptionPane.showMessageDialog(null,"error");
            return "";
        }

        return nameStr + "," + pass;
    }

    public String getSelectedUser(){
        return adminField.getText().trim();
    }

    public String getAddFieldText(){
        return addField.getText().trim();
    }
}
