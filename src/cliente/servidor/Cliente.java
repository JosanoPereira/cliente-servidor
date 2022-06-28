/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cliente.servidor;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 *
 * @author josanopereira
 */

public class Cliente extends JFrame implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;
	private final int porta;
	private final JTextField mensagem;
	private final JButton enviar;
	private final Dimension tamanhoTela;
	private Socket cliente;

	public Cliente(final int porta) {
		this.porta = porta;
		this.mensagem = new JTextField();
		this.enviar = new JButton("Enviar");
		this.tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
	}

	private void configurarJanelaMensagem() {

		enviar.addActionListener(this);

		this.setTitle("Mensageiro");
		this.setSize(500, 250);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3, 1));
		this.add(new JLabel("Mensagem:"));
		this.add(mensagem);
		this.add(enviar);
		this.setLocation((tamanhoTela.width / 2) - (this.getSize().width / 2),
				(tamanhoTela.height / 2) - (this.getSize().height / 2));
		this.setVisible(true);
	}

	private void iniciarCliente(final int porta) {

		try {
			// new Socket(IP da máquina, porta);
			cliente = new Socket(InetAddress.getLocalHost().getHostAddress(), porta);

		} catch (IOException excecao) {
			Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, excecao);
		}
	}

	@Override
	public void actionPerformed(ActionEvent evento) {

		try {
			final PrintStream saida = new PrintStream(cliente.getOutputStream());
			saida.println(mensagem.getText());
			mensagem.setText("");

		} catch (IOException excecao) {
			Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, excecao);
		}
	}

	@Override
	public void run() {
		configurarJanelaMensagem();
		iniciarCliente(porta);
	}
}