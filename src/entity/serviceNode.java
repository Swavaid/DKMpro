package entity;

import algorithm.Setup;
import it.unisa.dia.gas.jpbc.Element;

/**
 * @author emilio
 * @date 2023-10-07 14:57
 */
public class serviceNode {
    public Element ss;
    public Element[] coefficient = new Element[Setup.threshold];
    public Element[] polyEval = new Element[Setup.n];
    public Element[] received_coefficient = new Element[Setup.n];
    public Element[] pub_coefficient = new Element[Setup.n];
    public Element secret_share;
    public Element public_share;
    public Element blind_input;
    public Element evaluation_share;

    public Element rot_ss;
    public Element[] rot_coefficient = new Element[Setup.threshold];
    public Element[] rot_polyEval = new Element[2*Setup.threshold+1];
    public Element[] rot_received_coefficient = new Element[2*Setup.threshold+1];
    public Element[] rot_pub_coefficient = new Element[2*Setup.threshold+1];
    public Element rot_secret_share;
    public Element rot_public_share;
    public Element new_secret_share;
    //h_i(x)
    public Element[] rot_coefficient_later = new Element[Setup.threshold];
    public Element[] rot_polyEval_later = new Element[2*Setup.threshold+1];
    public Element[] received_later = new Element[2*Setup.threshold+1];

    public Element[] refresh_long_public_key;
    public Element[] refresh_long_secret_key;

    public Element[] refresh_ephemeral_public_key;
    public Element[] refresh_ephemeral_secret_key;
}
