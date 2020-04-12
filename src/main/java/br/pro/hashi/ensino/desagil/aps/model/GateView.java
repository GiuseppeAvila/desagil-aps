package br.pro.hashi.ensino.desagil.aps.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A classe JPanel representa uma das componentes mais
// simples da Swing. A função dela é simplesmente ser
// um contêiner para colocar outras componentes dentro.
// A razão de implementar ActionListener está mais abaixo.
public class GateView extends JPanel implements ActionListener {

    // A ideia é que essa componente gráfica represente
    // um gate específico. Esse gate que está
    // sendo representado é guardado como atributo.
    private final Gate gate;

    // A classe JCheckBox representa um checkbox.
    private final JCheckBox gateInputField1;
    private final JCheckBox gateInputField2;
    private final JCheckBox gateOutputField;

    public GateView(Gate gate) {
        this.gate = gate;

        // Nada de especial na construção dos campos.
        gateInputField1 = new JCheckBox();
        gateInputField2 = new JCheckBox();
        gateOutputField = new JCheckBox();

        // A classe JLabel representa um rótulo, ou seja,
        // um texto não-editável que queremos colocar na
        // interface para identificar alguma coisa. Não
        // precisa ser atributo, pois não precisamos mais
        // mexer nesses objetos depois de criar e adicionar.
        JLabel gateInputLabel = new JLabel("Entrada:");
        JLabel gateOutputLabel = new JLabel("Saida:");

        // Um JPanel tem um layout, ou seja, um padrão para
        // organizar as componentes dentro dele. A linha abaixo
        // estabelece um dos padrões mais simples: simplesmente
        // colocar uma componente debaixo da outra, alinhadas.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Colocamos todas componentes aqui no contêiner.
        add(gateInputLabel);
        add(gateInputField1);
        add(gateInputField2);
        add(gateOutputLabel);
        add(gateOutputField);

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
        gateOutputField.setEnabled(false);

        // Update é o método que definimos abaixo para atualizar o
        // último campo de acordo com os valores dos primeiros.
        // Precisamos chamar esse método no final da construção
        // para garantir que a interface não nasce inconsistente.
        update();
    }

    private void update() {

        Switch input1 = new Switch();
        Switch input2 = new Switch();

        if (gateInputField1.isSelected()){
            input1.turnOn();
        }
        if (gateInputField2.isSelected()){
            input2.turnOn();
        }

        gate.connect(0,input1 );
        gate.connect(1, input2);

        boolean result = gate.read();

        gateOutputField.setSelected(result);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }
}