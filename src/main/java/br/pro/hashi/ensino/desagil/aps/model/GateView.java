package br.pro.hashi.ensino.desagil.aps.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

// A classe JPanel representa uma das componentes mais
// simples da Swing. A função dela é simplesmente ser
// um contêiner para colocar outras componentes dentro.
// A razão de implementar ActionListener está mais abaixo.
public class GateView extends FixedPanel implements ActionListener, MouseListener {

    // A ideia é que essa componente gráfica represente
    // um gate específico. Esse gate que está
    // sendo representado é guardado como atributo.
    private final Gate gate;

    // A classe JCheckBox representa um checkbox.
    private final JCheckBox gateInputField1;
    private final JCheckBox gateInputField2;
    // Novos atributos necessários para esta versão da interface.
    private final Image image;
    private Color color;
    private Light light;


    public GateView(Gate gate) {
        super(320,133);
        color = Color.RED;
        light = new Light(255,0,0);

        this.gate = gate;

        // Nada de especial na construção dos campos.
        gateInputField1 = new JCheckBox();
        gateInputField2 = new JCheckBox();

        // A classe JLabel representa um rótulo, ou seja,
        // um texto não-editável que queremos colocar na
        // interface para identificar alguma coisa. Não
        // precisa ser atributo, pois não precisamos mais
        // mexer nesses objetos depois de criar e adicionar.


        // Não há mais a chamada de setLayout, pois ela agora
        // acontece no construtor da superclasse FixedPanel.


        // Colocamos todas componentes aqui no contêiner.
        if (gate.toString() != "NOT") {
            add(gateInputField1,20, 30, 20,20);
            add(gateInputField2, 20, 80, 20,20);
        }else{
            add(gateInputField1,20, 55, 20,20);
        }


        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);


        // Um checkbox tem uma lista de observadores que
        // reagem quando o usuário dá Enter depois de digitar.
        // Usamos o método addActionListener para adicionar esta
        // instância de GateView, ou seja "this", nessa
        // lista. Só que addActionListener espera receber um objeto
        // do tipo ActionListener como parâmetro. É por isso que
        // adicionamos o "implements ActionListener" lá em cima.
        gateInputField1.addActionListener(this);
        gateInputField2.addActionListener(this);

        // O último checkbox não pode ser editável, pois é
        // só para exibição. Logo, configuramos como desabilitado.

        // Update é o método que definimos abaixo para atualizar o
        // último campo de acordo com os valores dos primeiros.
        // Precisamos chamar esse método no final da construção
        // para garantir que a interface não nasce inconsistente.
        addMouseListener(this);
        update();

    }

    private void update() {

        Switch input1 = new Switch();

        if (gate.toString() != "NOT") {
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
        light.getColor();
        
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

        // Se o clique foi dentro do quadrado colorido...
        if (x >= 255 && x < 295 && y >= 30 && y < 80) {

            // ...então abrimos a janela seletora de cor...
            light.setColor(JColorChooser.showDialog(this, null, color));

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

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 0, 0, 320, 133, this);

        // Desenha um quadrado cheio.
        g.setColor(light.getColor());
        g.fillOval(270, 55, 25, 25);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}