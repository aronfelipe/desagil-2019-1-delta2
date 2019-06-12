// FONTE DAS IMAGENS: https://en.wikipedia.org/wiki/Logic_gate (domínio público)

package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener {
    private static final int BORDER = 10;
    private static final int SWITCH_SIZE = 18;
    private static final int LIGHT_SIZE = 12;
    private static final int GATE_WIDTH = 90;
    private static final int GATE_HEIGHT = 60;

    private final Switch[] switches;
    private final Gate gate;
    private final JCheckBox[] inputBoxes;
    private final JCheckBox[] outputBoxes;

    private final Image image;

    public GateView(Gate gate) {
        super(BORDER + SWITCH_SIZE + GATE_WIDTH + LIGHT_SIZE + BORDER, GATE_HEIGHT);

        this.gate = gate;

        int inputSize = gate.getInputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }

        int outputSize = gate.getOutputSize();

        outputBoxes = new JCheckBox[outputSize];

        for (int i = 0; i < outputSize; i++) {
            outputBoxes[i] = new JCheckBox();
            outputBoxes[i].setEnabled(false);

        }

        int xinp, yinp, stepinp;

        xinp = BORDER;
        yinp = -(SWITCH_SIZE / 2);
        stepinp = (GATE_HEIGHT / (inputSize + 1));
        for (JCheckBox inputBox : inputBoxes) {
            yinp += stepinp;
            add(inputBox, xinp, yinp, SWITCH_SIZE, SWITCH_SIZE);
        }


        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }

        update();
    }

    private void update() {
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        for (int i=0; i<outputBoxes.length; i++) {
            boolean result = gate.read(i);
            outputBoxes[i].setSelected(result);
        }

        repaint();
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, BORDER + SWITCH_SIZE, 0, GATE_WIDTH, GATE_HEIGHT, this);

        int outputSize = gate.getOutputSize();

        int xout, yout, stepout;

        xout = BORDER + SWITCH_SIZE + GATE_WIDTH;
        yout = -(SWITCH_SIZE / 2) + 3;
        stepout = (GATE_HEIGHT / (outputSize + 1));
        for (int i=0; i <outputBoxes.length; i++) {
            boolean result = gate.read(i);

            if (result) {
                yout += stepout;
                g.setColor(Color.RED);
                g.fillOval(xout, yout, LIGHT_SIZE, LIGHT_SIZE);
            }
            else {
                yout += stepout;
                g.setColor(Color.BLACK);
                g.fillOval(xout, yout, LIGHT_SIZE, LIGHT_SIZE);
            }
        }

        getToolkit().sync();
    }
}
