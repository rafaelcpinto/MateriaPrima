/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculoSobremetal;

/**
 *
 * @author Rafael
 */
import javax.swing.text.AttributeSet;  
import javax.swing.text.BadLocationException;  
import java.text.DecimalFormat;

public class CalculoSobremetal extends javax.swing.JFrame {
    boolean apertarBotaoa=true;//variavel utilizada para habilitar ou desabilitar o perfil escolhido
    public CalculoSobremetal() {
        initComponents();
        LADO1.setText(null); 
        LADO2.setText("");
        //LADO3.setText("10");
        BOTAO.setEnabled(true);
        setResizable(false); //
        getRootPane().setDefaultButton(BOTAO);  
        //LADO3.setEnabled(!apertarBotaoa);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        LADO2 = new javax.swing.JTextField();
        Dimensao2 = new javax.swing.JLabel();
        Dimensao1 = new javax.swing.JLabel();
        DiaPolegada = new javax.swing.JLabel();
        SOBREMETAL = new javax.swing.JLabel();
        RDiaPolegada = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        LADO1 = new javax.swing.JTextField();
        mensagemUsuario = new javax.swing.JLabel();
        MASSA = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        RSOBREMETAL = new javax.swing.JLabel();
        RMASSA = new javax.swing.JTextField();
        DiaMM = new javax.swing.JLabel();
        RDiamm = new javax.swing.JLabel();
        BOTAO = new javax.swing.JButton();
        selecPerfil = new javax.swing.JComboBox();
        LADO3 = new javax.swing.JTextField();
        Dimensao3 = new javax.swing.JLabel();
        Perfil = new javax.swing.JLabel();
        Perfil1 = new javax.swing.JLabel();
        selecMat = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        REDUZ = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTextField3.setEditable(false);
        jTextField3.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Calculo de Sobremetal");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        setMaximumSize(null);
        setMinimumSize(null);
        setName("jFrame"); // NOI18N
        setSize(new java.awt.Dimension(400, 400));

        LADO2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LADO2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LADO2ActionPerformed(evt);
            }
        });
        LADO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lado2LimitarCaracterNumerico(evt);
            }
        });

        Dimensao2.setText("DIGITE O VALOR DO  COMPRIMENTO [mm]");

        Dimensao1.setText("DIGITE O VALOR DO  MAIOR DIAMETRO DA PEÇA [mm]");

        DiaPolegada.setText("DIAMETRO EM POLEGADA: ");

        SOBREMETAL.setText("SOBREMETAL POR BANDA:");

        RDiaPolegada.setText(null);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("ESTE PROGRAMA BASEIA-SE NA NORMA DIN 7527");

        LADO1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LADO1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lado1LimitarCaracterNumerico(evt);
            }
        });

        mensagemUsuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mensagemUsuario.setForeground(new java.awt.Color(255, 0, 0));
        mensagemUsuario.setText(null);

        MASSA.setText("MASSA:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel3.setText("Criado por Rafael C. Pinto");

        RSOBREMETAL.setText(null);

        RMASSA.setEditable(false);
        RMASSA.setText(null);
        RMASSA.setBorder(null);
        RMASSA.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        DiaMM.setText("DIAMETRO EM MILIMETRO:");

        RDiamm.setText(null);

        BOTAO.setText("CALCULAR");
        BOTAO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BOTAOActionPerformed(evt);
            }
        });

        selecPerfil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CILINDRICO", "RETANGULAR"}));
        selecPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alteraValoresDoTextos(evt);
            }
        });

        LADO3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LADO3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lado3LimitarCaracterNumerico(evt);
            }
        });

        Dimensao3.setText("DIGITE O SOBREMETAL NO COMPRIMENTO  [mm]");

        Perfil.setText("SELECIONE O MATERIAL");

        Perfil1.setText("SELECIONE O PERFIL");

        selecMat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aço","Latao","Cobre","Polipropileno","Nylon","Alumínio","Bronze Fosfórico"}));
        selecMat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecMatActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel5.setText("Versão 05/0222-01");

        REDUZ.setText("OTIMIZAR");
        REDUZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                REDUZActionPerformed(evt);
            }
        });

        jLabel1.setText("(Abaixo da norma)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Dimensao1)
                            .addComponent(Dimensao2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(LADO1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(LADO2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(LADO3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Dimensao3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selecPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Perfil1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selecMat, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(Perfil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(REDUZ)
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(165, 165, 165)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RDiaPolegada, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(RDiamm, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(RMASSA, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(MASSA, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DiaMM, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DiaPolegada, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BOTAO)
                                    .addComponent(SOBREMETAL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(RSOBREMETAL, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(mensagemUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(138, 138, 138))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Dimensao1)
                    .addComponent(Perfil1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LADO1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selecPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Dimensao2)
                    .addComponent(Perfil, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LADO2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selecMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(Dimensao3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(REDUZ)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LADO3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(14, 14, 14)
                .addComponent(mensagemUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BOTAO)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RDiaPolegada, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DiaPolegada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DiaMM, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RDiamm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RSOBREMETAL, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SOBREMETAL, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RMASSA, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MASSA, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(53, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        getAccessibleContext().setAccessibleDescription("");

        setSize(new java.awt.Dimension(547, 627));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lado1LimitarCaracterNumerico(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lado1LimitarCaracterNumerico
        String caracteres="0987654321.";
        if(!caracteres.contains(evt.getKeyChar()+"")){
        evt.consume();
        } 
    }//GEN-LAST:event_lado1LimitarCaracterNumerico
 
    private void BOTAOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BOTAOActionPerformed
        if(apertarBotaoa){
           ResultadoCilindrico(); 
        }
        else{
            ResultadoRetangular();
        }
    }//GEN-LAST:event_BOTAOActionPerformed

    private void alteraValoresDoTextos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alteraValoresDoTextos
        //altera as label conformo o perfil escolhido
        
        CalcMateriaPrima materia = new CalcMateriaPrima();
        apertarBotaoa=selecPerfil.getSelectedItem()=="CILINDRICO";
        if(apertarBotaoa){
            //LADO3.setEnabled(!apertarBotaoa);
            Dimensao1.setText("DIGITE O VALOR DO  MAIOR DIAMETRO DA PEÇA ([mm]");
            Dimensao2.setText("DIGITE O VALOR DO  COMPRIMENTO [mm]");
            Dimensao3.setText("DIGITE O SOBREMETAL NO COMPRIMENTO [mm]");
            DiaPolegada.setText("DIAMETRO EM POLEGADA: ");
            DiaMM.setText("DIAMETRO EM MILIMETRO:");
            SOBREMETAL.setText("SOBREMETAL POR BANDA:");
            MASSA.setText("MASSA");

        }
        else{
            //LADO3.setEnabled(!apertarBotaoa);
            Dimensao1.setText("DIGITE O VALOR DO LADO 1 [mm]");
            Dimensao2.setText("DIGITE O VALOR DO LADO 2 [mm]");
            Dimensao3.setText("DIGITE O VALOR DO LADO 3 [mm]");
            DiaPolegada.setText("LADO 1 [mm]");
            DiaMM.setText("LADO 2 [mm]");
            SOBREMETAL.setText("LADO 3 [mm]");
            MASSA.setText("MASSA");
        }
    }//GEN-LAST:event_alteraValoresDoTextos

    private void selecMatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecMatActionPerformed
        CalcMateriaPrima materia = new CalcMateriaPrima();
        materia.posicaoDensidade=selecMat.getSelectedIndex();
        //System.out.println(selecMat.getSelectedIndex());
    }//GEN-LAST:event_selecMatActionPerformed

    private void lado3LimitarCaracterNumerico(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lado3LimitarCaracterNumerico
        String caracteres="0987654321.";
        if(!caracteres.contains(evt.getKeyChar()+"")){
        evt.consume();
        }        
    }//GEN-LAST:event_lado3LimitarCaracterNumerico

    private void lado2LimitarCaracterNumerico(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lado2LimitarCaracterNumerico
        String caracteres="0987654321.";
        if(!caracteres.contains(evt.getKeyChar()+"")){
        evt.consume();
        }
    }//GEN-LAST:event_lado2LimitarCaracterNumerico

    private void LADO2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LADO2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LADO2ActionPerformed

    private void REDUZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_REDUZActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_REDUZActionPerformed
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Set the Nimbus look and feel 
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        // If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        /// For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
        //
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CalculoSobremetal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalculoSobremetal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalculoSobremetal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalculoSobremetal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

         //Create and display the form 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalculoSobremetal().setVisible(true);
            }
        });
    }
    void ResultadoCilindrico(){
        //metodo para opcao cilindrica
        CalcMateriaPrima materia = new CalcMateriaPrima();
        DecimalFormat formato;
        formato = new DecimalFormat("###0.000");
        //if ((Double.parseDouble(DIAMETRO.getText())<=483)&&(Double.parseDouble(COMPRIMENTO.getText())>=0)&&(0<=Double.parseDouble(COMPRIMENTO.getText()))){
        boolean aux1,aux2,aux3;
        String caracteres=".0987654321 ";
        aux1 = !caracteres.endsWith(LADO1.getText()+"");
        aux2 = !caracteres.endsWith(LADO2.getText()+"");
        aux3 = !caracteres.endsWith(LADO3.getText()+"");
        if (aux1 && aux2 && aux3){
            //if ((Double.parseDouble(DIAMETRO.getText())<=483)&&(Double.parseDouble(DIAMETRO.getText())>=0)&&(0<=Double.parseDouble(COMPRIMENTO.getText()))){
                //nao ha necessidade de colocar maior que zero, pois ha varivavel nao recebe valores negativos
                if ((Double.parseDouble(LADO1.getText())<=483)&&(Double.parseDouble(LADO2.getText()))<=3500){
                    double diametro = Double.parseDouble(LADO1.getText());
                    double comprimento = Double.parseDouble(LADO2.getText())+Double.parseDouble(LADO3.getText());
                    materia.CalculoCilindrico(diametro,comprimento,REDUZ.isSelected());
                    RDiaPolegada.setText(materia.diametroPOrdinaria+"\" ");
                    RDiamm.setText(materia.diametroPoelegada + " mm");
                    //RSOBREMETAL.setText(Float.toString(materia.sobremetal)+" mm");
                    RSOBREMETAL.setText(formato.format(materia.sobremetal/2)+" mm");
                    RMASSA.setText(formato.format(materia.massaCilindro)+" kg");
                    mensagemUsuario.setText("");
                }
                else{
                    valorInvalido();  
                }
            }
            else{
                valorInvalido();                            
            }
        
    }
    void ResultadoRetangular(){
        //metodo para opcao retangular
        CalcMateriaPrima materia = new CalcMateriaPrima();
        DecimalFormat formato;
        formato = new DecimalFormat("###0.000");
        //if ((Double.parseDouble(DIAMETRO.getText())<=483)&&(Double.parseDouble(COMPRIMENTO.getText())>=0)&&(0<=Double.parseDouble(COMPRIMENTO.getText()))){
        boolean aux1,aux2,aux3;
        String caracteres=".0987654321 ";
        aux1 = !caracteres.endsWith(LADO1.getText()+"");
        aux2 = !caracteres.endsWith(LADO2.getText()+"");
        aux3 = !caracteres.endsWith(LADO3.getText()+"");
        if (aux1 && aux2 && aux3){
            if ((Double.parseDouble(LADO1.getText())<1000)&&(Double.parseDouble(LADO2.getText()))<1000 && (Double.parseDouble(LADO3.getText()))<1000){
                double lado1 = Double.parseDouble(LADO1.getText());
                double lado2 = Double.parseDouble(LADO2.getText());
                double lado3 = Double.parseDouble(LADO3.getText());
                materia.CalculoRetangular(lado1,lado2,lado3,REDUZ.isSelected());
                RDiaPolegada.setText(formato.format(materia.lado1)+" mm");
                RDiamm.setText(formato.format(materia.lado2)+" mm");
                RSOBREMETAL.setText(formato.format(materia.lado3)+" mm");
                RMASSA.setText(formato.format(materia.massaRetangular)+" kg");
                mensagemUsuario.setText("");
            }
            else{
                valorInvalido();  
            } 
        }
        else{
           valorInvalido();           
        }  
    }
    void valorInvalido(){
        RDiaPolegada.setText("");
        RSOBREMETAL.setText("");
        RMASSA.setText("");
        RDiamm.setText("");
        mensagemUsuario.setText("VALOR INVALIDO!");   
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BOTAO;
    private javax.swing.JLabel DiaMM;
    private javax.swing.JLabel DiaPolegada;
    private javax.swing.JLabel Dimensao1;
    private javax.swing.JLabel Dimensao2;
    private javax.swing.JLabel Dimensao3;
    public javax.swing.JTextField LADO1;
    public javax.swing.JTextField LADO2;
    public javax.swing.JTextField LADO3;
    private javax.swing.JLabel MASSA;
    private javax.swing.JLabel Perfil;
    private javax.swing.JLabel Perfil1;
    private javax.swing.JLabel RDiaPolegada;
    private javax.swing.JLabel RDiamm;
    private javax.swing.JCheckBox REDUZ;
    private javax.swing.JTextField RMASSA;
    private javax.swing.JLabel RSOBREMETAL;
    private javax.swing.JLabel SOBREMETAL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel mensagemUsuario;
    private javax.swing.JComboBox selecMat;
    private javax.swing.JComboBox selecPerfil;
    // End of variables declaration//GEN-END:variables
}
