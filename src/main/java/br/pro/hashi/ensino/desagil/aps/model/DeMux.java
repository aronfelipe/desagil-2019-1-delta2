package br.pro.hashi.ensino.desagil.aps.model;

public class DeMux extends Gate {

    private final NandGate nandOne;
    private final NandGate nandTwoUp;
    private final NandGate nandTwoDown;
    private final NandGate nandThreeUp;
    private final NandGate nandThreeDown;


    public DeMux() {
        super("DeMux", 2, 2);

        nandOne = new NandGate();
        nandTwoUp = new NandGate();
        nandTwoDown = new NandGate();
        nandThreeUp = new NandGate();
        nandThreeDown = new NandGate();

        nandTwoUp.connect(1, nandOne);

        nandThreeUp.connect(0, nandTwoUp);
        nandThreeUp.connect(1, nandTwoUp);

        nandThreeDown.connect(0, nandTwoDown);
        nandThreeDown.connect(1, nandTwoDown);


    }

    @Override
    public boolean read(int outputPin) {

        if (outputPin != 0 && outputPin != 1) {
            throw new IndexOutOfBoundsException(outputPin);
        }
        if(outputPin == 0) {
            return nandThreeUp.read();
        }
        return nandThreeDown.read();
    }

    @Override
    public void connect(int inputPin, SignalEmitter emitter) {

        switch (inputPin) {
            case 0:
                nandTwoUp.connect(0, emitter);
                nandTwoDown.connect(1, emitter);
                break;
            case 1:
                nandOne.connect(0, emitter);
                nandOne.connect(1, emitter);
                nandTwoDown.connect(0, emitter);
                break;
            default:
                throw new IndexOutOfBoundsException(inputPin);
        }

    }
}
