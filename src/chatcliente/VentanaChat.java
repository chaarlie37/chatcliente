package chatcliente;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JFrame;


public class VentanaChat extends javax.swing.JFrame {

    public ventanaConexion ventanac;
    public boolean conectado = false;
    public StringBuilder textoChat = new StringBuilder();
    public String ip_server;
    public int puerto;
    public Chat chat;
    public boolean minimizado = false;
    Thread hilo = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                System.out.println("e");
                chat.Escuchar();
            }
        }
    });
    
    public VentanaChat(){
        initComponents();
        
    }
    
      
    public VentanaChat(String ip, int port, ventanaConexion ventanaC) {
        initComponents();
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if(e.getNewState() == JFrame.ICONIFIED){
                    minimizado = true;
                    System.out.println("Minimizado");
                }else{
                    minimizado = false;
                }
            }
        });
        this.getRootPane().setDefaultButton(bEnviar);
        ip_server = ip;
        puerto = port;
        try {
            chat = new Chat(ip_server, puerto, this);
        } catch (UnknownHostException e) {
        } catch (IOException | AWTException e) {
        }
        hilo.start();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mensajeParaEnviar = new javax.swing.JTextArea();
        bEnviar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        conversacion = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        bCerrarConexion = new javax.swing.JMenuItem();
        bIniciarOtraConexion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mensajeParaEnviar.setColumns(20);
        mensajeParaEnviar.setRows(5);
        mensajeParaEnviar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mensajeParaEnviarKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(mensajeParaEnviar);

        bEnviar.setText("Enviar");
        bEnviar.setNextFocusableComponent(mensajeParaEnviar);
        bEnviar.setSelected(true);
        bEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEnviarActionPerformed(evt);
            }
        });

        conversacion.setEditable(false);
        conversacion.setColumns(20);
        conversacion.setRows(5);
        conversacion.setCaretPosition(conversacion.getDocument().getLength());
        conversacion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        conversacion.setHighlighter(null);
        jScrollPane2.setViewportView(conversacion);

        jMenu1.setText("Conexión...");

        bCerrarConexion.setText("Cerrar conexión");
        bCerrarConexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCerrarConexionActionPerformed(evt);
            }
        });
        jMenu1.add(bCerrarConexion);

        bIniciarOtraConexion.setText("Iniciar otra conexión...");
        bIniciarOtraConexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIniciarOtraConexionActionPerformed(evt);
            }
        });
        jMenu1.add(bIniciarOtraConexion);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bCerrarConexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCerrarConexionActionPerformed
        try{
            if (conectado) {
                chat.CerrarConexion();
                bCerrarConexion.setText("Conectar de nuevo...");
            } else {
                chat.EstablecerConexion(ip_server, puerto);
            }
        }catch (UnknownHostException e) {
        }catch (IOException e) {
        }catch(AWTException e){}      
    }//GEN-LAST:event_bCerrarConexionActionPerformed

    private void bEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEnviarActionPerformed
        // TODO add your handling code here:
        EnviarMensaje();
    }//GEN-LAST:event_bEnviarActionPerformed

    private void mensajeParaEnviarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mensajeParaEnviarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            EnviarMensaje();         
        }
    }//GEN-LAST:event_mensajeParaEnviarKeyPressed

    private void bIniciarOtraConexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIniciarOtraConexionActionPerformed
        ventanac = new ventanaConexion();
        ventanac.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bIniciarOtraConexionActionPerformed

    public static void main(String args[]) throws UnknownHostException{
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaChat().setVisible(true);
                
                
            }
        });
        
        
        
    }
    
    public void EscribirMensajeDelServer(String msg) {
        if (msg.length() > 0) {
            textoChat.append("[" + ip_server + "]: " + msg + "\n");
            ActualizarConversacion(textoChat.toString());
        }
    }

    public void EscribirMensajeCliente(String msg) {
        if (msg.length() > 0) {
            textoChat.append("[TÚ]: " + msg + "\n");
            ActualizarConversacion(textoChat.toString());
        }
    }
    
    public void ActualizarConversacion(String cadena){   
            conversacion.setText(cadena);
            conversacion.setCaretPosition(conversacion.getDocument().getLength());    
    }
    
    public void EnviarMensaje(){
        try {
            String mensaje = mensajeParaEnviar.getText();
            if (mensaje != null) {
                chat.EnviarMensaje(mensaje);
                EscribirMensajeCliente(mensaje);
                mensajeParaEnviar.setText("");
                mensajeParaEnviar.setCaretPosition(1);
            }
        } catch (IOException e) {
            textoChat.append("IOException: error de E/S");
        }
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem bCerrarConexion;
    private javax.swing.JButton bEnviar;
    private javax.swing.JMenuItem bIniciarOtraConexion;
    private javax.swing.JTextArea conversacion;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea mensajeParaEnviar;
    // End of variables declaration//GEN-END:variables

}
