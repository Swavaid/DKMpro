package entity;

import algorithm.Enc;
import algorithm.Setup;
import it.unisa.dia.gas.jpbc.Element;

/**
 * @author emilio
 * @date 2023-10-07 14:58
 */
public class Client {

    public Element secret_key;
    public Element ID_c;
    public byte[] mek;
    public String cipher_e;

    public Element[] received_eval_share = new Element[Setup.n];
    public Element blind_evaluation;
    public Element evaluation;

    public String plaintext;


}
