package tools;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author emilio
 * @date 2023-10-09 16:59
 */
public class ServiceNodesBlockchain {
    public static Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));

    public static HashMap hashMapAccountKey;

    static {
        try {
            hashMapAccountKey = IndexGen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static HashMap IndexGen() throws IOException {
        HashMap hashMap=new HashMap<>();
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(0),"99406e979030f14501f20cc1ea6309a0c696cf33d2c6bf870649291277d06362");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(1),"36899fe676335aeed4e5b45f5a629e96167984653cb553f3ea80330c48b70787");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(2),"69570a8d34131d2aa3bee1ec6ff681084192f90f2db857b3d2a6ede3b524149f");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(3),"1023a8823bdf38ad7c525779c42f311ddcd578dea474773b3c225b2db0c59109");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(4),"545434dd2c38af0b4ce9a65579840523c73bb3903c10a97807830ba6ce42d773");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(5),"726674d94e789b25b88946dac66bc77797074e8c3478cc88e2b7e2023b152019");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(6),"163d6e6c0c7f93346d3afb18226ba64b39663c1b5ff5daf1915923266d97f9cd");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(7),"35a5f142a35a53b93686cc4e3e2a3e5ff6b089148983cf9e062f8329c4af3778");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(8),"9137de6cefd8bec215f3095fee4a702349d114b424490028300eb884490d01db");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(9),"4b59792f5610c625ec4dd3323e301462df39ed88b5f2b70c928eb1dea1b99da9");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(10),"cc4be1a1b3ca7529f20061e605f19b9e76c930a33ef8cf1c90adc6ef67a8b92b");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(11),"844ff8dff13d23f9147258783dc1307ce08441be51d7095cc11cb82d03f43671");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(12),"d146ab1e7b5d3b591d4d56504be1f1f94ffcd94f04f7f931c46a9e8f0ff7aeb7");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(13),"6018480ce0c62267e6d0f07f2c36a9e891aa58367a2b572e34804d1a013bf37b");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(14),"1598fe488ca95b2cfa8f46bfa981c7a3c5e4c7f83e23bab41a391b359b49c047");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(15),"7a83a2398a89dd55ad65ff5aa34a84edbdcce661f8455e14b707e4f05e099204");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(16),"71d68a727da3cf20220042cc75e99f8a70712e2d425e03234fca8876c4aedce8");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(17),"38f70563ace2e92223cd96bcaed5137257c83d3081588ab3a1be931809c4e325");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(18),"931a66b17c1a4f28c9a89d3537323cc59e230f5deb5491cdc5fc52856ef14fc8");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(19),"366bb9077ae539eb9df4a016afb73929e3f7063192e20754d3835b5d2ec199ab");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(20),"df88ab9be77e817b68e0686ffc02ef3e84588f56d2dd1435437cf6b846131d34");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(21),"31227d4f6f9308bdc5d756e7a2580732c0deac96f8a6cf03d521039e1b60af8e");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(22),"c45beb85af5f23374e04ec523d223a115b145c88263cf1e394c104a32adcec80");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(23),"5ee291d4d5e3f457d1fde2ff3cb9fe6c33b96a4fc04511ff3aa2f0d0337885ab");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(24),"2486b76da0ee32b410fa7526e9cf139eb02f041d3f714b86ccb61b99c1190a90");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(25),"a5acaa4bffce2b7d002d01c46c69808983213e20379cb45249d829de9bcf5a2a");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(26),"9364e8e23f1dfa114813db33766c220a559cc79ce479521b46dd04baa5f213bb");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(27),"4c070e27bde1cac06946222d94e262adeb6ff94272ad909914bd2ac16fe395f4");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(28),"44e1d781ee9f4f4af3cdce60f465ebe8bfbb61f8c23dd9760b661b3b3bb86e20");
        hashMap.put(web3j.ethAccounts().send().getAccounts().get(29),"0c0197b4c4a1f00d6c3565cc80676930b8044416f6541bb1660cca0ab634f551");
        return hashMap;
    }
}
