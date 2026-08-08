/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.infox.telas;

import javax.swing.JOptionPane;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author bruno
 */
public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    // A linha abaixo cria uma variável para armazenar um texto de acordo com o
    // radio button selecionado
    private String tipo;

    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void emitir_os() {
        String sql = "insert into tbos (tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli) values (?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOSSituacao.getSelectedItem().toString());
            pst.setString(3, txtOSequip.getText());
            pst.setString(4, txtOSdefeito.getText());
            pst.setString(5, txtOSservico.getText());
            pst.setString(6, txtOStecnico.getText());
            // Substitui a vírgula pelo ponto
            pst.setString(7, txtOSvalor.getText().replace(",", "."));
            pst.setString(8, txtCliID.getText());
            if ((txtCliID.getText().isEmpty()) || (txtOSequip.getText().isEmpty()) || (txtOSdefeito.getText().isEmpty()) || (cboOSSituacao.getSelectedItem().equals(" "))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            } else {
                // A linha abaixo atualiza a tabelausuarios com os dados do formulário
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso.");
                    btnOScreate.setEnabled(false);
                    btnOSread.setEnabled(false);
                    btnOSprint.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            txtCliID.setText(null);
            txtOSequip.setText(null);
            txtOSdefeito.setText(null);
            txtOSservico.setText(null);
            txtOStecnico.setText(null);
            txtOSvalor.setText(null);
        }
    }

    // Método para pesquisar uma OS
    private void pesquisar_os() {
        // A linha abaixo cria uma caixa de entrada do tipo JOptionPane
        String num_os = JOptionPane.showInputDialog("Número da OS");
        String sql = "select os, date_format(data_os, '%d/%m/%Y - %H:%i'), tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli from tbos where os = ?;";
        // sqlCli = "select * from tbclientes where idcli = "
        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(num_os));
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOS.setText(rs.getString(1));
                txtData.setText(rs.getString(2));
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("OS")) {
                    rbtOS.setSelected(true);
                    tipo = "OS";
                } else {
                    rbtOrcamento.setSelected(true);
                    tipo = "Orçamento";
                }
                cboOSSituacao.setSelectedItem(rs.getString(4));
                txtOSequip.setText(rs.getString(5));
                txtOSdefeito.setText(rs.getString(6));
                txtOSservico.setText(rs.getString(7));
                txtOStecnico.setText(rs.getString(8));
                txtOSvalor.setText(rs.getString(9));
                txtCliID.setText(rs.getString(10));
                btnOScreate.setEnabled(false);
                tblClientes.setVisible(false);
                txtCliPesquisar.setEnabled(false);
                btnOSupdate.setEnabled(true);
                btnOSprint.setEnabled(true);
                btnOSdelete.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "OS não cadasrada.");
            }
        } catch (java.lang.NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "OS inválida.");
            System.out.println(e1);
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
            System.out.println(e2);
        }
    }

    public void alterar_os() {
        String sql = "update tbos set tipo = ?, situacao = ?, equipamento = ?, defeito = ?, servico = ?, tecnico = ?, valor = ? where os = ?;";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOSSituacao.getSelectedItem().toString());
            pst.setString(3, txtOSequip.getText());
            pst.setString(4, txtOSdefeito.getText());
            pst.setString(5, txtOSservico.getText());
            pst.setString(6, txtOStecnico.getText());
            // Substitui a vírgula pelo ponto
            pst.setString(7, txtOSvalor.getText().replace(",", "."));
            pst.setString(8, txtOS.getText());
            if ((txtCliID.getText().isEmpty()) || (txtOSequip.getText().isEmpty()) || (txtOSdefeito.getText().isEmpty()) || (cboOSSituacao.getSelectedItem().equals(" "))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            } else {
                // A linha abaixo atualiza a tabelausuarios com os dados do formulário
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS editada com sucesso.");
                    limpar_campos();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void deletar_os() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir essa OS?", "Atenção", JOptionPane.YES_NO_OPTION);
        String sql = "delete from tbos where os = ?;";
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOS.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS excluída com sucesso.");
                    limpar_campos();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "");
            }
        }
    }
    
    // Método para imprimir uma OS
    private void imprimir_os(){
        int confirma = JOptionPane.showConfirmDialog(null, "Você confirma a impressão desse relatório?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                // Usando a classe JasperPrint para preparar a impressão de um relatório
                JasperPrint print = JasperFillManager.fillReport("C:/reports/os.jasper", null, conexao);
                // A linha abaixo exibe o relatório a partir da classe JasperViewer
                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    // Limpar campos e habilitar os botões
    private void limpar_campos() {
        // Limpar os campos
        txtOS.setText(null);
        txtData.setText(null);
        txtCliID.setText(null);
        txtOSequip.setText(null);
        txtOSdefeito.setText(null);
        txtOSservico.setText(null);
        txtOStecnico.setText(null);
        txtOSvalor.setText(null);
        cboOSSituacao.setSelectedItem(" ");
        txtCliPesquisar.setText(null);
        ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
        // Habilitar os objetos
        btnOScreate.setEnabled(true);
        txtCliPesquisar.setEnabled(true);
        tblClientes.setVisible(true);
        // Desabilitar os botões
        btnOSupdate.setEnabled(false);
        btnOSdelete.setEnabled(false);
        btnOSprint.setEnabled(false);
    }

    // Método de alterar dados da tabela
    private void pesquisar_cliente() {
        String sql = "select idclientes as ID, nome as Nome, fone as Fone from tbclientes where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //Passando o conteúdo da caixa de pesquisa
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campos() {
        int setar = tblClientes.getSelectedRow();
        txtCliID.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoBotoes = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        numeroOS = new javax.swing.JLabel();
        dataOS = new javax.swing.JLabel();
        txtOS = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbtOrcamento = new javax.swing.JRadioButton();
        rbtOS = new javax.swing.JRadioButton();
        situacao = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCliID = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtOSequip = new javax.swing.JTextField();
        txtOSdefeito = new javax.swing.JTextField();
        txtOSservico = new javax.swing.JTextField();
        txtOStecnico = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOSvalor = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnOScreate = new javax.swing.JButton();
        btnOSread = new javax.swing.JButton();
        btnOSupdate = new javax.swing.JButton();
        btnOSdelete = new javax.swing.JButton();
        btnOSprint = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cboOSSituacao = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setTitle("OS");
        setPreferredSize(new java.awt.Dimension(640, 480));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        numeroOS.setText("N°OS");

        dataOS.setText("Data");

        txtOS.setEditable(false);

        txtData.setEditable(false);
        txtData.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        grupoBotoes.add(rbtOrcamento);
        rbtOrcamento.setText("Orçamento");
        rbtOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcamentoActionPerformed(evt);
            }
        });

        grupoBotoes.add(rbtOS);
        rbtOS.setText("Ordem de serviço");
        rbtOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numeroOS)
                    .addComponent(rbtOrcamento)
                    .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtOS)
                    .addComponent(dataOS)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataOS)
                    .addComponent(numeroOS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOS, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        situacao.setText("jLabel1");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrega OK", "Orçamento REPROVADO", "Aguardando aprovação", "Aguardando peças", "Abandonado pelo cliente", "Na bancada", "Retornou" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("CLIENTE"));

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/search-icon (2).png"))); // NOI18N

        jLabel2.setText("*ID");

        txtCliID.setEditable(false);

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Fone"
            }
        ));
        tblClientes.setFocusable(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliID, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtCliID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel3.setText("*Equipamento");

        jLabel4.setText("*Defeito");

        jLabel5.setText("Técnico");

        jLabel6.setText("Serviço");

        jLabel7.setText("Valor total");

        txtOSvalor.setText("0");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnOScreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnOScreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOScreateActionPerformed(evt);
            }
        });

        btnOSread.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnOSread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSreadActionPerformed(evt);
            }
        });

        btnOSupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/edit.png"))); // NOI18N
        btnOSupdate.setEnabled(false);
        btnOSupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSupdateActionPerformed(evt);
            }
        });

        btnOSdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnOSdelete.setEnabled(false);
        btnOSdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSdeleteActionPerformed(evt);
            }
        });

        btnOSprint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/IMPRIMIR.png"))); // NOI18N
        btnOSprint.setToolTipText("Imprimir OS");
        btnOSprint.setEnabled(false);
        btnOSprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSprintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(btnOScreate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOSread)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOSupdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOSdelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOSprint, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnOSprint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOSdelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOSupdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOSread, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOScreate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel8.setText("Situação:");

        cboOSSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Na bancada", "Entrega OK", "Orçamento REPROVADO", "Aguardando aprovação", "Aguardando peças", "Abandonado pelo cliente", "Retornou" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cboOSSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(situacao, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtOSequip, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtOSdefeito)
                                    .addComponent(txtOSservico)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtOStecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtOSvalor, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboOSSituacao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtOSequip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtOSdefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtOSservico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOStecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(txtOSvalor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(220, 220, 220)
                .addComponent(situacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setBounds(0, 0, 490, 463);
    }// </editor-fold>//GEN-END:initComponents

    private void rbtOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOSActionPerformed
        // TODO add your handling code here:
        // Atribuindo um texto a variavel tipo se selecionado
        tipo = "OS";
    }//GEN-LAST:event_rbtOSActionPerformed

    private void btnOScreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOScreateActionPerformed
        // TODO add your handling code here:
        emitir_os();
    }//GEN-LAST:event_btnOScreateActionPerformed

    private void btnOSreadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSreadActionPerformed
        // TODO add your handling code here:
        pesquisar_os();
    }//GEN-LAST:event_btnOSreadActionPerformed

    private void btnOSupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSupdateActionPerformed
        // TODO add your handling code here:
        alterar_os();
    }//GEN-LAST:event_btnOSupdateActionPerformed

    private void btnOSdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSdeleteActionPerformed
        // TODO add your handling code here:
        deletar_os();
    }//GEN-LAST:event_btnOSdeleteActionPerformed

    private void btnOSprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSprintActionPerformed
        // TODO add your handling code here:
        imprimir_os();
    }//GEN-LAST:event_btnOSprintActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        // Chamando o método setar campos
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void rbtOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcamentoActionPerformed
        // TODO add your handling code here:
        // Atribuindo um texto a variavel tipo se selecionado
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrcamentoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        // Ao abrir o FORM, marcar o radio button orçamento
        rbtOrcamento.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOScreate;
    private javax.swing.JButton btnOSdelete;
    private javax.swing.JButton btnOSprint;
    private javax.swing.JButton btnOSread;
    private javax.swing.JButton btnOSupdate;
    private javax.swing.JComboBox<String> cboOSSituacao;
    private javax.swing.JLabel dataOS;
    private javax.swing.ButtonGroup grupoBotoes;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel numeroOS;
    private javax.swing.JRadioButton rbtOS;
    private javax.swing.JRadioButton rbtOrcamento;
    private javax.swing.JLabel situacao;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliID;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtOS;
    private javax.swing.JTextField txtOSdefeito;
    private javax.swing.JTextField txtOSequip;
    private javax.swing.JTextField txtOSservico;
    private javax.swing.JTextField txtOStecnico;
    private javax.swing.JTextField txtOSvalor;
    // End of variables declaration//GEN-END:variables
}
