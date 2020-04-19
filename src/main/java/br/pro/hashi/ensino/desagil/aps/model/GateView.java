package br.pro.hashi.ensino.desagil.aps.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ActionListener, MouseListener {

    private final Gate gate;

    private final JCheckBox gateInputField1;
    private final JCheckBox gateInputField2;

    private final Image image;
    private final Light light;


    public GateView(Gate gate) {
        super(320, 133);

        light = new Light(255, 0, 0);

        this.gate = gate;

        gateInputField1 = new JCheckBox();
        gateInputField2 = new JCheckBox();

        if (!gate.toString().equals("NOT")) {
            add(gateInputField1, 20, 30, 20, 20);
            add(gateInputField2, 20, 80, 20, 20);
        } else {
            add(gateInputField1, 20, 55, 20, 20);
        }

        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        gateInputField1.addActionListener(this);
        gateInputField2.addActionListener(this);

        addMouseListener(this);
        update();

    }

    private void update() {

        Switch input1 = new Switch();

        if (!gate.toString().equals("NOT")) {
            Switch input2 = new Switch();
            if (gateInputField2.isSelected()) {
                input2.turnOn();
            }
            gate.connect(1, input2);
        }

        if (gateInputField1.isSelected()) {
            input1.turnOn();
        }

        gate.connect(0, input1);

        light.connect(0, gate);

        repaint();

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // Descobre em qual posição o clique ocorreu.
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        double distancia = Math.sqrt(Math.pow(280 - x, 2) + Math.pow(65 - y, 2));

        // Se o clique foi dentro do circulo colorido...
        if (distancia <= 10) {

            // ...então abrimos a janela seletora de cor...
            light.setColor(JColorChooser.showDialog(this, null, light.getColor()));

            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 0, 0, 320, 133, this);

        // Desenha um circulo cheio.
        g.setColor(light.getColor());
        g.fillOval(270, 55, 20, 20);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}