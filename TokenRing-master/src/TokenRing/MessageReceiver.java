package TokenRing;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Recebe mensagens do vizinho da esquerda e repassa para a classe MessageController. 
 * Provavelmente você não precisará modificar esta classe.
 */

public class MessageReceiver implements Runnable {
    
    private MessageQueue queue;
    private int port;
    private MessageController controller;
    
    public MessageReceiver(MessageQueue t, int p, MessageController c){
        queue = t;
        port = p;
        controller = c;
    }
    
    @Override
    public void run() {
        
        DatagramSocket serverSocket = null;
        
        try {
            
            // Inicializa o servidor para aguardar datagramas na porta especificada
            serverSocket = new DatagramSocket(port);
            
        } catch (SocketException ex) {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        // byte[] receiveData = new byte[1024];
                
        while(true){
            
            byte[] receiveData = new byte[1024];
            
            // Cria um DatagramPacket
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
            try {
                /* Aguarda o recebimento de uma mensagem. Esta thread ficará bloqueada neste ponto
                até receber uma mensagem. */
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /* Converte o conteúdo do datagrama em string. 
             * Lembre-se, isso apenas funciona porque sabemos que a mensagem recebida tem formato string. 
             */
            String msg = new String(receivePacket.getData());
                        
            /* Neste ponto você possui uma mensagem do seu vizinho da esquerda.
             * Passe a mensagem para a classe MessageController, ela deverá decidir
             * o que fazer.
             */
            controller.ReceivedMessage(msg);
        }
    }
    
}
