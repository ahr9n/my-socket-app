package socketapp;

import java.util.Scanner;

/**
 *
 * @author ahmad
 */
public class SocketApp {
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner scan = new Scanner(System.in);
        String data = scan.next();
        
        if(data.equalsIgnoreCase("server")){
            // server code
            Server s = new Server();
        }else{
            // client code
            Client c = new Client(data);
        }
    }
    
}
