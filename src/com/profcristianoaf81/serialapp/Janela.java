/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profcristianoaf81.serialapp;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author cristianoaf81
 */
public class Janela extends JFrame{
    private JButton bt_ligar,bt_desligar,bt_sair;
    private JLabel lb_App,lb_resposta;
    private ControlePorta ArduinoSerial;
    private final String LIGAR = "liga led";
    private final String DESLIGAR = "desliga led";
    
    public Janela(){
        InicializarComponentes();
        ArduinoSerial = new ControlePorta("/dev/ttyUSB0", 9600);
        DefinirEventos();
        
        /*
            utilizar no windows
            ArduinoSerial = new ControlePorta("COM4", 9600);
        */
    }
    
    public void InicializarComponentes(){
        setLookAndFell();
        setTitle("Exemplo Java+Arduino");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 400, 250);
        setResizable(true);
        setLocationRelativeTo(null);
        lb_App = new JLabel("Ligar / Desligar Led");
        lb_App.setBounds(130, 15, 170, 30);
        bt_ligar = new JButton("Ligar");
        bt_desligar = new JButton("Desligar");
        bt_sair = new JButton("Sair");
        bt_ligar.setBounds(120, 45, 170, 30);
        bt_desligar.setBounds(120, 75, 170, 30);
        bt_sair.setBounds(120, 105, 170, 30);
        lb_resposta = new JLabel("");
        lb_resposta.setBounds(150
                , 140
                , 120
                , 30);
        add(lb_App);
        add(bt_ligar);
        add(bt_desligar);
        add(bt_sair);
        add(lb_resposta);
        setVisible(true);
    
        
    }
    
    public void DefinirEventos(){
        bt_ligar.addActionListener((ae) -> {
            ArduinoSerial.enviarDados(LIGAR);
            lb_resposta.setText("Led Ligado");
            System.out.println("Arduino disse : "
                    +ArduinoSerial.getDadosDaSerial());
            repaint();
        });
        
        bt_desligar.addActionListener((ae2)->{
            ArduinoSerial.enviarDados(DESLIGAR);
            lb_resposta.setText("Led Desligado");
            System.out.println("Arduino disse : "
                    +ArduinoSerial.getDadosDaSerial());
            repaint();
                        
        });
        bt_sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              ArduinoSerial.enviarDados(DESLIGAR);
              ArduinoSerial.fechar();
              System.exit(0);
            }
        });
        
    }
    
    public void setLookAndFell(){
        try{
         UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }catch(ClassNotFoundException | IllegalAccessException 
                | InstantiationException | UnsupportedLookAndFeelException e){
          try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
          }catch(ClassNotFoundException 
                  | IllegalAccessException | InstantiationException 
                  | UnsupportedLookAndFeelException t){
          
          }
        }
    }
    
    
}
