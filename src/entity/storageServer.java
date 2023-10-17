package entity;

import algorithm.Setup;
import it.unisa.dia.gas.jpbc.Element;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author emilio
 * @date 2023-10-07 14:58
 */
public class storageServer {
    public Map<Element,Object[]> ciphertext_store = new LinkedHashMap<>();

    public Element[] token_shares = new Element[2*Setup.threshold+1];

    public Element[] public_shares = new Element[2*Setup.threshold+1];

    public Element update_token;
    public Element pub_new_key;
}
