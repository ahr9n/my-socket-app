package socketapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//import java.lang.System.Logger;
//import java.lang.System.Logger.Level;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmad
 */
public class Client {
    boolean isOk;
    
    Client(String name){
        isOk = true;
        
        try {
            InetAddress ip = InetAddress.getLocalHost();
            //System.out.println(ip);
            System.out.println("Welcome, "+name+".");
            
            Socket other = new Socket(ip, 22000);
            DataInputStream otherReadSource = new DataInputStream(other.getInputStream());
            DataOutputStream otherWriteSource = new DataOutputStream(other.getOutputStream());

            isOk = true;
            Thread t = new Thread(){
                @Override
                public void run(){
                    String str = "";
                    try{
                        while(isOk){
                            str = otherReadSource.readUTF();
                            System.out.println("Other said: "+str);
                        }
                    }catch(Exception e){}
                }
            };
            t.start();
            Scanner scan = new Scanner(System.in);
            
            String str = "";
            while(true){
                str = scan.nextLine();
                otherWriteSource.writeUTF(str);
                if(str.equalsIgnoreCase("exit")){
                    break;
                }
            }
               
            isOk = false;
            otherReadSource.close();
            otherWriteSource.close();
            other.close();

        }catch(IOException ex){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
