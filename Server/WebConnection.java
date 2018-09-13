package Server;

import AdjacentClass.Notification;
import AdjacentClass.Transformer;
import AdjacentClass.Transformer_JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebConnection extends SimpleChannelInboundHandler<TextWebSocketFrame> implements Connectionable {

    public String login;
    public String password;
    public boolean isAuthorized = false;
    public boolean isBan = false;
    public boolean isAdmin = false;

    ServerController serverController;
    ChannelHandlerContext outMessage;

    public WebConnection(ServerController serverController){
        this.serverController = serverController;
    }

    @Override
    public void run() {

    }

    @Override
    public void sendNotification(Notification notification) {
        Transformer transformer = Transformer_JSON.getInstance();
        outMessage.writeAndFlush(new TextWebSocketFrame(transformer.getString(notification)));
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isBan() {
        return isBan;
    }

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean isAuthorized() {
        return isAuthorized;
    }

    @Override
    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setBan(boolean isBan) {
        this.isBan = isBan;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        outMessage = channelHandlerContext;
        serverController.handleClientMessage(textWebSocketFrame.text(), this);
    }
}
