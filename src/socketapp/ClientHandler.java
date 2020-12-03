package socketapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmad
 */
public class ClientHandler extends Thread{
    Socket client;
    String name;
    DataInputStream input;
    DataOutputStream output;
    ArrayList<String> receivedMessages = new ArrayList<>();
    
    
    ClientHandler(Socket clientSocket){
        client = clientSocket;
        try{
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());
        }catch(IOException ex){
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        try{
            String str;
            while(true){
                str = input.readUTF();
                synchronized(receivedMessages){
                    receivedMessages.add(str);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
        try{
            if(output != null)
                output.close();
        }catch(IOException ex){}
        try{
            if(input != null)
                input.close();
        }catch(IOException ex){}
        try{
            if(client != null)
                client.close();
        }catch(IOException ex){}
    }
    
    public void sendMsg(String str){
        try{
            output.writeUTF(str);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<String> getMesseges(){
        return receivedMessages;
    }
    public void setClientName(String name){
        this.name = name;
    }
    public String getClientName(){
        return name;
    }
    public void closeAll(){
        isOk = false;
        try{input.close();}catch(Exception e){};
        try{output.close();}catch(Exception e){};
        try{client.close();}catch(Exception e){};
    }
}
