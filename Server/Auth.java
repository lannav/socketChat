package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Auth {

    private static Auth instance = null;

    public static Auth getInstance(){
        if(instance == null)
            instance = new Auth();

        return instance;
    }

    public boolean login(String name, String password){
        boolean result = false;
        try {
            if(sendRequest("login", name, password).equals("ok"))
                result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean register(String name, String password){
        boolean result = false;
        try {
            if(sendRequest("register", name, password).equals("ok"))
                result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String sendRequest(String mode, String name, String password) throws IOException {
        URL obj = new URL("http://localhost/Authorization/auth.php?data={\"name\":\"" + name + "\",\"password\":\"" + password + "\"}&mode=" + mode);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response);

        return response.toString();
    }
}
