/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profcristianoaf81.serialapp;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author cristianoaf81
 */
public class ControlePorta {
    private OutputStream saidaSerial;
    private final int taxa;
    private final String portaCom;
    private SerialPort porta;
    private BufferedReader leitor;
    private String DadosDaSerial;
    private InputStream buffer;
    
    public ControlePorta(String portaCom, int taxa){
        this.portaCom = portaCom;
        this.taxa = taxa;
        this.inicializar();
    }
    
    @SuppressWarnings("null")
    private void inicializar(){
        try{
            CommPortIdentifier IDporta = null;

            try{
                IDporta = CommPortIdentifier.getPortIdentifier(this.portaCom);
            }catch(NoSuchPortException pe){
                JOptionPane.showMessageDialog(null, "Erro"
                        ,"Porta com não Encontrada/ou já está em uso" 
                        , JOptionPane.PLAIN_MESSAGE);

            }
            
            porta = (SerialPort) IDporta.open("Comunicação Serial"
                    , this.taxa);
            saidaSerial = porta.getOutputStream();
            porta.setSerialPortParams(this.taxa
                    ,SerialPort.DATABITS_8
                    ,SerialPort.STOPBITS_1
                    ,SerialPort.PARITY_NONE);
            
        }catch(PortInUseException 
                | UnsupportedCommOperationException 
                | HeadlessException 
                | IOException e){
            JOptionPane.showMessageDialog(null, "Falha!", e.getMessage()
                    , JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public synchronized void fechar(){
        try{
            saidaSerial.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Falha ao fechar Porta COM "
                    +e.getMessage()
                    , "Fechar Porta COM", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public void enviarDados(String comando){
        try{
            saidaSerial.write(comando.getBytes());
        }catch(IOException e){
            JOptionPane.showMessageDialog(null
                    , e.getMessage()
                    , "Não Foi Possível enviar dado para o dispositivo"
                    , JOptionPane.PLAIN_MESSAGE);
        }
    }

   

    public String getDadosDaSerial() {
        this.DadosDaSerial = null;
        try {
            buffer = porta.getInputStream();
            leitor = new BufferedReader(
                    new InputStreamReader(
                            buffer
            ));
            while(buffer.available()>0){
                //System.out.println(" = "+(char) buffer.read());
                this.DadosDaSerial = leitor.readLine();
            }
            return this.DadosDaSerial;
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
        
    }
    
    
    
}
