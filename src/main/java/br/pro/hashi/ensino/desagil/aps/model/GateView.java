package br.pro.hashi.ensino.desagil.crossover.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A classe JPanel representa uma das componentes mais
// simples da Swing. A função dela é simplesmente ser
// um contêiner para colocar outras componentes dentro.
// A razão de implementar ActionListener está mais abaixo.
public class GateView extends JPanel implements ActionListener {

    // A ideia é que essa componente gráfica represente
    // uma calculadora específica. Essa calculadora que
    // está sendo representada é guardada como atributo.
    private final Calculator calculator;

    // A classe JTextField representa um campo de texto.
    private final JTextField weightField;
    private final JTextField radiusField;
    private final JTextField resultField;

    public GateView(Calculator calculator) {
        this.calculator = calculator;

        // Nada de especial na construção dos campos.
        weightField = new JTextField();
        radiusField = new JTextField();
        resultField = new JTextField();

        // A classe JLabel representa um rótulo, ou seja,
        // um texto não-editável que queremos colocar na
        // interface para identificar alguma coisa. Não
        // precisa ser atributo, pois não precisamos mais
        // mexer nesses objetos depois de criar e adicionar.
        JLabel weightLabel = new JLabel("Weight");
        JLabel radiusLabel = new JLabel("Radius");
        JLabel resultLabel = new JLabel("Result");

        // Um JPanel tem um layout, ou seja, um padrão para
        // organizar as componentes dentro dele. A linha abaixo
        // estabelece um dos padrões mais simples: simplesmente
        // colocar uma componente debaixo da outra, alinhadas.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Colocamos todas componentes aqui no contêiner.
        add(weightLabel);
        add(weightField);
        add(radiusLabel);
        add(radiusField);
        add(resultLabel);
        add(resultField);

        // Uma campo de texto tem uma lista de observadores que
        // reagem quando o usuário dá Enter depois de digitar.
        // Usamos o método addActionListener para adicionar esta
        // instância de CalculatorView, ou seja "this", nessa
        // lista. Só que addActionListener espera receber um objeto
        // do tipo ActionListener como parâmetro. É por isso que
        // adicionamos o "implements ActionListener" lá em cima.
        weightField.addActionListener(this);
        radiusField.addActionListener(this);

        // O último campo de texto não pode ser editável, pois é
        // só para exibição. Logo, configuramos como desabilitado.
        resultField.setEnabled(false);

        // Update é o método que definimos abaixo para atualizar o
        // último campo de acordo com os valores dos primeiros.
        // Precisamos chamar esse método no final da construção
        // para garantir que a interface não nasce inconsistente.
        update();
    }

    private void update() {
        double weight;
        double radius;

        try {
            // O conteúdo de um campo é uma String, não um double.
            // Se queremos interpretar como double, precisamos fazer
            // uma conversão. Esse Double.parseDouble faz isso...
            weight = Double.parseDouble(weightField.getText());
            radius = Double.parseDouble(radiusField.getText());

        } catch (NumberFormatException exception) {
            // ...e se a string não representar um double válido,
            // esse parseDouble lança um NumberFormatException.
            resultField.setText("?");
            return;
        }

        double result = calculator.calculate(weight, radius);

        // O contrário também vale! Para colocar um double no
        // campo, precisamos antes converter ele para String.
        resultField.setText(Double.toString(result));
    }

    // O que esta componente deve fazer quando o usuário der um
    // Enter depois de digitar? Apenas chamar o update, é claro!
    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }
}