package socketapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmad
 */
public class Server {
    
    ArrayList<String> clientNames = new ArrayList<>();
    ArrayList<String> clientRef = new ArrayList<>();
    boolean isOk;
    
    Server(){
        isOk = true;
        
        try{
            ServerSocket server = new ServerSocket(22000);
            Thread handelingIncomingConnections = new Threads(){
                @Override
                public void run(){
                    try{int id = 1;
                        while(isOk){
                            Socket clientSocket = server.accept();
                            ClientHandler clientHandle = new ClientHandler(clientSocket, id+"");
                            id++;
                            clientHandle.start();
                            synchronized(clientRef){
                                clientRef.add(clientHandle);
                            }
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            };
            handelingIncomingConnections.start();
            
            Thread messageHandler = new Thread(){
                @Override
                public void run(){
                    ArrayList<String> msgs;
                    while(isOk){
                        while(true){
                            msgs = clientHandle1.getMesseges();
                            if(!msgs.isEmpty()){
                                synchronized(msgs){
                                    for(int i=0; i<msgs.size(); ++i){
                                        clientHandle2.sendMsg(msgs.get(i));
                                    }
                                    msgs.clear();
                                }
                            }

                            try {
                                Thread.sleep(10);
                            }catch(InterruptedException ex) {}
                        }
                        
                    }
                }
            };
            messageHandler.start();
            
        }catch(IOException ex){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);    
        }
        
    }
}
