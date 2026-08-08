/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;

/**
 *
 * @author bruno
 */
public class TelaUsuario extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaUsuario
     */
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // Método para consultar usuários
    private void consultar() {
        String sql = "select * from tbusuarios where iduser = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtareaID.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtareaNome.setText(rs.getString(2));
                txtareaFone.setText(rs.getString(3));
                txtareaLogin.setText(rs.getString(4));
                txtareaSenha.setText(rs.getString(5));
                comboPerfil.setSelectedItem(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado");
                // As linhas abaixo "limpam" os campos
                txtareaNome.setText(null);
                txtareaFone.setText(null);
                txtareaLogin.setText(null);
                txtareaSenha.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Método para inserir usuários
    private void inserir() {
        String sql = "insert into tbusuarios(iduser, usuario, fone, login, senha, perfil) values (?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtareaID.getText());
            pst.setString(2, txtareaNome.getText());
            pst.setString(3, txtareaFone.getText());
            pst.setString(4, txtareaLogin.getText());
            pst.setString(5, txtareaSenha.getText());
            pst.setString(6, comboPerfil.getSelectedItem().toString());
            if ((txtareaID.getText().isEmpty()) || (txtareaNome.getText().isEmpty()) || (txtareaLogin.getText().isEmpty()) || (txtareaSenha.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            } else {
                // A linha abaixo atualiza a tabelausuarios com os dados do formulário
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "O usuário " + txtareaNome.getText() + " foi inserido com sucesso.");
                    txtareaNome.setText(null);
                txtareaFone.setText(null);
                txtareaLogin.setText(null);
                txtareaSenha.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            txtareaNome.setText(null);
            txtareaFone.setText(null);
            txtareaLogin.setText(null);
            txtareaSenha.setText(null);
        }

    }
    
    // Método de alterar dados da tabela
    public void alterar(){
        String sql = "update tbusuarios set usuario = ?, fone = ?, login = ?, senha = ?, perfil = ? where iduser = ?";
        try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtareaNome.getText());
            pst.setString(2, txtareaFone.getText());
            pst.setString(3, txtareaLogin.getText());
            pst.setString(4, txtareaSenha.getText());
            pst.setString(5, (String) comboPerfil.getSelectedItem());
            pst.setString(6, txtareaID.getText());
            if ((txtareaID.getText().isEmpty()) || (txtareaNome.getText().isEmpty()) || (txtareaLogin.getText().isEmpty()) || (txtareaSenha.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            } else {
                // A linha abaixo atualiza a tabelausuarios com os dados do formulário
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "O usuário " + txtareaNome.getText() + " foi editado com sucesso.");
                    txtareaNome.setText(null);
                    txtareaFone.setText(null);
                    txtareaLogin.setText(null);
                    txtareaSenha.setText(null);
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
   private void remover() {
        String sql = "delete from tbusuarios where iduser = ?";
        consultar();
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar o usuário " + txtareaNome.getText(), "Atenção", JOptionPane.YES_NO_OPTION);
        if(confirmar == JOptionPane.YES_OPTION)
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtareaID.getText());
                if (txtareaID.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Insira o ID do usuário que deseja deletar.");
                } else {
                    // A linha abaixo atualiza a tabelausuarios com os dados do formulário
                    int adicionado = pst.executeUpdate();
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "O usuário " + txtareaNome.getText() + " foi removido com sucesso");
                        txtareaNome.setText(null);
                    txtareaFone.setText(null);
                    txtareaLogin.setText(null);
                    txtareaSenha.setText(null);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                txtareaNome.setText(null);
                txtareaFone.setText(null);
                txtareaLogin.setText(null);
                txtareaSenha.setText(null);
            }
   }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtareaID = new javax.swing.JTextField();
        txtareaNome = new javax.swing.JTextField();
        txtareaFone = new javax.swing.JTextField();
        txtareaLogin = new javax.swing.JTextField();
        comboPerfil = new javax.swing.JComboBox<>();
        txtareaSenha = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnRead = new javax.swing.JButton();
        btnCreate = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(363, 328));

        jLabel1.setText("*ID");

        jLabel2.setText("*Nome");

        jLabel3.setText("Fone");

        jLabel4.setText("*Login");

        jLabel5.setText("*Senha");

        jLabel6.setText("*Perfil");

        comboPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnDelete.setToolTipText("Apagar");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/edit.png"))); // NOI18N
        btnUpdate.setToolTipText("Editar");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnRead.setToolTipText("Consultar");
        btnRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadActionPerformed(evt);
            }
        });

        btnCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnCreate.setToolTipText("Adicionar");
        btnCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtareaNome)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtareaFone, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addComponent(txtareaSenha))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtareaLogin)
                            .addComponent(comboPerfil, 0, 95, Short.MAX_VALUE))))
                .addGap(0, 18, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtareaID, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRead)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtareaID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtareaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtareaFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txtareaLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtareaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(comboPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRead, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        setBounds(0, 0, 363, 328);
    }// </editor-fold>//GEN-END:initComponents
   
    private void btnReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadActionPerformed
        // TODO add your handling code here:
        consultar();
    }//GEN-LAST:event_btnReadActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        // TODO add your handling code here:
        inserir();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRead;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> comboPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtareaFone;
    private javax.swing.JTextField txtareaID;
    private javax.swing.JTextField txtareaLogin;
    private javax.swing.JTextField txtareaNome;
    private javax.swing.JTextField txtareaSenha;
    // End of variables declaration//GEN-END:variables

}
